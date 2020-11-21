CREATE TABLE endereco (
  	id FLOAT NOT NULL AUTO_INCREMENT,
  	logradouro VARCHAR(255),
	numero VARCHAR(30),
	complemento VARCHAR(255),
	bairro VARCHAR(255),
	cep VARCHAR(8),
	cidade VARCHAR(255),
	estado VARCHAR(50),
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE detentor (
  id FLOAT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(72) NOT NULL,
  telefone VARCHAR(15) NOT NULL,
  endereco_id FLOAT NOT NULL,
  foto_perfil LONGBLOB,
  PRIMARY KEY (id),
  FOREIGN KEY (endereco_id) REFERENCES endereco(id),
  UNIQUE (email)
 ) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE categoria (
  id FLOAT NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(100) NOT NULL,
  ativo BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

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

CREATE TABLE coleta (
  id FLOAT NOT NULL AUTO_INCREMENT,
  coletor_id FLOAT NOT NULL,
  situacao VARCHAR(255),
  dt_movimentacao DATE,
  dt_prevista_coleta DATE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (coletor_id) REFERENCES coletor(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE solicitacao (
	id FLOAT NOT NULL AUTO_INCREMENT,
	solicitante_id FLOAT NOT NULL,
	situacao VARCHAR(255) NOT NULL,
    coleta_id FLOAT,
	justificativa TEXT,
	PRIMARY KEY (id),
  	FOREIGN KEY (solicitante_id) REFERENCES detentor(id),
  	FOREIGN KEY (coleta_id) REFERENCES coleta(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE residuo (
  id FLOAT NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(255) NOT NULL,
  quantidade DOUBLE(10,3) NOT NULL,
  unidade_medida VARCHAR(255) NOT NULL,
  categoria_id FLOAT NOT NULL,
  solicitacao_id FLOAT,
  PRIMARY KEY (id),
  FOREIGN KEY (categoria_id) REFERENCES categoria(id),
  FOREIGN KEY (solicitacao_id) REFERENCES solicitacao(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


