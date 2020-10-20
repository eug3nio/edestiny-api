package br.com.up.edestiny.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Coletor;

public interface ColetorRepository extends JpaRepository<Coletor, Long> {

	public Optional<Coletor> findByEmail(String email);

}
