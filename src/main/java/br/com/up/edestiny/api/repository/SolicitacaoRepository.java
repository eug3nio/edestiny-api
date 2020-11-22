package br.com.up.edestiny.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.repository.solicitacao.SolicitacaoRepositoryQuery;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long>, SolicitacaoRepositoryQuery {

	List<Solicitacao> findBySolicitante(Detentor solicitante);
	
	List<Solicitacao> findByColeta(Coleta	 coleta);
	
	
}
