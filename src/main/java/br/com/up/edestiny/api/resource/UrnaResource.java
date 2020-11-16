package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.model.Urna;
import br.com.up.edestiny.api.repository.EmpresaRepository;
import br.com.up.edestiny.api.repository.UrnaRepository;
import br.com.up.edestiny.api.repository.dto.UrnaDTO;
import br.com.up.edestiny.api.repository.filter.UrnaFilter;
import br.com.up.edestiny.api.service.UrnaService;

@RestController
@RequestMapping("/urna")
public class UrnaResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private UrnaRepository urnaRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private UrnaService urnaService;

	@GetMapping
	public Page<Urna> pesquisar(UrnaFilter filter, Pageable pageable) {
		return urnaRepository.filtrar(filter, pageable);
	}

	@GetMapping(params = "resumo")
	public Page<UrnaDTO> resumir(UrnaFilter filter, Pageable pageable) {
		return urnaRepository.resumir(filter, pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Urna> obterPorId(@PathVariable Long id) {
		Optional<Urna> opt = urnaRepository.findById(id);
		return opt.isPresent() ? ResponseEntity.ok(opt.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/listaUrnasEmpresa/{id}")
	public ResponseEntity<List<Urna>> listaUrnasEmpresa(@PathVariable Long id) {
		Optional<Empresa> opt = empresaRepository.findById(id);
		List<Urna> lista = urnaRepository.findAllByEmpresa(opt.get());
		return !lista.isEmpty() ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Urna> novaUrna(@Valid @RequestBody Urna urna, HttpServletResponse response) {
		Urna novaUrna = urnaRepository.save(urna);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaUrna.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(novaUrna);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removerUrna(@PathVariable Long id) {
		urnaRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Urna> atualizarUrna(@PathVariable Long id, @Valid @RequestBody Urna urna) {
		return ResponseEntity.status(HttpStatus.OK).body(urnaService.atualizarUsuario(id, urna));
	}

}
