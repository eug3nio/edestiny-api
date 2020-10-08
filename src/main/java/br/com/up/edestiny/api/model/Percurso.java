package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Percurso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private Coleta coleta;
	private Date dtCriacao;
	private String jsonPercurso;
}
