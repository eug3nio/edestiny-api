package br.com.up.edestiny.api.config.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.com.up.edestiny.api.security.UsuarioSistema;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();

		Map<String, Object> addInfo = new HashMap<>();
		addInfo.put("nome", usuarioSistema.getNomeUsuario());
		addInfo.put("empresaId", usuarioSistema.getEmpresaId());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
		return accessToken;
	}

}
