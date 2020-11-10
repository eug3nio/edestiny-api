package br.com.up.edestiny.api.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.repository.EmpresaRepository;

@Service
public class EmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmpresaRepository empresaRepository;

	public Empresa atualizarEmpresa(Long id, Empresa empresa) {
		Optional<Empresa> empresaSalva = empresaRepository.findById(id);

		if (empresaSalva.isPresent()) {
			BeanUtils.copyProperties(empresa, empresaSalva, "id");
			return empresaRepository.save(empresaSalva.get());
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

}
