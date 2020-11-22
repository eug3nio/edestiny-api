package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.up.edestiny.api.event.RecursoCriadoEvent;
import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.model.Percurso;
import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.model.enums.SituacaoColeta;
import br.com.up.edestiny.api.model.enums.SituacaoSolicitacao;
import br.com.up.edestiny.api.repository.ColetaRepository;
import br.com.up.edestiny.api.repository.PercursoRepository;
import br.com.up.edestiny.api.repository.SolicitacaoRepository;
import br.com.up.edestiny.api.repository.dto.ColetaDTO;
import br.com.up.edestiny.api.repository.filter.ColetaFilter;
import br.com.up.edestiny.api.service.ColetaService;

@RestController
@RequestMapping("/coleta")
public class ColetaResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ColetaRepository coletaRepository;

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private PercursoRepository percursoRepository;

	@Autowired
	private ColetaService coletaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public Page<Coleta> pesquisar(ColetaFilter filter, Pageable pageable) {
		return coletaRepository.filtrar(filter, pageable);
	}

	@GetMapping(params = "resumo")
	public Page<ColetaDTO> resumir(ColetaFilter filter, Pageable pageable) {
		return coletaRepository.resumir(filter, pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Coleta> getById(@PathVariable Long id) {
		Optional<Coleta> optColeta = coletaRepository.findById(id);
		return optColeta.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(optColeta.get())
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/gerarPercurso/{id}")
	public ResponseEntity<Coleta> gerarPercurso(@PathVariable Long id) {
		coletaService.gerarPercurso(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/visualizarPercurso/{id}")
	public ResponseEntity<String> visulizarPercurso(@PathVariable Long id) {
		return ResponseEntity.ok(coletaService.visualizarPercurso(id));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Coleta> criarColeta(@Valid @RequestBody Coleta coleta, HttpServletResponse response) {
		coleta.setSituacao(SituacaoColeta.EM_DIGITACAO);

		Coleta novaColeta = coletaRepository.save(coleta);

		for (Solicitacao item : coleta.getSolicitacoes()) {
			item.setColeta(coleta);
			item.setSituacao(SituacaoSolicitacao.EM_ATENDIMENTO);
			solicitacaoRepository.save(item);
		}

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaColeta.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novaColeta);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removerColeta(@PathVariable Long id) {
		Optional<Coleta> optColeta = coletaRepository.findById(id);

		if (optColeta.isPresent()) {
			optColeta.get().getSolicitacoes().forEach(it -> {
				it.setColeta(null);
				it.setSituacao(SituacaoSolicitacao.ABERTA);
				solicitacaoRepository.save(it);
			});

			List<Percurso> percurso = percursoRepository.findByColeta(optColeta.get());
			percurso.forEach(it -> percursoRepository.delete(it));

			coletaRepository.deleteById(id);
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Coleta> atualizarColeta(@PathVariable Long id, @Valid @RequestBody Coleta coleta) {
		return ResponseEntity.status(HttpStatus.OK).body(coletaService.atualizarColeta(id, coleta));
	}

	@GetMapping("/finalizar/{id}")
	public ResponseEntity<Coleta> finalizarColeta(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(coletaService.finalizarColeta(id));
	}
}
