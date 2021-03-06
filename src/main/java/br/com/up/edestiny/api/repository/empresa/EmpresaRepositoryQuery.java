package br.com.up.edestiny.api.repository.empresa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.up.edestiny.api.model.Empresa;
import br.com.up.edestiny.api.repository.dto.EmpresaDTO;
import br.com.up.edestiny.api.repository.filter.EmpresaFilter;

public interface EmpresaRepositoryQuery {

	public Page<Empresa> filtrar(EmpresaFilter filter, Pageable pageable);

	public Page<EmpresaDTO> resumir(EmpresaFilter filter, Pageable pageable);
	
	public List<Empresa> listByRazaoSocial(String razaoSocial);
}
