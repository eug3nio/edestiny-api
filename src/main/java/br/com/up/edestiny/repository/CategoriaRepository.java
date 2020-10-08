package br.com.up.edestiny.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
