package br.com.up.edestiny.api.repository.solicitacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.repository.dto.SolicitacaoDTO;
import br.com.up.edestiny.api.repository.filter.SolicitacaoFilter;

public interface SolicitacaoRepositoryQuery {

	public Page<Solicitacao> filtrar(SolicitacaoFilter filter, Pageable pageable);

	public Page<SolicitacaoDTO> resumir(SolicitacaoFilter filter, Pageable pageable);
}
