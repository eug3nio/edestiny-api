package br.com.up.edestiny.api.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;

import br.com.up.edestiny.api.config.EdestinyApiProperty;
import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Endereco;
import br.com.up.edestiny.api.model.Percurso;
import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.model.enums.SituacaoColeta;
import br.com.up.edestiny.api.model.enums.SituacaoSolicitacao;
import br.com.up.edestiny.api.repository.ColetaRepository;
import br.com.up.edestiny.api.repository.PercursoRepository;
import br.com.up.edestiny.api.repository.SolicitacaoRepository;
import br.com.up.edestiny.api.repository.dto.LocationDTO;
import br.com.up.edestiny.api.repository.dto.VisualizarDTO;

@Service
public class ColetaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private EdestinyApiProperty edestinyApiProperty;

	@Autowired
	private ColetaRepository coletaRepository;

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private PercursoRepository percursoRepository;

	/**
	 * 
	 * @param id
	 * @param coleta
	 * @return
	 */
	public Coleta atualizarColeta(Long id, Coleta coleta) {
		Optional<Coleta> coletaSalva = coletaRepository.findById(id);

		if (coletaSalva.isPresent()) {
			coletaSalva.get().getSolicitacoes().forEach(it -> {
				if (!coleta.getSolicitacoes().contains(it)) {
					it.setColeta(null);
					it.setSituacao(SituacaoSolicitacao.ABERTA);
					solicitacaoRepository.save(it);
				}
			});

			coletaSalva.get().getSolicitacoes().clear();
			coletaSalva.get().getSolicitacoes().addAll(coleta.getSolicitacoes());
			coletaSalva.get().getSolicitacoes().forEach(it -> {
				it.setColeta(coletaSalva.get());
				it.setSituacao(SituacaoSolicitacao.EM_ATENDIMENTO);
				solicitacaoRepository.save(it);
			});

			BeanUtils.copyProperties(coleta, coletaSalva.get(), "id", "solicitacoes");
			return coletaRepository.save(coletaSalva.get());
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	/**
	 * 
	 * @param id
	 */
	public String visualizarPercurso(Long id) {
		Optional<Coleta> optColeta = coletaRepository.findById(id);
		if (optColeta.isPresent()) {
			Coleta coleta = optColeta.get();

			VisualizarDTO dto = new VisualizarDTO();

			LocationDTO origin = new LocationDTO();
			origin.setLat(new BigDecimal(coleta.getColetor().getEndereco().getLatitude()));
			origin.setLng(new BigDecimal(coleta.getColetor().getEndereco().getLongitude()));
			dto.setOrigin(origin);

			Endereco enderecoDestination = coleta.getSolicitacoes().get(coleta.getSolicitacoes().size() - 1)
					.getSolicitante().getEndereco();
			LocationDTO destination = new LocationDTO();
			destination.setLat(new BigDecimal(enderecoDestination.getLatitude()));
			destination.setLng(new BigDecimal(enderecoDestination.getLongitude()));
			dto.setDestination(destination);

			dto.setWaypoints(new ArrayList<>());

			Collections.sort(coleta.getSolicitacoes(), new Comparator<Solicitacao>() {
				@Override
				public int compare(Solicitacao o1, Solicitacao o2) {
					return o2.getDistancia() - o1.getDistancia();
				}
			});

			for (Solicitacao item : coleta.getSolicitacoes()) {
				Endereco enderecoLocation = item.getSolicitante().getEndereco();

				if (!enderecoDestination.equals(enderecoLocation)) {
					LocationDTO location = new LocationDTO();
					location.setLat(new BigDecimal(enderecoLocation.getLatitude()));
					location.setLng(new BigDecimal(enderecoLocation.getLongitude()));
					Map<String, LocationDTO> map = new HashMap<>();
					map.put("location", location);
					dto.getWaypoints().add(map);
				}
			}

			return new Gson().toJson(dto);
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	/**
	 * 
	 * @param id
	 */
	public void gerarPercurso(Long id) {
		Optional<Coleta> optColeta = coletaRepository.findById(id);
		GeoApiContext context = new GeoApiContext.Builder().apiKey(edestinyApiProperty.getApiKeyGoogle()).build();

		if (optColeta.isPresent()) {
			Coleta coleta = optColeta.get();
			coleta.setSituacao(SituacaoColeta.EM_ANDAMENTO);
			
			Percurso percurso = new Percurso();
			percurso.setDtCriacao(LocalDate.now());
			percurso.setColeta(coleta);

			Endereco enderecoColetor = coleta.getColetor().getEndereco();
			String enderecoOrigin = obterEnderecoFormatado(enderecoColetor);

			carregarDistancias(coleta, enderecoOrigin, context);

			Collections.sort(coleta.getSolicitacoes(), new Comparator<Solicitacao>() {
				@Override
				public int compare(Solicitacao o1, Solicitacao o2) {
					return o2.getDistancia() - o1.getDistancia();
				}
			});

			percursoRepository.save(percurso);
			coletaRepository.save(coleta);
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	/**
	 * 
	 * @param coleta
	 * @param enderecoOrigin
	 * @param context
	 */
	private void carregarDistancias(Coleta coleta, String enderecoOrigin, GeoApiContext context) {
		LatLng startLocation = null;
		for (Solicitacao solicitacao : coleta.getSolicitacoes()) {
			Detentor solicitante = solicitacao.getSolicitante();
			if (solicitante.getEndereco() != null) {
				DirectionsResult result = DirectionsApi
						.getDirections(context, enderecoOrigin, obterEnderecoFormatado(solicitante.getEndereco()))
						.awaitIgnoreError();

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				DirectionsRoute route = result.routes[0];
				DirectionsLeg leg = route.legs[0];
				solicitacao.setDistancia(Integer.parseInt(gson.toJson(leg.distance.inMeters)));

				if (solicitacao.getSolicitante().getEndereco().getLatitude() == null) {
					solicitacao.getSolicitante().getEndereco().setLatitude(String.valueOf(leg.endLocation.lat));
				}

				if (solicitacao.getSolicitante().getEndereco().getLongitude() == null) {
					solicitacao.getSolicitante().getEndereco().setLongitude(String.valueOf(leg.endLocation.lng));
				}

				if (startLocation == null) {
					startLocation = leg.startLocation;
				}
			}
		}

		if (coleta.getColetor().getEndereco().getLatitude() == null) {
			coleta.getColetor().getEndereco().setLatitude(String.valueOf(startLocation.lat));
		}

		if (coleta.getColetor().getEndereco().getLongitude() == null) {
			coleta.getColetor().getEndereco().setLongitude(String.valueOf(startLocation.lng));
		}

	}

	/**
	 * 
	 * @param endereco
	 * @return
	 */
	private String obterEnderecoFormatado(Endereco endereco) {
		return removerAcentos(endereco.getLogradouro().replace(" ", "_") + "_" + endereco.getNumero() + "_"
				+ endereco.getCidade() + "_" + endereco.getEstado()).toLowerCase();
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	private String removerAcentos(String str) {
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
