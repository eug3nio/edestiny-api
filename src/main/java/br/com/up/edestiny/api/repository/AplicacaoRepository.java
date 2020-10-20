package br.com.up.edestiny.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Aplicacao;

public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long> {

	public Optional<Aplicacao> findByClient(String client);
}
