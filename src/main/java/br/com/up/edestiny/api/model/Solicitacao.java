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

import br.com.up.edestiny.api.model.enums.SituacaoSolicitacao;

@Entity
@Table(name = "solicitacao")
public class Solicitacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "solicitante_id")
	@JsonIgnoreProperties({ "enderecos" })
	private Detentor solicitante;

	@OneToMany(mappedBy = "solicitacao", orphanRemoval = true)
	@JsonIgnoreProperties({ "solicitacao" })
	private List<Residuo> residuos;

	@Enumerated(EnumType.STRING)
	private SituacaoSolicitacao situacao;
	private String justificativa;

	@NotNull
	@Column(name = "dt_solicitacao")
	private LocalDate dtSolicitacao;

	@ManyToOne
	@JoinColumn(name = "coleta_id", referencedColumnName = "id")
	@JsonIgnoreProperties({ "solicitacoes" })
	private Coleta coleta;

	private Integer distancia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Detentor getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Detentor solicitante) {
		this.solicitante = solicitante;
	}

	public List<Residuo> getResiduos() {
		return residuos;
	}

	public void setResiduos(List<Residuo> residuos) {
		this.residuos = residuos;
	}

	public SituacaoSolicitacao getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoSolicitacao situacao) {
		this.situacao = situacao;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public LocalDate getDtSolicitacao() {
		return dtSolicitacao;
	}

	public void setDtSolicitacao(LocalDate dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}

	public Coleta getColeta() {
		return coleta;
	}

	public void setColeta(Coleta coleta) {
		this.coleta = coleta;
	}

	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
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
		Solicitacao other = (Solicitacao) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int compareTo(Solicitacao item) {
		return this.distancia - item.getDistancia();
	}

}
