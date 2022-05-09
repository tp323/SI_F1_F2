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
--drop trigger delete_cliente;
--drop function delete_clientes();



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



CREATE TRIGGER delete_cliente
BEFORE DELETE ON cliente
FOR EACH ROW
EXECUTE FUNCTION delete_clientes();


--DELETE FROM cliente;

--não funciona mas a linha de baixo faz o pretendido
--UPDATE clientes SET ativo = FALSE WHERE nif = 121222333;
