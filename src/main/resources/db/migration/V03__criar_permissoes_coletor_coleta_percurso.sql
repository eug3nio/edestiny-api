CREATE TABLE coletor (
  id FLOAT NOT NULL AUTO_INCREMENT,
  razao_social VARCHAR(255) NOT NULL,
  nome_fantasia VARCHAR(255),
  cnpj VARCHAR(14) NOT NULL,
  inscricao_estadual VARCHAR(12),
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(72) NOT NULL,
  endereco_id FLOAT NOT NULL,
  telefone VARCHAR(15) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (endereco_id) REFERENCES endereco(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

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

CREATE TABLE detentor_permissao (
	detentor_id FLOAT NOT NULL,
	permissao_id FLOAT NOT NULL,
	PRIMARY KEY (detentor_id, permissao_id),
	FOREIGN KEY (detentor_id) REFERENCES detentor(id),
	FOREIGN KEY (permissao_id) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE coletor_permissao (
	coletor_id FLOAT NOT NULL,
	permissao_id FLOAT NOT NULL,
	PRIMARY KEY (coletor_id, permissao_id),
	FOREIGN KEY (coletor_id) REFERENCES coletor(id),
	FOREIGN KEY (permissao_id) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE coleta (
  id FLOAT NOT NULL AUTO_INCREMENT,
  coletor_id FLOAT NOT NULL,
  situacao VARCHAR(255),
  dt_movimentacao DATE NOT NULL,
  dt_prevista_coleta DATE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (coletor_id) REFERENCES coletor(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE coleta_solicitacoes (
  coleta_id FLOAT NOT NULL,
  solicitacao_id FLOAT NOT NULL,
  FOREIGN KEY (coleta_id) REFERENCES coleta(id),
  FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE percurso (
	id FLOAT NOT NULL AUTO_INCREMENT,
	coleta_id FLOAT NOT NULL,
	dt_criacao DATE NOT NULL,
	json_percurso TEXT NOT NULL,
	PRIMARY KEY (id),
  	FOREIGN KEY (coleta_id) REFERENCES coleta(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

