package br.com.up.edestiny.api.repository.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.up.edestiny.api.model.Usuario;
import br.com.up.edestiny.api.repository.dto.UsuarioDTO;
import br.com.up.edestiny.api.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {

	
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);

	public Page<UsuarioDTO> resumir(UsuarioFilter filter, Pageable pageable);
}
