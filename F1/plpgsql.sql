
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
            FROM alarmes
            INNER JOIN bip_equipamento_eletronico b on b.id = alarmes.bip
            INNER JOIN veiculo v on b.equipamento = v.equipamento
            WHERE extract(YEAR FROM marca_temporal) = year AND matricula = target;
            RETURN number;

        end if;

        SELECT COUNT(*) INTO number
        FROM alarmes
        INNER JOIN bip_equipamento_eletronico bee on bee.id = alarmes.bip
        WHERE extract(YEAR FROM marca_temporal) = year;

        RETURN number;
    end;$$LANGUAGE plpgsql;



--------------- PONTO F ---------------

CREATE OR REPLACE PROCEDURE validateRequest(requestID int, equipamentoID int, requestMarca_temporal time, requestLatitude numeric, requestLongitude numeric)
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

    SELECT id INTO cords
    FROM coordenadas
    WHERE longitude = requestLongitude AND latitude = requestLatitude
    LIMIT 1;

    if (cords is null) then
        INSERT INTO coordenadas(id, latitude, longitude)
        VALUES (DEFAULT, requestLatitude, requestLongitude)
        RETURNING id INTO cords;
    end if;

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
            RETURN;
        end if;

        SELECT COUNT(*) INTO nZones
        FROM zona_verde
        INNER JOIN veiculo v on v.matricula = zona_verde.veiculo
        INNER JOIN bip_equipamento_eletronico bee on v.equipamento = bee.equipamento
        WHERE v.equipamento = NEW.equipamento;

        if(nZones = 0) then
            RETURN;
        end if;

        OPEN ITERATOR;
        FETCH NEXT FROM ITERATOR INTO raio, cords;

        WHILE (FOUND) LOOP
            --isValid := zonaVerdeValida(new.coordenadas, cords, raio);

            if (isValid = false) then
                INSERT INTO alarmes VALUES (DEFAULT, NEW.id);
                UPDATE equipamento_eletronico SET estado = 'Activo' WHERE id = NEW.equipamento;
                CLOSE ITERATOR;
                RETURN;
            end if;

            FETCH NEXT FROM ITERATOR INTO raio, cords;
        end loop;
        CLOSE ITERATOR;
        RETURN;

end;$$LANGUAGE plpgsql;

CREATE TRIGGER newBip AFTER INSERT ON bip_equipamento_eletronico
    FOR EACH ROW;
END;

