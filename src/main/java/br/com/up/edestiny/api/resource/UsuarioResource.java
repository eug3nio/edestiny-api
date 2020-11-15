package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.util.ArrayList;
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
import br.com.up.edestiny.api.model.Urna;
import br.com.up.edestiny.api.model.Usuario;
import br.com.up.edestiny.api.repository.UsuarioRepository;
import br.com.up.edestiny.api.repository.dto.UsuarioDTO;
import br.com.up.edestiny.api.repository.filter.UsuarioFilter;
import br.com.up.edestiny.api.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public Page<Usuario> pesquisar(UsuarioFilter filter, Pageable pageable) {
		return usuarioRepository.filtrar(filter, pageable);
	}

	@GetMapping(params = "resumo")
	public Page<UsuarioDTO> resumir(UsuarioFilter filter, Pageable pageable) {
		return usuarioRepository.resumir(filter, pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obterPorId(@PathVariable Long id) {
		Optional<Usuario> opt = usuarioRepository.findById(id);
		return opt.isPresent() ? ResponseEntity.ok(opt.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping("/obterPorUsuarioEmail/{email}")
	public ResponseEntity<Usuario> obterPorEmail(@PathVariable String email) {
		Optional<Usuario> opt = usuarioRepository.findByEmail(email);
		return opt.isPresent() ? ResponseEntity.ok(opt.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/urnas")
	public ResponseEntity<List<Urna>> listaUrnasUsuario(@PathVariable Long id) {
		Optional<Usuario> opt = usuarioRepository.findById(id);
		return opt.isPresent() ? ResponseEntity.ok(opt.get().getUrnas())
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Usuario> novoUsuario(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		usuario.setSenha(usuarioService.obterSenhaBCrypt(usuario.getSenha()));

		Optional<Usuario> optUsuario = usuarioRepository.findByEmail(usuario.getEmail());
		if (optUsuario.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optUsuario.get());
		}

		Usuario novoUsuario = usuarioRepository.save(usuario);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, novoUsuario.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}

	@PostMapping("/recuperarSenha")
	@ResponseStatus(code = HttpStatus.OK)
	public void recuperarSenha(@RequestBody String email) {
		usuarioService.recuperarSenha(email);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removerUsuario(@PathVariable Long id) {
		usuarioRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.atualizarUsuario(id, usuario));
	}
}
