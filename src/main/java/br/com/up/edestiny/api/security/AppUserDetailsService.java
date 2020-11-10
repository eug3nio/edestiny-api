package br.com.up.edestiny.api.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.up.edestiny.api.model.Coletor;
import br.com.up.edestiny.api.model.Detentor;
import br.com.up.edestiny.api.model.Permissao;
import br.com.up.edestiny.api.model.Usuario;
import br.com.up.edestiny.api.repository.ColetorRepository;
import br.com.up.edestiny.api.repository.DetentorRepository;
import br.com.up.edestiny.api.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private DetentorRepository detentorRepository;

	@Autowired
	private ColetorRepository coletorRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

		if (usuario.isPresent()) {
			return new UsuarioSistema(usuario.get().getNome(), email, usuario.get().getSenha(), true, true, true, true,
					getPermissoes(usuario.get().getPermissoes()));
		}

		Optional<Detentor> detentor = detentorRepository.findByEmail(email);

		if (detentor.isPresent()) {
			return new UsuarioSistema(detentor.get().getNome(), email, detentor.get().getSenha(), true, true, true,
					true, getPermissoes(detentor.get().getPermissoes()));
		}

		Optional<Coletor> coletor = coletorRepository.findByEmail(email);

		if (coletor.isPresent()) {
			return new UsuarioSistema(coletor.get().getRazaoSocial(), email, coletor.get().getSenha(), true, true, true,
					true, getPermissoes(coletor.get().getPermissoes()));
		}

		new UsernameNotFoundException("Usuário e/ou senha incorretos");
		return null;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	private Collection<? extends GrantedAuthority> getPermissoes(List<Permissao> list) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		list.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}

}
