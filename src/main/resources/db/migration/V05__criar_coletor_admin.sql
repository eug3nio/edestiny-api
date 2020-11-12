INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade, estado) 
VALUES ('Rua João Stenzowski', '416', '', 'Novo Mundo', '81020120', 'Curitiba', 'PR');

INSERT INTO coletor(razao_social, nome_fantasia, cnpj, inscricao_estadual, email, senha, endereco_id, telefone) 
VALUES ('Equipe e-Destiny', 
'e-Destiny', 
'99999999000191',
'', 
'equipe.edestinyup@gmail.com', 
'$2a$10$3OAdUNAo5Yi7qoVYAGraL.SM.j3ErbEwziFOEA.kIqb8I5NPJ.8y6',
(SELECT id FROM endereco WHERE cep = '81020120'),
'41999710324');