package br.com.up.edestiny.api.service;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import br.com.up.edestiny.api.config.EdestinyApiProperty;
import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Percurso;
import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.model.enums.SituacaoSolicitacao;
import br.com.up.edestiny.api.repository.ColetaRepository;
import br.com.up.edestiny.api.repository.SolicitacaoRepository;

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
	public void gerarPercurso(Long id) {
		Optional<Coleta> optColeta = coletaRepository.findById(id);

		if (optColeta.isPresent()) {
			Percurso percurso = new Percurso();
			percurso.setDtCriacao(LocalDate.now());
			percurso.setColeta(optColeta.get());

			for (Solicitacao solicitacao : optColeta.get().getSolicitacoes()) {
				Detentor solicitante = solicitacao.getSolicitante();
				if (solicitante.getEndereco() != null) {
					try {
					GeoApiContext context = new GeoApiContext.Builder().apiKey(edestinyApiProperty.getApiKeyGoogle())
							.build();
					GeocodingResult[] results;
						results = GeocodingApi.geocode(context, "1600 Amphitheatre Parkway Mountain View, CA 94043")
								.await();
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					System.out.println(gson.toJson(results[0].addressComponents));
				} catch (ApiException | InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

}
