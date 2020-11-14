package br.com.up.edestiny.api.repository.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.up.edestiny.api.model.Urna;

public class UrnaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String detalhamento;
	private String tipoMedida;
	private BigDecimal qtdAtual;
	private BigDecimal qtdMaxima;
	private String empresa;

	public UrnaDTO(Urna urna) {
		this.id = urna.getId();
		this.detalhamento = urna.getDetalhamento();
		this.tipoMedida = urna.getTipoMedida().getDescricao();
		this.qtdAtual = urna.getQtdAtual();
		this.qtdMaxima = urna.getQtdMaxima();
		this.empresa = urna.getEmpresa().getRazaoSocial();
	}

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

	public String getTipoMedida() {
		return tipoMedida;
	}

	public void setTipoMedida(String tipoMedida) {
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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
}
