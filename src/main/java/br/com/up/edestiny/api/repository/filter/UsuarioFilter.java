package br.com.up.edestiny.api.repository.filter;

import java.io.Serializable;

public class UsuarioFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	private String empresaId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(String empresaId) {
		this.empresaId = empresaId;
	}

}
