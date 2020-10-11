package br.com.up.edestiny.api.model.enums;

public enum SituacaoSolicitacao {

	ABERTA(0, "Aberta"), EM_ATENDIMENTO(1, "Em atendimento"), COLETADA(2, "Coletada"), FINALIZADA(3, "Finalizada");

	private int codigo;
	private String descricao;

	private SituacaoSolicitacao(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	/**
	 * 
	 * @param descricao
	 * @return
	 */
	public static SituacaoSolicitacao findByDescricao(String descricao) {
		for (SituacaoSolicitacao item : SituacaoSolicitacao.values()) {
			if (item.toString().equals(descricao)) {
				return item;
			}
		}

		return null;
	}
}
