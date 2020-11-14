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
import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.model.Usuario;
import br.com.up.edestiny.api.repository.EmpresaRepository;
import br.com.up.edestiny.api.repository.EnderecoRespository;
import br.com.up.edestiny.api.repository.UsuarioRepository;
import br.com.up.edestiny.api.repository.dto.EmpresaDTO;
import br.com.up.edestiny.api.repository.filter.EmpresaFilter;
import br.com.up.edestiny.api.service.EmpresaService;

@RestController
@RequestMapping("/empresa")
public class EmpresaResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private EnderecoRespository enderecoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private EmpresaService empresaService;

	@GetMapping("/findByRazaoSocial/{razaoSocial}")
	public List<Empresa> findByRazaoSocial(@PathVariable String razaoSocial) {
		return empresaRepository.listByRazaoSocial(razaoSocial);
	}

	@GetMapping
	public Page<Empresa> pesquisar(EmpresaFilter filter, Pageable pageable) {
		return empresaRepository.filtrar(filter, pageable);
	}

	@GetMapping(params = "resumo")
	public Page<EmpresaDTO> resumir(EmpresaFilter filter, Pageable pageable) {
		return empresaRepository.resumir(filter, pageable);
	}

	@GetMapping("/obterPorUsuarioEmail/{email}")
	public ResponseEntity<Empresa> obterPorUsuarioId(@PathVariable String email) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		if (usuario.isPresent()) {
			return usuario.get().getEmpresa() != null ? ResponseEntity.ok(usuario.get().getEmpresa())
					: ResponseEntity.notFound().build();
		}

		throw new EmptyResultDataAccessException(1);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Empresa> obterPorId(@PathVariable Long id) {
		Optional<Empresa> opt = empresaRepository.findById(id);
		return opt.isPresent() ? ResponseEntity.ok(opt.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Empresa> novaEmpresa(@Valid @RequestBody Empresa empresa, HttpServletResponse response) {
		Optional<Empresa> optEmpresa = empresaRepository.findByCnpj(empresa.getCnpj());

		if (optEmpresa.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optEmpresa.get());
		}

		enderecoRepository.save(empresa.getEndereco());
		empresa.getUrnas().forEach(it -> it.setEmpresa(empresa));
		empresa.getUsuarios().forEach(it -> it.setEmpresa(empresa));
		Empresa novaEmpresa = empresaRepository.save(empresa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novaEmpresa.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removerEmpresa(@PathVariable Long id) {
		Optional<Empresa> empresa = empresaRepository.findById(id);
		if (empresaService.validarExclusao(empresa)) {
			for (Usuario user : empresa.get().getUsuarios()) {
				usuarioRepository.delete(user);
			}

			empresaRepository.deleteById(id);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @Valid @RequestBody Empresa empresa) {
		return ResponseEntity.status(HttpStatus.OK).body(empresaService.atualizarEmpresa(id, empresa));
	}
}
