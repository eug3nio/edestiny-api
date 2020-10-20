package br.com.up.edestiny.api.security;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class AppClientDetailsService implements ClientDetailsService {

	@Override
	public ClientDetails loadClientByClientId(String aplicacao) throws ClientRegistrationException {
		// TODO Auto-generated method stub
		return null;
	}

}
