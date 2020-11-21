--Insert de Categorias


INSERT INTO `edestiny`.`categoria` (`descricao`,`ativo`)
VALUES
('Smartphone', '1');

INSERT INTO `edestiny`.`categoria`(`descricao`,`ativo`)
VALUES
('Computadores', '1');

INSERT INTO `edestiny`.`categoria`(`descricao`,`ativo`)
VALUES
( 'Tablet','1');

--Insert de Detentor

INSERT INTO `edestiny`.`detentor`(`nome`,`email`,`senha`,`telefone`,`foto_perfil`)
VALUES
('Ewerton','ewerton.cap@hotmail.com','123','996495906',null);

INSERT INTO `edestiny`.`detentor`(`nome`,`email`,`senha`,`telefone`,`foto_perfil`)
VALUES
('Eugenio','eugenio@hotmail.com','123','33333333',null);

INSERT INTO `edestiny`.`detentor`(`nome`,`email`,`senha`,`telefone`,`foto_perfil`)
VALUES
('Gustavo','gustavo@hotmail.com','123','9999999',null);

INSERT INTO `edestiny`.`detentor`(`nome`,`email`,`senha`,`telefone`,`foto_perfil`)
VALUES
('Pedro','pedro@hotmail.com','123','11111111',null);


--Inseert de Solicitações

INSERT INTO `edestiny`.`solicitacao`(`solicitante_id`,`situacao`,`coleta_id`,`justificativa`,`dt_solicitacao`)
VALUES
('1','ABERTA',null,null,'2020-11-20');

INSERT INTO `edestiny`.`solicitacao`(`solicitante_id`,`situacao`,`coleta_id`,`justificativa`,`dt_solicitacao`)
VALUES
('2','ABERTA',null,null,'2020-11-21');


INSERT INTO `edestiny`.`solicitacao`(`solicitante_id`,`situacao`,`coleta_id`,`justificativa`,`dt_solicitacao`)
VALUES
('3','ABERTA',null,null,'2020-11-22');

INSERT INTO `edestiny`.`solicitacao`(`solicitante_id`,`situacao`,`coleta_id`,`justificativa`,`dt_solicitacao`)
VALUES
('4','ABERTA',null,null,'2020-11-23');
SELECT * FROM edestiny.solicitacao;

--Insert de Residuos

INSERT INTO `edestiny`.`residuo`(`descricao`,`quantidade`,`unidade_medida`,`categoria_id`,`solicitacao_id`)
VALUES
('Celular','1','QUANTIDADE','2','1');

INSERT INTO `edestiny`.`residuo`(`descricao`,`quantidade`,`unidade_medida`,`categoria_id`,`solicitacao_id`)
VALUES
('Tabet','1','QUANTIDADE','2','2');

INSERT INTO `edestiny`.`residuo`(`descricao`,`quantidade`,`unidade_medida`,`categoria_id`,`solicitacao_id`)
VALUES
('Notebook','1','KG','2','3');


