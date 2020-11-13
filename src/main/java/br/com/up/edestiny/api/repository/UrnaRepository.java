package br.com.up.edestiny.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Urna;
import br.com.up.edestiny.api.repository.urna.UrnaRepositoryQuery;

public interface UrnaRepository extends JpaRepository<Urna, Long>, UrnaRepositoryQuery {

}
