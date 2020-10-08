package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.com.up.edestiny.api.model.enums.SituacaoUrna;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ControleUrna implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private Urna urna;
	private Usuario usuarioResponsavel;
	private SituacaoUrna situacao;
	private BigDecimal qtdMovimentacao;
	private Date dtMovimentacao;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		ControleUrna other = (ControleUrna) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
