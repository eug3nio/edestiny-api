package br.com.up.edestiny.api.repository.solicitacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.repository.dto.SolicitacaoDTO;

public interface SolicitacaoRepositoryQuery {

	public Page<Solicitacao> filtrar(Pageable pageable);

	public Page<SolicitacaoDTO> resumir(Pageable pageable);
}
