--d
--insert cliente particular
CREATE OR REPLACE PROCEDURE insert_cliente_particular(
	IN nif int,
	IN nome varchar,
	IN morada varchar,
	IN telefone varchar,
	IN ref_cliente int,
	IN cc int)
LANGUAGE 'plpgsql'
AS $$
    BEGIN    
        INSERT INTO cliente VALUES (nif,nome,morada,telefone,ref_cliente);
		if(nif not in (SELECT nif FROM cliente)) then
        	RAISE NOTICE 'Cliente nao inserido';        
        END IF;
        INSERT INTO cliente_particular VALUES (cc,nif);
		--check if cliente was correctly added to the DB
    END;
$$;

--test insert_cliente_particular
--call insert_cliente_particular(122226709 ,'o individuo' ,'Rua x','967992934', NULL, 333992934); 

--update cliente particular
--cc and nif can't be changed
--check if parameter is equal to avoid unecessary writes to DB
CREATE OR REPLACE PROCEDURE update_cliente_particular(
	IN nif_to_update int, 
	IN new_nome varchar, 
	IN new_morada varchar, 
	IN new_telefone varchar, 
	IN new_ref_cliente int)
LANGUAGE plpgsql AS
$$
    begin
		--check if nif is valid
		--not sure if this exception should exist
		IF (nif_to_update NOT IN (SELECT nif FROM cliente)) then
			RAISE NOTICE 'Nao existe cliente para esse nif';        
		END IF;
       	UPDATE cliente SET nome = new_nome WHERE nif = nif_to_update;
       	UPDATE cliente SET morada = new_morada WHERE nif = nif_to_update;
       	UPDATE cliente SET telefone = new_telefone WHERE nif = nif_to_update;
       	UPDATE cliente SET ref_cliente = new_ref_cliente WHERE nif = nif_to_update;
	END;
$$;

--test update
--call update_cliente_particular(123456799 ,'outro individuo' , 'ali', '967992934', 123456700);

--remove cliente particular
CREATE OR REPLACE PROCEDURE remove_cliente_particular(
	IN nif_to_delete int,
	IN cc_to_delete int)
LANGUAGE 'plpgsql'
AS $$
    BEGIN
		DELETE FROM Cliente WHERE nif = nif_to_delete;
		UPDATE Cliente set ref_cliente = null WHERE ref_cliente = cc_to_delete;
        DELETE FROM Cliente_Particular WHERE cc = cc_to_delete;
    END;
$$;

--test delete
--call remove_cliente_particular(123456700, 123456700);


--l
CREATE OR REPLACE FUNCTION delete_clientes()
RETURNS trigger AS $$
	DECLARE
	nif_to_delete int:= old.nif;
    BEGIN
       	UPDATE cliente SET ativo = FALSE WHERE nif = nif_to_delete;
		return new;
	END;
$$LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER delete_cliente
BEFORE DELETE ON cliente
FOR EACH ROW
EXECUTE FUNCTION delete_clientes();


--não funciona mas a linha de baixo faz o pretendido
--UPDATE clientes SET ativo = FALSE WHERE nif = 121222333;

--j
--todos_alarmes(matricula, nome, latitude, longitude, marca_temporal)
CREATE OR REPLACE FUNCTION insert_view_alarme()
RETURNS trigger AS $$
	DECLARE
	equip int:= null;
	cond int:= random_number(9);
	eq_id int:= null;
	coord_id int:= null;
	bip_id int:= null;
	cliente_hardcoded int:= 121222333;
    BEGIN
		INSERT INTO Equipamento_Eletronico(estado) values('Inactivo') returning id into eq_id;
		INSERT INTO Condutor(CC, nome, contacto) values(cond, new.nome, null);
       	INSERT INTO veiculo(matricula, condutor, equipamento, cliente) values(new.matricula, cond, eq_id, cliente_hardcoded);
		INSERT INTO Coordenadas(latitude, longitude) VALUES (new.latitude, new.longitude) returning id into coord_id;
		INSERT into Bip_Equipamento_Eletronico(equipamento, marca_temporal, coordenadas) VALUES (eq_id, new.marca_temporal,coord_id) returning id into bip_id;
		INSERT INTO alarmes(bip) VALUES (bip_id);
		RETURN new;
	END;
$$LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER insert_view_alarmes
	INSTEAD OF INSERT ON todos_alarmes
	FOR EACH ROW
	EXECUTE FUNCTION insert_view_alarme();

--test insert_view_alarme
--insert into todos_alarmes(matricula, nome, latitude, longitude, marca_temporal) values ('ff22ff', 'elu', 2.0, 3.4, '2016-01-10 13:34:14');

CREATE OR REPLACE FUNCTION random_number(length integer)
RETURNS int AS $$
	DECLARE
		numbers text[] := '{0,1,2,3,4,5,6,7,8,9}';
		numb text := '';
		n integer := 0;
		cnt integer=0;
	BEGIN
		IF length < 0 then
			raise exception 'Given length cannot be less than 0';
		END IF;
		for n in 1..length loop
			numb := numb || numbers[1+random()*(array_length(numbers, 1)-1)];
			cnt = cnt +1;
		END loop;
	RETURN numb;
	END;
$$LANGUAGE plpgsql;


--test restriction
--INSERT INTO Veiculo(matricula, condutor, equipamento, cliente) VALUES ('F26G5F',111111113,1,111222333);


