package br.com.up.edestiny.api.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.up.edestiny.api.model.Usuario;
import br.com.up.edestiny.api.repository.UsuarioRepository;

@Service
public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	public Usuario atualizarUsuario(Long id, Usuario usuario) {
		Optional<Usuario> usuarioSalvo = usuarioRepository.findById(id);

		if (usuarioSalvo.isPresent()) {
			BeanUtils.copyProperties(usuario, usuarioSalvo.get(), "id", "empresa");
			return usuarioRepository.save(usuarioSalvo.get());
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

}
