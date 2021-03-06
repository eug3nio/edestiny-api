CREATE TABLE empresa (
  id FLOAT NOT NULL AUTO_INCREMENT,
  razao_social VARCHAR(255) NOT NULL,
  nome_fantasia VARCHAR(255),
  cnpj VARCHAR(14) NOT NULL,
  inscricao_estadual VARCHAR(12),
  email_empresa VARCHAR(255) NOT NULL,
  endereco_id FLOAT NOT NULL,
  fone VARCHAR(15) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (endereco_id) REFERENCES endereco(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE usuario (
  id FLOAT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  senha VARCHAR(72) NOT NULL,
  foto_perfil LONGBLOB,
  admin BOOLEAN NOT NULL,
  empresa_id FLOAT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (empresa_id) REFERENCES empresa(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

CREATE TABLE urna (
  id FLOAT NOT NULL AUTO_INCREMENT,
  detalhamento VARCHAR(255) NOT NULL,
  tipo_medida VARCHAR(255) NOT NULL,
  quantidade_atual DOUBLE(10,3) NOT NULL,
  quantidade_maxima DOUBLE(10,3) NOT NULL,
  empresa_id FLOAT NOT NULL,
  usuario_id FLOAT,
  PRIMARY KEY (id),
  FOREIGN KEY (empresa_id) REFERENCES empresa(id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

