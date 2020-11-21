package br.com.up.edestiny.api.service;

import java.io.Serializable;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

import br.com.up.edestiny.api.config.EdestinyApiProperty;
import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Endereco;
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
	public void gerarPercurso(Long id) {
		GeoApiContext context = new GeoApiContext.Builder().apiKey(edestinyApiProperty.getApiKeyGoogle()).build();

		Optional<Coleta> optColeta = coletaRepository.findById(id);

		if (optColeta.isPresent()) {
			Percurso percurso = new Percurso();
			percurso.setDtCriacao(LocalDate.now());
			percurso.setColeta(optColeta.get());

			Endereco enderecoColetor = optColeta.get().getColetor().getEndereco();
			String enderecoOrigin = obterEnderecoFormatado(enderecoColetor);

			for (Solicitacao solicitacao : optColeta.get().getSolicitacoes()) {
				Detentor solicitante = solicitacao.getSolicitante();
				if (solicitante.getEndereco() != null) {
					DirectionsResult result = DirectionsApi
							.getDirections(context, enderecoOrigin, obterEnderecoFormatado(solicitante.getEndereco()))
							.awaitIgnoreError();

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					System.out.println(gson.toJson(result.routes));
				}
			}
		} else {
			throw new EmptyResultDataAccessException(1);
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
