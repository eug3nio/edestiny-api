package br.com.up.edestiny.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.up.edestiny.api.config.EdestinyApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(EdestinyApiProperty.class)
public class EdestinyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdestinyApplication.class, args);
	}

}
