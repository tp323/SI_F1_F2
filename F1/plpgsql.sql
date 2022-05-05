

--------------- PONTO F

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
    $$