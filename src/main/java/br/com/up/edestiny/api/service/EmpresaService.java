package br.com.up.edestiny.api.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.repository.EmpresaRepository;
import br.com.up.edestiny.api.repository.UrnaRepository;
import br.com.up.edestiny.api.repository.UsuarioRepository;

@Service
public class EmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private UrnaRepository urnaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * 
	 * @param id
	 * @param empresa
	 * @return
	 */
	public Empresa atualizarEmpresa(Long id, Empresa empresa) {
		Optional<Empresa> empresaSalva = empresaRepository.findById(id);

		if (empresaSalva.isPresent()) {
			empresaSalva.get().getUrnas().clear();
			empresaSalva.get().getUrnas().addAll(urnaRepository.findAllByEmpresa(empresaSalva.get()));
			empresaSalva.get().getUrnas().forEach(it -> it.setEmpresa(empresaSalva.get()));
			
			empresaSalva.get().getUsuarios().clear();
			empresaSalva.get().getUsuarios().addAll(usuarioRepository.findAllByEmpresa(empresaSalva.get()));
			empresaSalva.get().getUsuarios().forEach(it -> it.setEmpresa(empresaSalva.get()));
			

			BeanUtils.copyProperties(empresa, empresaSalva.get(), "id", "urnas", "usuarios");
			return empresaRepository.save(empresaSalva.get());
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	/**
	 * 
	 * @param empresa
	 */
	public boolean validarExclusao(Optional<Empresa> empresa) {
		if (empresa.isPresent()) {
			if (!empresa.get().getUrnas().isEmpty()) {
				throw new DataIntegrityViolationException("A empresa possui urnas vinculadas.");
			}

		} else {
			throw new EmptyResultDataAccessException(1);
		}

		return true;
	}

}
