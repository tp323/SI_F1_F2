-- PROCEDURE: public.insert_particular(integer, character varying, character varying, character varying, integer, integer)

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
		IF (nif_to_update NOT IN (SELECT cliente.nif FROM cliente)) then
			RAISE NOTICE 'Nao existe cliente para esse nif';        
		end if;
		IF (new_nome IS NOT null) THEN 
       		UPDATE cliente SET nome = new_nome WHERE nif = nif_to_update; 
       	END IF;
       	IF (new_morada IS NOT null) THEN 
       		UPDATE cliente SET morada = new_morada WHERE nif = nif_to_update;
       	END IF; 
       	IF (new_telefone IS NOT null) THEN 
       		UPDATE cliente SET telefone = new_telefone WHERE nif = nif_to_update;
       	END IF; 
	   	--check if cc from new_ref_cliente is valid
       	IF (new_ref_cliente IS NOT null) THEN 
       		UPDATE cliente SET ref_cliente = new_ref_cliente WHERE nif = nif_to_update;
       	END IF;
	END;
$$;

--test update
call update_cliente_particular(123456799 ,'outro individuo' , 'ali', '967992934', 123456700);


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
call remove_cliente_particular(123456700, 123456700);
