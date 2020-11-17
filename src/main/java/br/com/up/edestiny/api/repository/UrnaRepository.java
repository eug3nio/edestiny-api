package br.com.up.edestiny.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.model.Urna;
import br.com.up.edestiny.api.model.Usuario;
import br.com.up.edestiny.api.repository.urna.UrnaRepositoryQuery;

public interface UrnaRepository extends JpaRepository<Urna, Long>, UrnaRepositoryQuery {

	public List<Urna> findByEmpresaAndUsuarioResponsavelIsNull(Empresa empresa);
	
	public List<Urna> findByEmpresa(Empresa empresa);

	public List<Urna> findByUsuarioResponsavel(Usuario usuarioResponsavel);

}
