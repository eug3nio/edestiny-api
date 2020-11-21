package br.com.up.edestiny.api.repository.dto;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.up.edestiny.api.model.Solicitacao;

public class SolicitacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String solicitante;
	private String endereco;
	private Integer qtdResiduos;
	private LocalDate dtSolicitacao;

	public SolicitacaoDTO(Solicitacao solicitacao) {
		this.id = solicitacao.getId();
		this.solicitante = solicitacao.getSolicitante().getNome();
		this.qtdResiduos = solicitacao.getResiduos().size();
		this.dtSolicitacao = solicitacao.getDtSolicitacao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getQtdResiduos() {
		return qtdResiduos;
	}

	public void setQtdResiduos(Integer qtdResiduos) {
		this.qtdResiduos = qtdResiduos;
	}

	public LocalDate getDtSolicitacao() {
		return dtSolicitacao;
	}

	public void setDtSolicitacao(LocalDate dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}

}
