package br.com.up.edestiny.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.repository.empresa.EmpresaRepositoryQuery;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>, EmpresaRepositoryQuery {

	public Optional<Empresa> findByCnpj(String cnpj);

}
