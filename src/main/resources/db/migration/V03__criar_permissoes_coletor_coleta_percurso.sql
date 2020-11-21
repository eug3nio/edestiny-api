CREATE TABLE permissao (
	id FLOAT PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	usuario_id FLOAT NOT NULL,
	permissao_id FLOAT NOT NULL,
	PRIMARY KEY (usuario_id, permissao_id),
	FOREIGN KEY (usuario_id) REFERENCES usuario(id),
	FOREIGN KEY (permissao_id) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE percurso (
	id FLOAT NOT NULL AUTO_INCREMENT,
	coleta_id FLOAT NOT NULL,
	dt_criacao DATE NOT NULL,
	json_percurso TEXT,
	PRIMARY KEY (id),
  	FOREIGN KEY (coleta_id) REFERENCES coleta(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

