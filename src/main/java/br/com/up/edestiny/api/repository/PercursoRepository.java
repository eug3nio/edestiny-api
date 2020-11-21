package br.com.up.edestiny.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.model.Percurso;

public interface PercursoRepository extends JpaRepository<Percurso, Long> {

	List<Percurso> findByColeta(Coleta coleta);
	
}
