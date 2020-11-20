package br.com.up.edestiny.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.repository.coleta.ColetaRepositoryQuery;

public interface ColetaRepository extends JpaRepository<Coleta, Long>, ColetaRepositoryQuery {

}
