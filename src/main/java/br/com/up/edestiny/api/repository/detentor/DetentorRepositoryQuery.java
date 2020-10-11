package br.com.up.edestiny.api.repository.detentor;

import br.com.up.edestiny.api.model.Detentor;

public interface DetentorRepositoryQuery {
	public Detentor findByEmail(String email);
}
