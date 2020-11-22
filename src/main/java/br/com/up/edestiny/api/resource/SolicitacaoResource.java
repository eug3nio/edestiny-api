package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.up.edestiny.api.event.RecursoCriadoEvent;
import br.com.up.edestiny.api.model.Categoria;
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Residuo;
import br.com.up.edestiny.api.model.Solicitacao;
import br.com.up.edestiny.api.model.enums.SituacaoSolicitacao;
import br.com.up.edestiny.api.repository.CategoriaRepository;
import br.com.up.edestiny.api.repository.ColetaRepository;
import br.com.up.edestiny.api.repository.DetentorRepository;
import br.com.up.edestiny.api.repository.ResiduoRepository;
import br.com.up.edestiny.api.repository.SolicitacaoRepository;
import br.com.up.edestiny.api.repository.dto.SolicitacaoDTO;
import br.com.up.edestiny.api.repository.filter.SolicitacaoFilter;

@RestController
@RequestMapping("/solicitacao")
public class SolicitacaoResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private DetentorRepository detentorRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ResiduoRepository residuoRepository;

	@Autowired
	private ColetaRepository coletaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public Page<Solicitacao> pesquisar(SolicitacaoFilter filter, Pageable pageable) {
		return solicitacaoRepository.filtrar(filter, pageable);
	}

	@GetMapping(params = "resumo")
	public Page<SolicitacaoDTO> resumir(SolicitacaoFilter filter, Pageable pageable) {
		return solicitacaoRepository.resumir(filter, pageable);
	}

	@GetMapping("/findAllBySolicitante")
	public List<Solicitacao> findAllBySolicitante(String email, HttpServletResponse response) {
		Optional<Detentor> solicitante = detentorRepository.findByEmail(email);
		return solicitacaoRepository.findBySolicitante(solicitante.get());
	}

	@GetMapping("/findAllByColeta/{id}")
	public List<Solicitacao> findAllByColeta(@PathVariable Long id, HttpServletResponse response) {
		return solicitacaoRepository.findByColeta(coletaRepository.findById(id).get());
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

		List<Residuo> residuos = new ArrayList<>();
		for (Residuo item : solicitacao.getResiduos()) {
			Optional<Categoria> categoria = categoriaRepository.findByDescricao(item.getCategoria().getDescricao());
			if (!categoria.isPresent()) {
				throw new EmptyResultDataAccessException(1);
			} else {
				item.setCategoria(categoria.get());
			}
			residuos.add(residuoRepository.save(item));
		}

		solicitacao.setResiduos(residuos);

		ZoneId zoneId = ZoneId.of("GMT");
		LocalDate dtSolicitacao = LocalDate.now(zoneId);
		solicitacao.setDtSolicitacao(dtSolicitacao);

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
