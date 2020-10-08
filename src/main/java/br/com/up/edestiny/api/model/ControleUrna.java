package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.up.edestiny.api.model.enums.SituacaoUrna;

public class ControleUrna implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Urna urna;
	private Usuario usuarioResponsavel;
	private SituacaoUrna situacao;
	private BigDecimal qtdMovimentacao;
	private LocalDate dtMovimentacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Urna getUrna() {
		return urna;
	}

	public void setUrna(Urna urna) {
		this.urna = urna;
	}

	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public SituacaoUrna getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoUrna situacao) {
		this.situacao = situacao;
	}

	public BigDecimal getQtdMovimentacao() {
		return qtdMovimentacao;
	}

	public void setQtdMovimentacao(BigDecimal qtdMovimentacao) {
		this.qtdMovimentacao = qtdMovimentacao;
	}

	public LocalDate getDtMovimentacao() {
		return dtMovimentacao;
	}

	public void setDtMovimentacao(LocalDate dtMovimentacao) {
		this.dtMovimentacao = dtMovimentacao;
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
		ControleUrna other = (ControleUrna) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
