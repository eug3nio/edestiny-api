package br.com.up.edestiny.api.repository.coleta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.up.edestiny.api.model.Coleta;
import br.com.up.edestiny.api.repository.dto.ColetaDTO;
import br.com.up.edestiny.api.repository.filter.ColetaFilter;

public interface ColetaRepositoryQuery {
	public Page<Coleta> filtrar(ColetaFilter filter, Pageable pageable);

	public Page<ColetaDTO> resumir(ColetaFilter filter, Pageable pageable);
}
