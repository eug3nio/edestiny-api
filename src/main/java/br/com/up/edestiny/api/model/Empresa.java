package br.com.up.edestiny.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "razao_social")
	@NotNull
	@Size(min = 3, max = 255)
	private String razaoSocial;

	@Column(name = "nome_fantasia")
	@Size(min = 3, max = 255)
	private String nomeFantasia;

	@NotNull
	@Size(min = 14, max = 14)
	private String cnpj;

	@NotNull
	@Size(min = 3, max = 255)
	@Email
	private String email;

	@Column(name = "inscricao_estadual")
	@Size(min = 12, max = 12)
	private String inscricaoEstadual;

	@NotNull
	@Size(min = 11, max = 15)
	private String fone;

	@OneToOne
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;

	@OneToMany
	@JoinTable(name = "empresa_urna", joinColumns = @JoinColumn(name = "empresa_id"), inverseJoinColumns = @JoinColumn(name = "urna_id"))
	private List<Urna> urnas;

	@OneToMany
	@JoinTable(name = "empresa_usuario", joinColumns = @JoinColumn(name = "empresa_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private List<Usuario> usuarios;

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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Urna> getUrnas() {
		return urnas;
	}

	public void setUrnas(List<Urna> urnas) {
		this.urnas = urnas;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

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
		Empresa other = (Empresa) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
