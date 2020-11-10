INSERT INTO endereco (longitude, latitude) VALUES ('-25,4493', '-49,3574');

INSERT INTO empresa(razao_social, nome_fantasia, cnpj, inscricao_estadual, email_empresa, endereco_id, fone)
VALUES
('E-Destiny LTDA.',
'Empresa e-Destiny',
'99999999000191',
'',
'equipe_edestiny@gmail.com',
(SELECT id FROM endereco WHERE longitude = '-25,4493'),
'41999710324');

INSERT INTO empresa_usuario(empresa_id,usuario_id)
VALUES((SELECT id FROM empresa WHERE cnpj = '99999999000191'), (SELECT id FROM usuario WHERE email = 'equipe_edestiny@gmail.com'));
