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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.up.edestiny.api.event.RecursoCriadoEvent;
import br.com.up.edestiny.api.model.Coletor;
import br.com.up.edestiny.api.repository.ColetorRepository;
import br.com.up.edestiny.api.service.UsuarioService;

@RestController
@RequestMapping("/coletor")
public class ColetorResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ColetorRepository coletorRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Coletor> findAll() {
		return coletorRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Coletor> obterPorId(@PathVariable Long id) {
		Optional<Coletor> opt = coletorRepository.findById(id);
		return opt.isPresent() ? ResponseEntity.ok(opt.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping("/novoColetor")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Coletor> novoColetor(@Valid @RequestBody Coletor coletor, HttpServletResponse response) {

		coletor.setSenha(usuarioService.obterSenhaBCrypt(coletor.getSenha()));

		Optional<Coletor> optColetor = coletorRepository.findByCnpj(coletor.getCnpj());

		if (optColetor.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optColetor.get());
		}

		Coletor novoColetor = coletorRepository.save(coletor);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novoColetor.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novoColetor);
	}

}
