package br.com.up.edestiny.api.resource;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.up.edestiny.api.event.RecursoCriadoEvent;
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.repository.DetentorRepository;

@RestController
@RequestMapping("/detentor")
public class DetentorResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DetentorRepository detentorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Detentor> novoDetentor(@Valid @RequestBody Detentor detentor, HttpServletResponse response) {
		Detentor novoDetentor = detentorRepository.save(detentor);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novoDetentor.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novoDetentor);
	}
}
