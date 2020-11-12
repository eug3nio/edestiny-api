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

@Service
public class EmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmpresaRepository empresaRepository;

	/**
	 * 
	 * @param id
	 * @param empresa
	 * @return
	 */
	public Empresa atualizarEmpresa(Long id, Empresa empresa) {
		Optional<Empresa> empresaSalva = empresaRepository.findById(id);

		if (empresaSalva.isPresent()) {
			BeanUtils.copyProperties(empresa, empresaSalva, "id");
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
