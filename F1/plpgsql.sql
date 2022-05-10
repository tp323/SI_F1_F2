
--------------- PONTO D ---------------

CREATE OR REPLACE PROCEDURE insert_cliente_particular(
	IN newNif int,
	IN newNome varchar,
	IN newMorada varchar,
	IN newTelefone varchar,
	IN newRef_cliente int,
	IN newCC int)
LANGUAGE 'plpgsql'
AS $$
    BEGIN
        INSERT INTO cliente VALUES (newNif,newNome,newMorada,newTelefone,newRef_cliente);
		if(newNif not in (SELECT cliente.nif FROM cliente)) then
        	RAISE NOTICE 'Cliente nao inserido';
        END IF;
        INSERT INTO cliente_particular VALUES (newCC,newNif);
		--check if cliente was correctly added to the DB
    END;
$$;

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
			RAISE NOTICE 'Nao existe cliente para este nif';
		END IF;
       	UPDATE cliente SET nome = new_nome WHERE nif = nif_to_update;
       	UPDATE cliente SET morada = new_morada WHERE nif = nif_to_update;
       	UPDATE cliente SET telefone = new_telefone WHERE nif = nif_to_update;
       	UPDATE cliente SET ref_cliente = new_ref_cliente WHERE nif = nif_to_update;
	END;
$$;

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

--------------- PONTO E ---------------

CREATE OR REPLACE FUNCTION alarm_number(registration varchar(6), year numeric) RETURNS int AS $$
    DECLARE
        number int := null;
        target varchar(6) := '*';
    BEGIN
        if (year is null)then
            RAISE EXCEPTION 'Year cannot be null!';
        end if;

        if (registration is not null) then

            SELECT matricula INTO target
            FROM veiculo
            WHERE matricula = registration;

            if (target is null) then RETURN NULL; end if;

            SELECT COUNT(*) INTO number
            FROM bip_equipamento_eletronico b
            INNER JOIN veiculo v on b.equipamento = v.equipamento
            WHERE extract(YEAR FROM marca_temporal) = year AND matricula = target;
            RETURN number;

        end if;

        SELECT COUNT(*) INTO number
        FROM bip_equipamento_eletronico bee
        WHERE extract(YEAR FROM marca_temporal) = year;

        RETURN number;
    end;$$LANGUAGE plpgsql;

--------------- PONTO F ---------------

CREATE OR REPLACE PROCEDURE validateRequest(requestID int, equipamentoID int, requestMarca_temporal timestamp, requestLatitude numeric, requestLongitude numeric)
    LANGUAGE plpgsql
AS
    $$
    DECLARE
        equip int := null;
        cords int := null;
    BEGIN

    SELECT id INTO equip
    FROM bip_equipamento_eletronico
    WHERE bip_equipamento_eletronico.id = equipamentoID
    LIMIT 1;

    if(requestMarca_temporal is null OR requestLatitude is null OR requestLongitude is null OR equip is null) then
        INSERT INTO invalid_requests(id, equipamento, marca_temporal, latitude, longitude)
        VALUES (DEFAULT, equipamentoID, requestMarca_temporal, requestLatitude, requestLongitude);

        DELETE FROM requests
        WHERE id = requestID;

        RETURN;
    end if;

    SELECT checkCords(requestLatitude, requestLongitude) INTO cords;

    INSERT INTO bip_equipamento_eletronico(id, equipamento, marca_temporal, coordenadas)
    VALUES (DEFAULT, equip, requestMarca_temporal, cords);

end;
$$;

CREATE OR REPLACE PROCEDURE processRequests()
    LANGUAGE plpgsql
AS
    $$
    DECLARE
        ITERATOR CURSOR FOR SELECT * FROM requests;

        id int;
		equipamento int;
		marca_temporal timestamp;
		latitude numeric(3,1);
		longitude numeric(3,1);
    BEGIN

        OPEN ITERATOR;
        FETCH NEXT FROM ITERATOR INTO id, equipamento, marca_temporal, latitude, longitude;

        WHILE (FOUND) LOOP
            CALL validateRequest(id,equipamento,marca_temporal, latitude, longitude);
            FETCH NEXT FROM ITERATOR INTO id, equipamento, marca_temporal, latitude, longitude;
        end loop;
        CLOSE ITERATOR;
    END;
    $$;

--------------- PONTO G ---------------

CREATE OR REPLACE FUNCTION zonaVerdeValida(beepCoordenates int, gzCoordenates int, raio int) RETURNS BOOLEAN AS
    $$DECLARE
        gzLat numeric(3,1);
        gzLong numeric(3,1);

        beepLat numeric(3,1);
        beepLong numeric(3,1);
    BEGIN

        SELECT latitude, longitude INTO beepLat, beepLong
        FROM coordenadas
        WHERE id = beepCoordenates;
        SELECT latitude, longitude INTO gzLat, gzLong
        FROM coordenadas
        WHERE id = gzCoordenates;

        if(beepLat NOT BETWEEN gzLat - raio AND gzLat + raio OR beepLong NOT BETWEEN gzLong - raio AND gzLong + raio)then
            RETURN FALSE;
        end if;
        RETURN TRUE;
    end;$$LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION checkAlarm() RETURNS TRIGGER AS
    $$DECLARE
        nZones int:= null;
        status varchar:= null;
        raio int := null;
        cords int := null;
        isValid boolean := true;

        ITERATOR CURSOR FOR
            SELECT raio, coordenadas
            FROM zona_verde
            INNER JOIN veiculo v2 on v2.matricula = zona_verde.veiculo
            INNER JOIN equipamento_eletronico ee on ee.id = v2.equipamento
            WHERE ee.id = NEW.equipamento;
    BEGIN

        SELECT estado INTO status
        FROM equipamento_eletronico
        WHERE equipamento_eletronico.id = NEW.equipamento;

        if(status = 'PausaDeAlarmes') then
            RETURN NEW;
        end if;

        SELECT COUNT(*) INTO nZones
        FROM zona_verde
        INNER JOIN veiculo v on v.matricula = zona_verde.veiculo
        INNER JOIN bip_equipamento_eletronico bee on v.equipamento = bee.equipamento
        WHERE v.equipamento = NEW.equipamento;

        if(nZones = 0) then
            RETURN NEW;
        end if;

        OPEN ITERATOR;
        FETCH NEXT FROM ITERATOR INTO raio, cords;

        WHILE (FOUND) LOOP
            isValid := zonaVerdeValida(new.coordenadas, cords, raio);

            if (isValid = false) then
				UPDATE bip_equipamento_eletronico SET alarme = TRUE WHERE bip = NEW.id;
                UPDATE equipamento_eletronico SET estado = 'Activo' WHERE id = NEW.equipamento;
				UPDATE equipamento_eletronico SET estado = 'Inactivo' WHERE id = NEW.equipamento;
                CLOSE ITERATOR;
                RETURN NEW;
            end if;

            FETCH NEXT FROM ITERATOR INTO raio, cords;
        end loop;
        CLOSE ITERATOR;
        RETURN NEW;

end;$$LANGUAGE plpgsql;

CREATE TRIGGER newBip AFTER INSERT ON bip_equipamento_eletronico
    FOR EACH ROW
    EXECUTE FUNCTION checkAlarm();

--------------- PONTO H ---------------

CREATE OR REPLACE PROCEDURE createVehicle(newRegistration varchar(6), newDriver int, newEquip int, newClient int, raio int = null, lat numeric = null, long numeric = null)
    LANGUAGE plpgsql
AS
    $$
    DECLARE
        registrationCheck varchar(6);
        driverCheck int;
        equipCheck int;
        clientCheck int;
        cords int;
    BEGIN

        SELECT matricula INTO registrationCheck
        FROM veiculo
        WHERE matricula = newRegistration;

        IF(registrationCheck is not null) then
            RAISE EXCEPTION 'This vehicle registration already exists!';
        end if;

        SELECT cc INTO driverCheck
        FROM condutor
        WHERE cc = newDriver;

        IF(driverCheck is null) then
            RAISE EXCEPTION 'This driver reference does not exist!';
        end if;

        SELECT id INTO equipCheck
        FROM equipamento_eletronico
        WHERE id = newEquip;

        IF(equipCheck is null) then
            RAISE EXCEPTION 'This equipment reference does not exist!';
        end if;

        SELECT nif INTO clientCheck
        FROM cliente
        WHERE nif = newClient;

        IF(clientCheck is null) then
            RAISE EXCEPTION 'This client reference does not exist!';
        end if;

        INSERT INTO veiculo VALUES (newRegistration, newDriver, newEquip, newClient);

        if(raio is null or lat is null or long is null) then
            RETURN;
        end if;

        SELECT id INTO cords
        FROM coordenadas
        WHERE latitude = lat AND longitude = long;

        if(cords is null) then
            INSERT INTO coordenadas VALUES (DEFAULT, lat, long) RETURNING id INTO cords;
        end if;

        INSERT INTO zona_verde VALUES (DEFAULT, cords, newRegistration, raio);
    END;
    $$;

--------------- PONTO I ---------------

-- View inserted on create.sql

--------------- PONTO J ---------------

CREATE OR REPLACE FUNCTION insert_view_alarme()
RETURNS trigger AS $$
	DECLARE
	eq_count int:= null;
	eq_id int:= null;
	coord_id int:= null;
	bip_id int:= null;
    BEGIN
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
		UPDATE bip_equipamento_eletronico SET alarme = TRUE WHERE id=bip_id;

		RETURN new;
	END;
$$LANGUAGE plpgsql;

CREATE TRIGGER insert_view_alarmes
	INSTEAD OF INSERT ON todos_alarmes
	FOR EACH ROW
	EXECUTE FUNCTION insert_view_alarme();

--------------- PONTO K ---------------

CREATE OR REPLACE PROCEDURE deleteInvalids()
    LANGUAGE plpgsql AS
    $$
    DECLARE currentDate date := current_date;
        targetID int;
        targetDate date;
        ITERATOR CURSOR FOR
            SELECT id, marca_temporal::date
            FROM invalid_requests;
    BEGIN

        OPEN ITERATOR;
        FETCH NEXT FROM ITERATOR INTO targetID, targetDate;

        WHILE (FOUND) LOOP

            if currentDate - targetDate >= 15 THEN
                DELETE FROM invalid_requests WHERE id = targetID;
            end if;

            FETCH NEXT FROM ITERATOR INTO targetID, targetDate;
        end loop;
        CLOSE ITERATOR;
    END;
$$;

--------------- PONTO L ---------------

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

--------------- PONTO M ---------------

CREATE OR REPLACE FUNCTION incrementAlarm()RETURNS TRIGGER AS
    $$DECLARE
        target varchar(6);
    BEGIN
		IF old.estado = 'Inactivo' AND new.estado = 'Activo' THEN
			SELECT matricula INTO target FROM veiculo WHERE equipamento = new.id;
			UPDATE veiculo set alarms = alarms + 1 WHERE matricula = target;
		END IF;
        RETURN NEW;
END;$$LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION incrementAlarmOnInsert()RETURNS TRIGGER AS
    $$DECLARE
        target varchar(6);
    BEGIN
		IF new.alarme = TRUE THEN
			SELECT matricula INTO target FROM veiculo WHERE equipamento = new.equipamento;
			UPDATE veiculo set alarms = alarms + 1 WHERE matricula = target;
		END IF;
        RETURN NEW;
END;$$LANGUAGE plpgsql;

CREATE TRIGGER alarmIncrement AFTER UPDATE ON equipamento_eletronico
        FOR EACH ROW
        EXECUTE FUNCTION incrementAlarm();
		
CREATE TRIGGER alarmIncrementIfpassedAsTrue AFTER INSERT ON bip_equipamento_eletronico
        FOR EACH ROW
        EXECUTE FUNCTION incrementAlarmOnInsert();


--------------- AUXILIARIES ---------------


CREATE OR REPLACE FUNCTION checkCords(lat numeric, long numeric) RETURNS INT AS
    $$DECLARE
        target int = null;
    BEGIN

        SELECT id INTO target
        FROM coordenadas
        WHERE longitude = long AND latitude = lat
        LIMIT 1;

    if (target is null) then
        INSERT INTO coordenadas(id, latitude, longitude)
        VALUES (DEFAULT, lat, long)
        RETURNING id INTO target;
    end if;

    RETURN target;

END;$$LANGUAGE plpgsql;