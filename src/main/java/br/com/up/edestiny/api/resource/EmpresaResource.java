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
import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.repository.EmpresaRepository;

@RestController
@RequestMapping("/empresa")
public class EmpresaResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Empresa> getAll() {
		return empresaRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Empresa> novaEmpresa(@Valid @RequestBody Empresa empresa, HttpServletResponse response) {
		Optional<Empresa> optEmpresa = empresaRepository.findByCnpj(empresa.getCnpj());

		if (optEmpresa.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optEmpresa.get());
		}

		Empresa novaEmpresa = empresaRepository.save(empresa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaEmpresa.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
	}
}
