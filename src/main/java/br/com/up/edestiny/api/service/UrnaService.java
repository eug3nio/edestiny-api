package br.com.up.edestiny.api.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.up.edestiny.api.model.Urna;
import br.com.up.edestiny.api.repository.UrnaRepository;

@Service
public class UrnaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UrnaRepository urnaRepository;
	
	@Bean
	public Queue filaLeituraObjetos() {
	    return new Queue("urna-recebimento", false);
	}
	
	/**
	 * 
	 * @param id
	 * @param urna
	 * @return
	 */
	public Urna atualizarUrna(Long id, Urna urna) {
		Optional<Urna> urnaSalvo = urnaRepository.findById(id);

		if (urnaSalvo.isPresent()) {
			BeanUtils.copyProperties(urna, urnaSalvo.get(), "id");
			return urnaRepository.save(urnaSalvo.get());
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

}
