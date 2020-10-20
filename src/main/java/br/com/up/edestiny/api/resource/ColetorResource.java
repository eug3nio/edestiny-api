package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.up.edestiny.api.event.RecursoCriadoEvent;
import br.com.up.edestiny.api.model.Coletor;
import br.com.up.edestiny.api.repository.ColetorRepository;

@RestController
@RequestMapping("/coletor")
public class ColetorResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ColetorRepository coletorRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Coletor> findAll() {
		return coletorRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Coletor> novoColetor(@Valid @RequestBody Coletor coletor, HttpServletResponse response) {
		Optional<Coletor> optColetor = coletorRepository.findByCnpj(coletor.getCnpj());

		if (optColetor.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optColetor.get());
		}

		Coletor novoColetor = coletorRepository.save(coletor);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novoColetor.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novoColetor);
	}

}
