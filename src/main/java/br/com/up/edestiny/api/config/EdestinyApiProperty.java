package br.com.up.edestiny.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("edestiny")
public class EdestinyApiProperty {

	private String originPermitidaDetentor = "http://localhost:4200";

	private String originPermitidaMonitoramento = "http://localhost:4200";

	private String originPermitidaColetor = "http://localhost:4200";

	private String apiKeyGoogle = "AIzaSyDZJfPgnA3e77_iJxb_cMmTihlapPNYKwk";

	private final Seguranca seguranca = new Seguranca();

	public static class Seguranca {
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

	}

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public String getOriginPermitidaDetentor() {
		return originPermitidaDetentor;
	}

	public void setOriginPermitidaDetentor(String originPermitidaDetentor) {
		this.originPermitidaDetentor = originPermitidaDetentor;
	}

	public String getOriginPermitidaMonitoramento() {
		return originPermitidaMonitoramento;
	}

	public void setOriginPermitidaMonitoramento(String originPermitidaMonitoramento) {
		this.originPermitidaMonitoramento = originPermitidaMonitoramento;
	}

	public String getOriginPermitidaColetor() {
		return originPermitidaColetor;
	}

	public void setOriginPermitidaColetor(String originPermitidaColetor) {
		this.originPermitidaColetor = originPermitidaColetor;
	}

	public String getApiKeyGoogle() {
		return apiKeyGoogle;
	}
}
