package br.com.up.edestiny.api.repository.dto;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import br.com.up.edestiny.api.model.Coleta;

public class ColetaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer qtdSoliticoes;
	private String dataPrevista;
	private String situacao;

	public ColetaDTO(Coleta coleta) {
		this.id = coleta.getId();
		this.qtdSoliticoes = coleta.getSolicitacoes().size();
		this.dataPrevista = coleta.getDtPrevistaColeta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.situacao = coleta.getSituacao().getDescricao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQtdSoliticoes() {
		return qtdSoliticoes;
	}

	public void setQtdSoliticoes(Integer qtdSoliticoes) {
		this.qtdSoliticoes = qtdSoliticoes;
	}

	public String getDataPrevista() {
		return dataPrevista;
	}

	public void setDataPrevista(String dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
