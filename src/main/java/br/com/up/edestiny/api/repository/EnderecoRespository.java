package br.com.up.edestiny.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.up.edestiny.api.model.Endereco;

public interface EnderecoRespository extends JpaRepository<Endereco, Long> {

}
