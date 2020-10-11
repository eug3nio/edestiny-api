package br.com.up.edestiny.api.model.enums;

public enum SituacaoColeta {

	EM_DIGITACAO(0, "Em digitação"), EM_ANDAMENTO(1, "Em andamento"), FINALIZADA(2, "Finalizada");

	private int codigo;
	private String descricao;

	private SituacaoColeta(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}
