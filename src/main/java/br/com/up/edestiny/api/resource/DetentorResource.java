package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Endereco;
import br.com.up.edestiny.api.repository.DetentorRepository;
import br.com.up.edestiny.api.repository.EnderecoRespository;
import br.com.up.edestiny.api.service.DetentorService;
import br.com.up.edestiny.api.service.UsuarioService;

@RestController
@RequestMapping("/detentor")
public class DetentorResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DetentorRepository detentorRepository;

	@Autowired
	private EnderecoRespository enderecoRespository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private DetentorService detentorService;


	@GetMapping
	public List<Detentor> listar() {
		return detentorRepository.findAll();
	}
	
	@GetMapping(params = "findDetentorByEmail")
	public ResponseEntity<Detentor> findDetentorByEmail(String email, HttpServletResponse response) {
		Optional<Detentor> detentorExistente = detentorRepository.findByEmail(email);

		if (detentorExistente.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(detentorExistente.get());
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	@PostMapping("/novoDetentor")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Detentor> novoDetentor(@Valid @RequestBody Detentor detentor, HttpServletResponse response) {
		Optional<Detentor> detentorExistente = detentorRepository.findByEmail(detentor.getEmail());

		if (detentorExistente.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(detentorExistente.get());
		}

		for (Endereco item : detentor.getEnderecos()) {
			enderecoRespository.save(item);
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		detentor.setSenha(encoder.encode(detentor.getSenha()));

		Detentor novoDetentor = detentorRepository.save(detentor);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, novoDetentor.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(novoDetentor);
	}

	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		detentorRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Detentor> atualizar(@PathVariable Long id, @Valid @RequestBody Detentor detentor) {

		Detentor detentorSalva = this.detentorRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));

		if (detentorSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}

		BeanUtils.copyProperties(detentor, detentorSalva, "codigo");

		return ResponseEntity.ok(this.detentorRepository.save(detentorSalva));
	}
	
	@PostMapping("/recuperarSenha")
	@ResponseStatus(code = HttpStatus.OK)
	public void recuperarSenha(@RequestBody String email) {
		detentorService.recuperarSenha(email);
	}
}
