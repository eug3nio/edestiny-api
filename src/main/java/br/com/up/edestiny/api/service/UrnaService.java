package br.com.up.edestiny.api.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.up.edestiny.api.model.Urna;
import br.com.up.edestiny.api.repository.UrnaRepository;

@Service
public class UrnaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UrnaRepository urnaRepository;

	/**
	 * 
	 * @param id
	 * @param urna
	 * @return
	 */
	public Urna atualizarUsuario(Long id, Urna urna) {
		Optional<Urna> urnaSalvo = urnaRepository.findById(id);

		if (urnaSalvo.isPresent()) {
			BeanUtils.copyProperties(urna, urnaSalvo.get(), "id", "empresa");
			return urnaRepository.save(urnaSalvo.get());
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

}
