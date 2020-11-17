package br.com.up.edestiny.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private String nomeUsuario;
	private Long empresaId;
	private boolean admin;

	public UsuarioSistema(String nomeUsuario, Long empresaId, boolean admin, String username, String password,
			boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.nomeUsuario = nomeUsuario;
		this.empresaId = empresaId;
		this.admin = admin;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public boolean isAdmin() {
		return admin;
	}

}
