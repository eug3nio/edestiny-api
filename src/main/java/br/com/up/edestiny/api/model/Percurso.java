package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Percurso implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Coleta coleta;
	private LocalDate dtCriacao;
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
}
