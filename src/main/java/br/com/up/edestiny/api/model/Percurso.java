package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "percurso")
public class Percurso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "coleta_id")
	@NotNull
	@JsonIgnoreProperties({ "percurso" })
	private Coleta coleta;

	@NotNull
	@Column(name = "dt_criacao")
	private LocalDate dtCriacao;

	@Column(name = "json_percurso")
	private String jsonPercurso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Coleta getColeta() {
		return coleta;
	}

	public void setColeta(Coleta coleta) {
		this.coleta = coleta;
	}

	public LocalDate getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(LocalDate dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getJsonPercurso() {
		return jsonPercurso;
	}

	public void setJsonPercurso(String jsonPercurso) {
		this.jsonPercurso = jsonPercurso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Percurso other = (Percurso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
