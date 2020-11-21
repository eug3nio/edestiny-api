package br.com.up.edestiny.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.repository.solicitacao.SolicitacaoRepositoryQuery;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>, SolicitacaoRepositoryQuery {

}
