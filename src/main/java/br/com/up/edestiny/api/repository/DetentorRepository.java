package br.com.up.edestiny.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Detentor;

public interface DetentorRepository extends JpaRepository<Detentor, Long> {

	public Optional<Detentor> findByEmail(String email);
}
