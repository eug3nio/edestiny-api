package br.com.up.edestiny.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	public Optional<Empresa> findByCnpj(String cnpj);

}
