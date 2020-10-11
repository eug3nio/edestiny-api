package br.com.up.edestiny.api.model.enums;

public enum UnidadeMedida {

	KG(0, "KG"), QUANTIDADE(1, "Quantidade");

	private int codigo;
	private String descricao;

	private UnidadeMedida(int codigo, String descricao) {
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
