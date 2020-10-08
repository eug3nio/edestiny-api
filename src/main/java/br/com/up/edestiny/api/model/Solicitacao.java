package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private Detentor solicitante;
	
	private List<Residuo> residuos;

	@Enumerated(EnumType.STRING)
	private SituacaoSolicitacao situacao;
	private String justificativa;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

}
