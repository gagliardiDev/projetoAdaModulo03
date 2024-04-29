INSERT INTO cliente (nome, cpf, data_nascimento) VALUES ('Machado de Assis', '012345678', '1990-01-01');
INSERT INTO cliente (nome, cpf, data_nascimento) VALUES ('Clarice Lispector','876543210', '1990-06-06');
INSERT INTO cliente (nome, cpf, data_nascimento) VALUES ('Jorge Amado','024567890', '1990-05-05');
INSERT INTO cliente (nome, cpf, data_nascimento) VALUES ('Graciliano Ramos','11222233', '1990-04-04');
INSERT INTO cliente (nome, cpf, data_nascimento) VALUES ('Cecília Meireles', '4444555566', '1981-04-01');
INSERT INTO cliente (nome, cpf, data_nascimento) VALUES ('João Guimarães Rosa','00111010', '2000-01-01');
INSERT INTO cliente (nome, cpf, data_nascimento) VALUES ('Carlos Drummond de Andrade', '0000011111', '1890-01-01');
INSERT INTO cliente (nome, cpf, data_nascimento) VALUES ('Érico Veríssimo', '1111222233444', '1980-05-01');




INSERT INTO conta (cliente_id, saldo)
VALUES (1, 100);

INSERT INTO conta (cliente_id, saldo)
VALUES (2, 200);
INSERT INTO conta (cliente_id, saldo)
VALUES (3, 300);
INSERT INTO conta (cliente_id, saldo)
VALUES (4, 400);
INSERT INTO conta (cliente_id, saldo)
VALUES (5, 400);






insert into usuario (nome, account_expired, account_locked, active, cpf, credentials_expired, email, password, roles, telefone, username) VALUES
('Admin',false,false,true,'07120220403',false,'user@admin.com' ,'$2a$12$yrp1A9bCUvgGjOjY.sf4GeSmFIRUR3B.7naToAD5OuBpG/VKgG2EW','ADMIN','99999999','user_admin');

insert into usuario (nome, account_expired, account_locked, active, cpf, credentials_expired, email, password, roles, telefone, username) VALUES
('Cliente',false,false,true,'42118950012',false,'user@cliente.com' ,'$2a$12$h8tyqe5VmXqmKwWmmKOLV.Wl038pn5zQIkyinuws06pIVhzCuWuju','CLIENTE','99999999','user_cliente');

insert into usuario (nome, account_expired, account_locked, active, cpf, credentials_expired, email, password, roles, telefone, username) VALUES
    ('Funcionario',false,false,true,'88385302034',false,'user@funcionario.com' ,'$2a$12$JLicT3XBxu9l/KK5ceE8KOLDp7oopCGXo2eOkQsQT9Jeosp2UOZf6','FUNCIONARIO','99999999','user_funcionario');