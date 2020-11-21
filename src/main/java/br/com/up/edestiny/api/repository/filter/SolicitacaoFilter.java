package br.com.up.edestiny.api.repository.filter;

import java.io.Serializable;

public class SolicitacaoFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idColeta;

	public String getIdColeta() {
		return idColeta;
	}

	public void setIdColeta(String idColeta) {
		this.idColeta = idColeta;
	}
}
