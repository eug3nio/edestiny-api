package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
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
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Endereco;
import br.com.up.edestiny.api.repository.DetentorRepository;
import br.com.up.edestiny.api.repository.EnderecoRespository;

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
	
	@GetMapping
	public List<Detentor> listar() {
		return detentorRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Detentor> novoDetentor(@Valid @RequestBody Detentor detentor, HttpServletResponse response) {
		Detentor detentorExistente = detentorRepository.findByEmail(detentor.getEmail());

		if (detentorExistente != null) {
			return ResponseEntity.status(HttpStatus.OK).body(detentorExistente);
		}

		for (Endereco item : detentor.getEnderecos()) {
			enderecoRespository.save(item);
		}

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(detentor.getSenha().getBytes(), 0, detentor.getSenha().length());
			detentor.setSenha(new BigInteger(1, md.digest()).toString(16));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

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
}
