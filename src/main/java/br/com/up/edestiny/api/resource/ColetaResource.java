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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.up.edestiny.api.event.RecursoCriadoEvent;
import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.repository.ColetaRepository;

@RestController
@RequestMapping("/coleta")
public class ColetaResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ColetaRepository coletaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Coleta> findAll() {
		return coletaRepository.findAll();
	}

	@GetMapping(name = "/{id}")
	public ResponseEntity<Coleta> getById(@RequestParam Long id) {
		Optional<Coleta> optColeta = coletaRepository.findById(id);
		return optColeta.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(optColeta.get())
				: ResponseEntity.notFound().build();
	}

	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Coleta> criarColeta(@Valid @RequestBody Coleta coleta, HttpServletResponse response) {
		Coleta novaColeta = coletaRepository.save(coleta);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaColeta.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novaColeta);
	}
}
