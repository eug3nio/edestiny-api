INSERT INTO endereco (longitude, latitude) VALUES ('-25.4852555', '-49.2883482');

INSERT INTO coletor(razao_social, nome_fantasia, cnpj, inscricao_estadual, email, senha, endereco_id, telefone) 
VALUES ('Equipe e-Destiny', 
'e-Destiny', 
'99999999000191',
'', 
'equipe_edestiny@gmail.com', 
'$2a$10$3OAdUNAo5Yi7qoVYAGraL.SM.j3ErbEwziFOEA.kIqb8I5NPJ.8y6',
(SELECT id FROM endereco WHERE longitude = '-25.4852555'),
'41999710324');