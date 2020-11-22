package br.com.up.edestiny.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.up.edestiny.api.config.EdestinyApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	@Autowired
	private EdestinyApiProperty edestinyApiProperty;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String origin = request.getHeader("Origin");

		if (origin != null) {
			if (origin.equals(edestinyApiProperty.getOriginPermitidaColetor())) {
				response.setHeader("Access-Control-Allow-Origin", edestinyApiProperty.getOriginPermitidaColetor());
			} else if (origin.equals(edestinyApiProperty.getOriginPermitidaMonitoramento())) {
				response.setHeader("Access-Control-Allow-Origin",
						edestinyApiProperty.getOriginPermitidaMonitoramento());
			} else if (origin.equals(edestinyApiProperty.getOriginPermitidaDetentor())) {
				response.setHeader("Access-Control-Allow-Origin", edestinyApiProperty.getOriginPermitidaDetentor());
			}
		}

		response.setHeader("Access-Control-Allow-Credentials", "true");

		if ("OPTIONS".equals(request.getMethod())
				&& (edestinyApiProperty.getOriginPermitidaColetor().equals(request.getHeader("Origin"))
						|| edestinyApiProperty.getOriginPermitidaMonitoramento().equals(request.getHeader("Origin"))
						|| edestinyApiProperty.getOriginPermitidaDetentor().equals(request.getHeader("Origin")))) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			response.setHeader("Access-Control-Max-Age", "3600");

			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}

	}

}
