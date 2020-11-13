package br.com.up.edestiny.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private String nomeUsuario;
	private boolean admin;

	public UsuarioSistema(String nomeUsuario, boolean admin, String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.nomeUsuario = nomeUsuario;
		this.admin = admin;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public boolean isAdmin() {
		return admin;
	}

}
