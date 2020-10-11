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
import br.com.up.edestiny.api.model.Categoria;
import br.com.up.edestiny.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Categoria> novaCategora(@Valid @RequestBody Categoria categoria,
			HttpServletResponse response) {
		Categoria novaCategoria = categoriaRepository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaCategoria.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
	}

	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		Optional<Categoria> categoriaSalva = categoriaRepository.findById(id);
		if (!categoriaSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}

		categoriaSalva.get().setAtivo(ativo);
		categoriaRepository.save(categoriaSalva.get());
	}
}
