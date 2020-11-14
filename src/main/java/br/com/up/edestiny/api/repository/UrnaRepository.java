package br.com.up.edestiny.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.model.Urna;
import br.com.up.edestiny.api.repository.urna.UrnaRepositoryQuery;

public interface UrnaRepository extends JpaRepository<Urna, Long>, UrnaRepositoryQuery {
	
	public List<Urna> findAllByEmpresa(Empresa empresa);

}
