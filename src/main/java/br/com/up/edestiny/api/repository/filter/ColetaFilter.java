package br.com.up.edestiny.api.repository.filter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class ColetaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String coletorId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicio;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFim;
	private List<String> situacoes;

	public String getColetorId() {
		return coletorId;
	}

	public void setColetorId(String coletorId) {
		this.coletorId = coletorId;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public List<String> getSituacoes() {
		return situacoes;
	}

	public void setSituacoes(List<String> situacoes) {
		this.situacoes = situacoes;
	}
}
