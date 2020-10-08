package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.up.edestiny.api.event.RecursoCriadoEvent;
import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.repository.SolicitacaoRepository;

@RestController
@RequestMapping("/solicitacao")
public class SolicitacaoResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Solicitacao> novaSolicitacao(@Validated @RequestBody Solicitacao solicitacao,
			HttpServletResponse response) {
		Solicitacao novaSolicitacao = solicitacaoRepository.save(solicitacao);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaSolicitacao.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novaSolicitacao);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Solicitacao> findById(@PathVariable Long id) {
		Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id);
		return solicitacao.isPresent() ? ResponseEntity.ok(solicitacao.get()) : ResponseEntity.notFound().build();
	}
}
