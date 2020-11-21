package br.com.up.edestiny.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Solicitacao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

	List<Solicitacao> findBySolicitante(Detentor solicitante);
	
}
