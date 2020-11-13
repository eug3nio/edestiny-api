package br.com.up.edestiny.api.model.enums;

public enum TipoMedida {

	PESO(0, "Peso"), QUANTIDADE(1, "Quantidade"), VOLUME(2, "Volume");

	private int codigo;
	private String descricao;

	private TipoMedida(int codigo, String descricao) {
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
	 * @param codigo
	 * @return
	 */
	public static TipoMedida findByCodigo(Integer codigo) {
		for (TipoMedida item : TipoMedida.values()) {
			if (item.getCodigo() == codigo) {
				return item;
			}
		}

		return null;
	}
}
