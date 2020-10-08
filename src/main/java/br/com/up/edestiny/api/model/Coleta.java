package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import br.com.up.edestiny.api.model.enums.SituacaoColeta;

public class Coleta implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Coletor coletor;
	private List<Solicitacao> solicitacoes;
	private SituacaoColeta situacao;
	private LocalDate dtMovimentacao;
	private LocalDate dtPrevistaColeta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Coletor getColetor() {
		return coletor;
	}

	public void setColetor(Coletor coletor) {
		this.coletor = coletor;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public SituacaoColeta getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoColeta situacao) {
		this.situacao = situacao;
	}

	public LocalDate getDtMovimentacao() {
		return dtMovimentacao;
	}

	public void setDtMovimentacao(LocalDate dtMovimentacao) {
		this.dtMovimentacao = dtMovimentacao;
	}

	public LocalDate getDtPrevistaColeta() {
		return dtPrevistaColeta;
	}

	public void setDtPrevistaColeta(LocalDate dtPrevistaColeta) {
		this.dtPrevistaColeta = dtPrevistaColeta;
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
		Coleta other = (Coleta) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
