BEGIN transaction;

---------------TRIGGERS----------------
DROP TRIGGER IF EXISTS max3_veiculo_particular ON Veiculo;
DROP TRIGGER IF EXISTS delete_cliente ON cliente;
DROP TRIGGER IF EXISTS insert_view_alarmes ON todos_alarmes;
DROP TRIGGER IF EXISTS newBip ON bip_equipamento_eletronico;
DROP TRIGGER IF EXISTS veichuleCreated ON veiculo;
DROP TRIGGER IF EXISTS alarmIncrement ON equipamento_eletronico;
DROP TRIGGER IF EXISTS alarmIncrementIfpassedAsTrue ON bip_equipamento_eletronico;

-----FUNCTIONS-RELATED-TO-TRIGGERS------
DROP FUNCTION IF EXISTS check_veiculos_particular;
DROP FUNCTION IF EXISTS delete_clientes;
DROP FUNCTION IF EXISTS insert_view_alarme;
DROP FUNCTION IF EXISTS checkAlarm;
DROP FUNCTION IF EXISTS createAlarmCounter;
DROP FUNCTION IF EXISTS incrementAlarm;

---------------FUNCTIONS----------------
DROP FUNCTION IF EXISTS alarm_number;
DROP FUNCTION IF EXISTS random_number;
DROP FUNCTION IF EXISTS zonaverdevalida(INT, INT);
DROP FUNCTION IF EXISTS zonaverdevalida;
DROP FUNCTION IF EXISTS checkcords;
DROP FUNCTION IF EXISTS incrementalarmoninsert;

---------------PROCEDURES---------------
DROP PROCEDURE IF EXISTS createVehicle;
DROP PROCEDURE IF EXISTS deleteinvalids;
DROP PROCEDURE IF EXISTS insert_cliente_particular;
DROP PROCEDURE IF EXISTS processrequests;
DROP PROCEDURE IF EXISTS remove_cliente_particular;
DROP PROCEDURE IF EXISTS update_cliente_particular;
DROP PROCEDURE IF EXISTS validaterequest;


------------------VIEWS-----------------
DROP VIEW IF EXISTS todos_alarmes;

-----------------TABLES-----------------
DROP TABLE IF EXISTS N_Alarms;
DROP TABLE IF EXISTS Requests;
DROP TABLE IF EXISTS Invalid_Requests;
DROP TABLE IF EXISTS Alarmes;
DROP TABLE IF EXISTS Zona_Verde;
DROP TABLE IF EXISTS Veiculo;
DROP TABLE IF EXISTS Condutor;
DROP TABLE IF EXISTS Bip_Equipamento_Eletronico;
DROP TABLE IF EXISTS Coordenadas;
DROP TABLE IF EXISTS Equipamento_Eletronico;
DROP TABLE IF EXISTS Cliente_Institucional;

ALTER table if EXISTS Cliente DROP constraint if EXISTS ref_cliente_part;

DROP TABLE IF EXISTS Cliente_Particular;
DROP TABLE IF EXISTS Cliente;

-----------------TESTS------------------

DROP PROCEDURE IF EXISTS alarm_number_testing;
DROP PROCEDURE IF EXISTS alarmcounter_testing;
DROP PROCEDURE IF EXISTS client_testing;
DROP PROCEDURE IF EXISTS createvehicle_testing;
DROP PROCEDURE IF EXISTS delete_clientes_testing;
DROP PROCEDURE IF EXISTS deleteinvalids_testing;
DROP PROCEDURE IF EXISTS insert_view_alarme_testing;
DROP PROCEDURE IF EXISTS newbip_testing;
DROP PROCEDURE IF EXISTS processrequests_testing;
DROP PROCEDURE IF EXISTS todos_alarmes_testing;

	--Cliente(NIF, nome, morada, telefone, ref cliente)
	create table IF NOT EXISTS Cliente(
		NIF int primary key check(NIF between 100000000 and 999999999),
		nome varchar(25) not null,
		morada varchar(150) not null,
		telefone varchar(10) not null,
		ref_cliente int DEFAULT null,
		ativo boolean DEFAULT TRUE not null
	);
	--Cliente_Particular(CC, cliente)
	create table IF NOT EXISTS Cliente_Particular(
		CC int primary key check(CC between 100000000 and 999999999),
		cliente int not null,
		FOREIGN KEY (cliente)
     		REFERENCES Cliente (NIF) ON DELETE CASCADE ON UPDATE cascade
	);


ALTER table if EXISTS Cliente_Particular DROP constraint if EXISTS ref_cliente_part;

ALTER table if EXISTS Cliente_Particular
ADD constraint ref_cliente_part foreign KEY (cliente) references Cliente(NIF) DEFERRABLE INITIALLY DEFERRED;


	--Cliente_Institucional(nome de contacto, cliente)
	create table IF NOT EXISTS Cliente_Institucional(
		nome_contacto varchar(25) not null,
		cliente int primary key,
		FOREIGN KEY (cliente)
     		REFERENCES Cliente (NIF) ON DELETE CASCADE ON UPDATE cascade
	);

	--Equipamento(id, estado)
	create table IF NOT EXISTS Equipamento_Eletronico(
		id serial primary key,
		estado varchar(15),
		constraint estado check (estado IN ('Activo', 'PausaDeAlarmes', 'Inactivo'))
	);
	--Coordenadas(id, latitude, longitude)
	create table IF NOT EXISTS Coordenadas(
		id serial primary key,
		latitude numeric(3,1) NOT NULL,
		longitude numeric(3,1) NOT NULL
	);
	--Bip Equipamento Eletronico(id, equipamento, marca temp, coordenadas)
	create table IF NOT EXISTS Bip_Equipamento_Eletronico(
		id serial primary key , --id é relativo ao numero do bit para o atual equi eletronico
		equipamento int NOT NULL,
		marca_temporal timestamp NOT NULL,
		coordenadas int NOT NULL,
		alarme boolean DEFAULT FALSE not null,
		FOREIGN KEY (equipamento)
     		REFERENCES Equipamento_Eletronico (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (coordenadas)
     		REFERENCES Coordenadas (id) ON DELETE CASCADE ON UPDATE cascade
	);
	--Condutor(CC, nome, contacto)
	create table IF NOT EXISTS Condutor(
		CC int primary key, 
		nome varchar(20) not null,
		contacto varchar(10)
	);
	--Veiculo(matricula, condutor, equipamento, cliente)
	create table IF NOT EXISTS Veiculo(
		matricula varchar(6) primary key,
		condutor int NOT NULL,
		equipamento int NOT NULL,
		cliente int NOT NULL,
		alarms int not null default 0,
		FOREIGN KEY (condutor)
     		REFERENCES Condutor (CC) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (equipamento)
     		REFERENCES Equipamento_Eletronico (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (cliente)
     		REFERENCES Cliente (NIF) ON DELETE CASCADE ON UPDATE cascade
	);
	
	--Zona Verde(veiculo, coordenadas, raio)
	create table IF NOT EXISTS Zona_Verde(
		id serial primary key,
		coordenadas int not null,
		veiculo varchar(6) NOT NULL,
		raio int NOT NULL,
		FOREIGN KEY (coordenadas)
     		REFERENCES Coordenadas (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (veiculo)
     		REFERENCES Veiculo (matricula) ON DELETE CASCADE ON UPDATE cascade
	);
	
	--Requests(id, equipamento, marca temp, coordenadas)
	create table IF NOT EXISTS Requests(
		id serial primary key, --id é relativo ao numero do bit para o atual equi eletronico
		equipamento int,
		marca_temporal timestamp,
		latitude numeric(3,1),
		longitude numeric(3,1)
	);
	
	--Invalid Requests(id, equipamento, marca temp, coordenadas)
	create table IF NOT EXISTS Invalid_Requests(
		id serial primary key,
		equipamento int,
		marca_temporal timestamp,
		latitude numeric(3,1),
		longitude numeric(3,1)
	);
	
	
	------------------------------VIEWS------------------------------
	CREATE OR REPLACE VIEW todos_alarmes AS
		SELECT matricula, nome, latitude, longitude, marca_temporal
		FROM bip_equipamento_eletronico bip
		inner join equipamento_eletronico eq on equipamento = eq.id 
		inner join veiculo v on eq.id = v.equipamento
		inner join condutor cond on v.condutor=cond.cc
		inner join coordenadas coord on coordenadas = coord.id;

	----------------------------FUNCTIONS---------------------------
	CREATE OR REPLACE FUNCTION check_veiculos_particular()
	RETURNS trigger AS $$
		DECLARE
			cnt integer := 0;
			is_particular integer := 0;
		BEGIN
			select count(*) into is_particular from cliente_particular where cliente = new.cliente;
			IF is_particular != 0 THEN
				select count(matricula) into cnt from veiculo where cliente = new.cliente;
				IF cnt >= 3 then
					raise exception 'Cliente Particular ja alcançou numero maximo de veiculos permitidos 3';
				END IF;
			END IF;
		RETURN new;
		END;
	$$LANGUAGE plpgsql;
	
	
	----------------------------TRIGGERS----------------------------
	CREATE TRIGGER max3_veiculo_particular
	BEFORE INSERT ON veiculo
	FOR EACH ROW
	EXECUTE FUNCTION check_veiculos_particular();

------------------------------------------------------------PROCEDURES------------------------------------------------------------
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
				UPDATE bip_equipamento_eletronico SET alarme = TRUE WHERE id = NEW.id;
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

-----------------------------------------------------------------INSERTS-----------------------------------------------------------------
	--Cliente(NIF, nome, morada, telefone, ref cliente)
INSERT INTO Cliente (NIF, nome, morada, telefone, ref_cliente)
VALUES (111222333, 'O maior da minha aldeia', 'Vila Nova de Robiães', 911222111, DEFAULT),
(121222333, 'Limitada', 'Ali', 911333222, 121222333),
(100000000, 'O menor da minha aldeia', 'Vila Velha de Robiães', 911332111, DEFAULT),
(999999999, 'Aquele', 'ali', 911722111, DEFAULT);

	--Cliente_Particular(CC, cliente)
INSERT INTO Cliente_Particular(CC, cliente)
VALUES (121222333, 111222333),(100000000,100000000),(999999999,999999999);

	--Cliente_Institucional(nome de contacto, cliente)
INSERT INTO Cliente_Institucional(nome_contacto, cliente)
VALUES ('aquele', 121222333);

	--Equipamento(id, estado)
INSERT INTO Equipamento_Eletronico(estado)
VALUES ('Inactivo'),('Inactivo'),('Activo');

	--Coordenadas(id, latitude, longitude)
INSERT INTO Coordenadas(latitude, longitude)
VALUES (0.1, 0.2),(0, 0),(21.2, 23.4);

	--Bip Equipamento Eletronico(id, equipamento, marca temp, coordenadas)
INSERT into Bip_Equipamento_Eletronico(equipamento, marca_temporal, coordenadas, alarme)
VALUES (1,'2015-01-10 00:51:14',1,TRUE),(2,'2015-01-10 00:51:14',3,DEFAULT);

	--Condutor(CC, nome, contacto)
INSERT INTO Condutor(CC, nome, contacto)
VALUES (111111113,'Charles Leclerc',922555888),(111111114,'Carlos Sainz',922755688),(111111115,'Max Verstappen',912555688),
(111111116,'Fernando Alonso',922555788);

	--Veiculo(matricula, condutor, equipamento, cliente)
INSERT INTO Veiculo(matricula, condutor, equipamento, cliente)
VALUES ('FF17FF',111111113,1,111222333), ('FF18FF',111111114,2,111222333), ('RB16RB',111111115,3,121222333);

	--Zona Verde(veiculo, coordenadas, raio)
INSERT INTO Zona_Verde(veiculo, coordenadas, raio)
VALUES ('FF17FF',2,3);

commit;