package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.up.edestiny.api.event.RecursoCriadoEvent;
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Residuo;
import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.model.enums.SituacaoSolicitacao;
import br.com.up.edestiny.api.repository.DetentorRepository;
import br.com.up.edestiny.api.repository.ResiduoRepository;
import br.com.up.edestiny.api.repository.SolicitacaoRepository;

@RestController
@RequestMapping("/solicitacao")
public class SolicitacaoResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private DetentorRepository detentorRepository;

	@Autowired
	private ResiduoRepository residuoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Solicitacao> listar() {
		return solicitacaoRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Solicitacao> novaSolicitacao(@Valid @RequestBody Solicitacao solicitacao,
			HttpServletResponse response) {
		Optional<Detentor> detentor = detentorRepository.findById(solicitacao.getSolicitante().getId());

		if (!detentor.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		} else {
			solicitacao.setSolicitante(detentor.get());
		}

		for (Residuo item : solicitacao.getResiduos()) {
			residuoRepository.save(item);
		}

		Solicitacao novaSolicitacao = solicitacaoRepository.save(solicitacao);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaSolicitacao.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novaSolicitacao);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Solicitacao> findById(@PathVariable Long id) {
		Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id);
		return solicitacao.isPresent() ? ResponseEntity.ok(solicitacao.get()) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}/atualizarSituacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeSituacao(@PathVariable Long id, @RequestBody String situacao) {
		Optional<Solicitacao> solicitacaoSalva = solicitacaoRepository.findById(id);
		if (!solicitacaoSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}

		solicitacaoSalva.get().setSituacao(SituacaoSolicitacao.findByDescricao(situacao));
		solicitacaoRepository.save(solicitacaoSalva.get());
	}
}
