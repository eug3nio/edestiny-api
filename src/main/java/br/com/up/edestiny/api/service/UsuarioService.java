package br.com.up.edestiny.api.service;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.up.edestiny.api.model.Usuario;
import br.com.up.edestiny.api.repository.UsuarioRepository;

@Service
public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	public Usuario atualizarUsuario(Long id, Usuario usuario) {
		Optional<Usuario> usuarioSalvo = usuarioRepository.findById(id);

		if (usuarioSalvo.isPresent()) {
			usuarioSalvo.get().getUrnas().clear();
			usuarioSalvo.get().getUrnas().addAll(usuario.getUrnas());
			usuarioSalvo.get().getUrnas().forEach(it -> {
				it.setUsuarioResponsavel(usuarioSalvo.get());
				it.setEmpresa(usuarioSalvo.get().getEmpresa());
			});

			BeanUtils.copyProperties(usuario, usuarioSalvo.get(), "id", "empresa", "urnas");
			return usuarioRepository.save(usuarioSalvo.get());
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	/**
	 * 
	 * @param senha
	 * @return
	 */
	public String obterSenhaBCrypt(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}

	/**
	 * 
	 * @param email
	 */
	public void recuperarSenha(String email) {
		Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);

		if (optUsuario.isPresent()) {
			String novaSenha = gerarSenhaAleatoria();
			optUsuario.get().setSenha(obterSenhaBCrypt(novaSenha));

			SimpleMailMessage emailSender = new SimpleMailMessage();
			emailSender.setTo(email);
			emailSender.setSubject("Recuperação de senha");
			emailSender.setText("Nova senha do usuário " + optUsuario.get().getNome() + ": " + novaSenha);
			javaMailSender.send(emailSender);

			usuarioRepository.save(optUsuario.get());
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	private static String gerarSenhaAleatoria() {
		int qtdeMaximaCaracteres = 8;
		String[] caracteres = { "a", "1", "b", "2", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B",
				"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z" };

		StringBuilder senha = new StringBuilder();

		for (int i = 0; i < qtdeMaximaCaracteres; i++) {
			int posicao = (int) (Math.random() * caracteres.length);
			senha.append(caracteres[posicao]);
		}
		return senha.toString();
	}

}
