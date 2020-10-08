package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.up.edestiny.api.model.enums.TipoMedida;

public class Urna implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String detalhamento;
	private TipoMedida tipoMedida;
	private BigDecimal qtdAtual;
	private BigDecimal qtdMaxima;
	private Empresa empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetalhamento() {
		return detalhamento;
	}

	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}

	public TipoMedida getTipoMedida() {
		return tipoMedida;
	}

	public void setTipoMedida(TipoMedida tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	public BigDecimal getQtdAtual() {
		return qtdAtual;
	}

	public void setQtdAtual(BigDecimal qtdAtual) {
		this.qtdAtual = qtdAtual;
	}

	public BigDecimal getQtdMaxima() {
		return qtdMaxima;
	}

	public void setQtdMaxima(BigDecimal qtdMaxima) {
		this.qtdMaxima = qtdMaxima;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Urna other = (Urna) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
