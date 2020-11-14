package br.com.up.edestiny.api.repository.filter;

import java.io.Serializable;

public class UrnaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String detalhamento;
	private String tipoMedida;

	public String getDetalhamento() {
		return detalhamento;
	}

	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}

	public String getTipoMedida() {
		return tipoMedida;
	}

	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}
}
