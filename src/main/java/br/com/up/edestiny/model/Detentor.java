package br.com.up.edestiny.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Detentor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String nome;
	private String email;
	private String senha;
	private String telefone;
	private byte[] fotoPerfil;
	private List<Endereco> enderecos;

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
		Detentor other = (Detentor) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
