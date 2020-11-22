--Insert de Categorias


INSERT INTO categoria (descricao,ativo)
VALUES
('Smartphone', '1');

INSERT INTO categoria(descricao,ativo)
VALUES
('Computadores', '1');

INSERT INTO categoria(descricao,ativo)
VALUES
( 'Tablet','1');

-- INSERT ENDERECO DETENTOR

INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade, estado) 
VALUES ('Rua Demétrio Romaniuk', '100', '', 'Fazendinha', '81070474', 'Curitiba', 'PR');

INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade, estado) 
VALUES ('Rua Lauro Schleder', '100', '', 'Boa Vista', '82650312', 'Curitiba', 'PR');

INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade, estado) 
VALUES ('Travessa José Vicente Filho', '100', '', 'Santa Cândida', '82650455', 'Curitiba', 'PR');

INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade, estado) 
VALUES ('Rua Crescência Bertoldi', '100', '', 'Rua Crescência Bertoldi', '81490476', 'Curitiba', 'PR');

--Insert de Detentor

INSERT INTO detentor(nome,email,senha,telefone,foto_perfil, endereco_id)
VALUES
('Ewerton','ewerton.cap@hotmail.com','123','996495906', null, (SELECT id FROM endereco WHERE cep = '81070474'));

INSERT INTO detentor(nome,email,senha,telefone,foto_perfil, endereco_id)
VALUES
('Eugenio','eugenio@hotmail.com','123','33333333',null, (SELECT id FROM endereco WHERE cep = '82650312'));

INSERT INTO detentor(nome,email,senha,telefone,foto_perfil, endereco_id)
VALUES
('Gustavo','gustavo@hotmail.com','123','9999999',null, (SELECT id FROM endereco WHERE cep = '82650455'));

INSERT INTO detentor(nome,email,senha,telefone,foto_perfil, endereco_id)
VALUES
('Pedro','pedro@hotmail.com','123','11111111',null, (SELECT id FROM endereco WHERE cep = '81490476'));


--Inseert de Solicitações

INSERT INTO solicitacao(solicitante_id,situacao,coleta_id,justificativa,dt_solicitacao)
VALUES
('1','ABERTA',null,null,'2020-11-20');

INSERT INTO solicitacao(solicitante_id,situacao,coleta_id,justificativa,dt_solicitacao)
VALUES
('2','ABERTA',null,null,'2020-11-21');


INSERT INTO solicitacao(solicitante_id,situacao,coleta_id,justificativa,dt_solicitacao)
VALUES
('3','ABERTA',null,null,'2020-11-22');

INSERT INTO solicitacao(solicitante_id,situacao,coleta_id,justificativa,dt_solicitacao)
VALUES
('4','ABERTA',null,null,'2020-11-23');

--Insert de Residuos

INSERT INTO residuo(descricao,quantidade,unidade_medida,categoria_id,solicitacao_id)
VALUES
('Celular','1','QUANTIDADE','2','1');

INSERT INTO residuo(descricao,quantidade,unidade_medida,categoria_id,solicitacao_id)
VALUES
('Tabet','1','QUANTIDADE','2','2');

INSERT INTO residuo(descricao,quantidade,unidade_medida,categoria_id,solicitacao_id)
VALUES
('Notebook','1','KG','2','3');


