package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.up.edestiny.api.model.enums.SituacaoColeta;

@Entity
@Table(name = "coleta")
public class Coleta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "coletor_id")
	private Coletor coletor;

	@OneToMany(mappedBy = "coleta")
	@JsonIgnoreProperties({ "coleta" })
	private List<Solicitacao> solicitacoes;

	@Enumerated(EnumType.STRING)
	private SituacaoColeta situacao;

	@Column(name = "dt_movimentacao")
	private LocalDate dtMovimentacao;

	@NotNull
	@Column(name = "dt_prevista_coleta")
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
