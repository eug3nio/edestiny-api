package br.com.up.edestiny.api.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.up.edestiny.api.model.Coleta;
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

		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

}
