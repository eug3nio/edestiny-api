package br.com.up.edestiny.api.repository.dto;

import java.io.Serializable;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import br.com.up.edestiny.api.model.Empresa;

public class EmpresaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String email;

	public EmpresaDTO(Empresa empresa) {
		this.id = empresa.getId();
		this.razaoSocial = empresa.getRazaoSocial();
		this.setNomeFantasia(empresa.getNomeFantasia());
		
		try {
			MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
			mask.setValueContainsLiteralCharacters(false);
			this.cnpj = mask.valueToString(empresa.getCnpj());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.email = empresa.getEmail();
	}

	public EmpresaDTO(Long id, String razaoSocial, String cnpj, String email) {
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
