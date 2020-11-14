package br.com.up.edestiny.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByEmail(String email);
	public List<Usuario> findAllByEmpresa(Empresa empresa);
}
