package br.com.up.edestiny.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.model.Solicitacao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

}
