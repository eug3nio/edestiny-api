CREATE TABLE aplicacao (
	id FLOAT AUTO_INCREMENT,
	client VARCHAR(255) NOT NULL,
	senha VARCHAR(80) NOT NULL,
	scopes VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO aplicacao(client, senha, scopes) VALUES ('monitoramento', '$2a$10$gyqK5Zu/q0B6vWF11CygX.HwPV83a2gcYnpvCWGmzPrWBp2UWxJ2C', 'read, write');
INSERT INTO aplicacao(client, senha, scopes) VALUES ('detentor', '$2a$10$gyqK5Zu/q0B6vWF11CygX.HwPV83a2gcYnpvCWGmzPrWBp2UWxJ2C', 'read, write');
INSERT INTO aplicacao(client, senha, scopes) VALUES ('coletor', '$2a$10$gyqK5Zu/q0B6vWF11CygX.HwPV83a2gcYnpvCWGmzPrWBp2UWxJ2C', 'read, write');
