package br.com.up.edestiny.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.repository.detentor.DetentorRepositoryQuery;

public interface DetentorRepository extends JpaRepository<Detentor, Long>, DetentorRepositoryQuery {

}
