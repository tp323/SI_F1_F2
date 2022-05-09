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
        --SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

        INSERT INTO cliente VALUES (nif,nome,morada,telefone,ref_cliente);
		if(nif not in (SELECT cliente.nif FROM cliente)) then
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
CREATE OR REPLACE PROCEDURE update_cliente_particular(
	IN nif_to_update int, 
	IN new_nome varchar, 
	IN new_morada varchar, 
	IN new_telefone varchar, 
	IN new_ref_cliente int)
LANGUAGE plpgsql AS
$$
    begin
        --SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

		--check if nif is valid
		--not sure if this exception should exist
		IF (nif_to_update NOT IN (SELECT nif FROM cliente)) then
			RAISE NOTICE 'Nao existe cliente para este nif';        
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
        --SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

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
	    --SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

       	UPDATE cliente SET ativo = FALSE WHERE nif = nif_to_delete;
		return new;
	END;
$$LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER delete_cliente
BEFORE DELETE ON cliente
FOR EACH ROW
EXECUTE FUNCTION delete_clientes();


--j
--todos_alarmes(matricula, nome, latitude, longitude, marca_temporal)
CREATE OR REPLACE FUNCTION insert_view_alarme()
RETURNS trigger AS $$
	DECLARE
	eq_count int:= null;
	eq_id int:= null;
	coord_id int:= null;
	bip_id int:= null;
    BEGIN
	    --SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
		select COUNT(*) into eq_count from veiculo v 
		inner join condutor cond on v.condutor = cond.cc
		where matricula = new.matricula and nome = new.nome;
		
		IF eq_count = 0 THEN
			RAISE EXCEPTION 'matricula ou nome incorretos, ou condutor não conduz o veiculo declarado';
		END IF;
		
		select distinct on (v.equipamento) v.equipamento into eq_id from veiculo v 
		inner join condutor cond on v.condutor = cond.cc
		where matricula = new.matricula and nome = new.nome;
		
		SELECT checkCords(new.latitude, new.longitude) into coord_id;

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
--insert into todos_alarmes(matricula, nome, latitude, longitude, marca_temporal) values ('FF18FF', 'Carlos Sainz', 0.1, 0.2, '2016-01-10 13:34:14');

--test restriction
--INSERT INTO Veiculo(matricula, condutor, equipamento, cliente) VALUES ('F26G5F',111111113,1,111222333);


