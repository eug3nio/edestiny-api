package br.com.up.edestiny.api.model.enums;

public enum SituacaoUrna {

	CHEIA(0, "Cheia"), ABERTA(1, "Aberta"), FECHADA(2, "Fechada");

	private int codigo;
	private String descricao;

	private SituacaoUrna(int codigo, String descricao) {
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
