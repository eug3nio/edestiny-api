package br.com.up.edestiny.api.resource;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.up.edestiny.api.model.Categoria;
import br.com.up.edestiny.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
}
