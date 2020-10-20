CREATE TABLE endereco (
  id FLOAT NOT NULL AUTO_INCREMENT,
  longitude VARCHAR(15) NOT NULL,
  latitude VARCHAR(15) NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE detentor (
  id FLOAT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(72) NOT NULL,
  telefone VARCHAR(15) NOT NULL,
  foto_perfil LONGBLOB,
  PRIMARY KEY (id),
  UNIQUE (email)
 ) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE endereco_detentor (
	detentor_id FLOAT NOT NULL,
	endereco_id FLOAT NOT NULL,
	FOREIGN KEY (detentor_id) REFERENCES detentor(id),
	FOREIGN KEY (endereco_id) REFERENCES endereco(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE categoria (
  id FLOAT NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(100) NOT NULL,
  ativo BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE residuo (
  id FLOAT NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(255) NOT NULL,
  quantidade DOUBLE(10,3) NOT NULL,
  unidade_medida VARCHAR(255) NOT NULL,
  categoria_id FLOAT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (categoria_id) REFERENCES categoria(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE solicitacao (
	id FLOAT NOT NULL AUTO_INCREMENT,
	solicitante_id FLOAT NOT NULL,
	situacao VARCHAR(255) NOT NULL,
	justificativa TEXT,
	PRIMARY KEY (id),
  	FOREIGN KEY (solicitante_id) REFERENCES detentor(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE solicitacao_residuo (
	solicitacao_id FLOAT NOT NULL,
	residuo_id FLOAT NOT NULL,
	FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id),
	FOREIGN KEY (residuo_id) REFERENCES residuo(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

