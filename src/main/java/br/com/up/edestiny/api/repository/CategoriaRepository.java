package br.com.up.edestiny.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
