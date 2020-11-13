package br.com.up.edestiny.api.repository.urna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.up.edestiny.api.model.Urna;
import br.com.up.edestiny.api.repository.dto.UrnaDTO;
import br.com.up.edestiny.api.repository.filter.UrnaFilter;

public interface UrnaRepositoryQuery {

	public Page<Urna> filtrar(UrnaFilter filter, Pageable pageable);
	public Page<UrnaDTO> resumir(UrnaFilter filter, Pageable pageable);
}
