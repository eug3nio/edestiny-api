INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade, estado) 
VALUES ('Praça Gen. Osório', '125', '', 'Centro', '80020010', 'Curitiba', 'PR');

INSERT INTO empresa(razao_social, nome_fantasia, cnpj, inscricao_estadual, email_empresa, endereco_id, fone)
VALUES
('E-Destiny LTDA.',
'Empresa e-Destiny',
'99999999000191',
'',
'equipe.edestinyup@gmail.com',
(SELECT id FROM endereco WHERE cep = '80020010'),
'41999710324');

INSERT INTO usuario(nome, email, senha, admin, empresa_id) 
VALUES (
'admin', 
'equipe.edestinyup@gmail.com', '$2a$10$3OAdUNAo5Yi7qoVYAGraL.SM.j3ErbEwziFOEA.kIqb8I5NPJ.8y6', 
true,
(SELECT id FROM empresa WHERE cnpj = '99999999000191'));
