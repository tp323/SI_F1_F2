
--------------- PONTO D Test ---------------

CREATE OR REPLACE PROCEDURE client_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        client INT;
        part_client INT;
        active BOOLEAN;
		status varchar;
    BEGIN
		SELECT current_setting('transaction_isolation') INTO status;
        RAISE NOTICE '%', status;
        CALL insert_cliente_particular(1334545634, CAST ('Ernesto Ferrero-Roche' AS VARCHAR), CAST ('Albal' AS VARCHAR), '926021405', NULL, 395478546);
        SELECT nif INTO client
        FROM cliente
        WHERE nif = 1334545634;

        IF client IS NOT NULL THEN
            RAISE NOTICE 'Inserting Ernesto OK';
        ELSE
            RAISE WARNING 'Inserting Ernesto NOT OK';
            DELETE FROM cliente WHERE nif = 1334545634;
            RETURN;
        END IF;

        CALL update_cliente_particular(1334545634, 'Ernesto Ferrero-Roche', 'Albal', '926021405', 12122233);

        SELECT ref_cliente INTO client
        FROM cliente
        WHERE nif = 1334545634;

        IF client = 12122233 THEN
            RAISE NOTICE 'Updating Ernesto OK';
        ELSE
            RAISE WARNING 'Updating Ernesto NOT OK';
        END IF;

        CALL remove_cliente_particular(1334545634, 395478546);

        SELECT ativo INTO active
        FROM cliente
        WHERE nif = 1334545634;

        SELECT cc INTO part_client
        FROM cliente_particular
        WHERE cc = 395478546;

        IF active IS FALSE AND part_client IS NULL THEN
            RAISE NOTICE 'Deleting Ernesto OK';
        ELSE
            RAISE WARNING 'Deleting Ernesto NOT OK';
        END IF;
        --ROLLBACK;
    END;
    $$;

--------------- PONTO E Test ---------------

CREATE OR REPLACE PROCEDURE alarm_number_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        num INT;
    BEGIN

        num = alarm_number(NULL, 2015);
        IF num = 4 THEN
            RAISE NOTICE 'Registration NULL test OK';
        ELSE
            RAISE WARNING 'Registration NULL test NOT OK';
        END IF;

        num = alarm_number('FF17FF', 2015);
        IF num = 1 THEN
            RAISE NOTICE 'Registration with all necessary data OK';
        ELSE
            RAISE WARNING 'Registration with all necessary data NOT OK';
        END IF;

        num = alarm_number('FA20FF', 2015);
        IF num IS NULL THEN
            RAISE NOTICE 'No alarm number with non existent registration OK';
        ELSE
            RAISE WARNING 'No alarm number with non existent registration NOT OK';
        END IF;

        num = alarm_number('1', NULL);
        RAISE WARNING 'Year NULL test NOT OK';
        --ROLLBACK;
        EXCEPTION
            WHEN RAISE_EXCEPTION THEN
                RAISE NOTICE 'Year NULL test OK';
                --ROLLBACK;
    END;
    $$;


--------------- PONTO F Test ---------------

CREATE OR REPLACE PROCEDURE processRequests_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        request INT;
        coordinates INT;
    BEGIN

        INSERT INTO requests VALUES(DEFAULT, NULL, '2029-05-02 03:04:05', NULL, NULL);
        INSERT INTO requests VALUES(DEFAULT, 2, '2024-12-06 03:11:43', 45, 43);
        CALL processrequests();

        SELECT id INTO request
        FROM invalid_requests
        WHERE equipamento IS NULL AND marca_temporal = '2029-05-02 03:04:05' AND latitude IS NULL AND longitude IS NULL;

        IF request IS NOT NULL THEN
            RAISE NOTICE 'Request invalidation OK';
        ELSE
            RAISE WARNING 'Request invalidation NOT OK';
        END IF;

        SELECT id INTO coordinates
        FROM coordenadas
        WHERE latitude = 45 AND longitude = 43;

        SELECT id INTO request
        FROM bip_equipamento_eletronico
        WHERE equipamento = 2 AND marca_temporal = '2024-12-06 03:11:43' AND coordenadas = coordinates;

        IF request IS NOT NULL THEN
            RAISE NOTICE 'Request validation OK';
        ELSE
            RAISE WARNING 'Request validation NOT OK';
        END IF;
        --ROLLBACK;
    END;
    $$;


--------------- PONTO G Test ---------------

CREATE OR REPLACE FUNCTION zonaVerdeValida(beepCoordenates INT, raio INT) RETURNS BOOLEAN LANGUAGE plpgsql
    AS
    $$
    DECLARE
        beepLat NUMERIC(3,1);
    BEGIN
        SELECT latitude INTO beepLat
        FROM coordenadas
        WHERE id = beepCoordenates;

        IF FLOOR(beepLat * raio) % 2 = 0 THEN
            RETURN TRUE;
        END IF;
        RETURN FALSE;
    END;
    $$;

CREATE OR REPLACE PROCEDURE newBIP_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        valid BOOLEAN;
    BEGIN
        valid = zonaVerdeValida(1, 4);

        IF valid = TRUE THEN
            RAISE NOTICE 'Latitude is even OK';
        ELSE
            RAISE WARNING 'Latitude is even NOT OK';
        END IF;

        valid = zonaVerdeValida(3, 3);

        IF valid = FALSE THEN
            RAISE NOTICE 'Latitude is odd OK';
        ELSE
            RAISE WARNING 'Latitude is odd NOT OK';
        END IF;
    END;
    $$;


--------------- PONTO H Test ---------------

CREATE OR REPLACE PROCEDURE createVehicle_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        registration VARCHAR(6);
        green_zone INT;
    BEGIN

        CALL createVehicle('FF40AS', 111111113, 1, 111222333);

        SELECT matricula INTO registration
        FROM veiculo
        WHERE matricula = 'FF40AS';

        IF registration IS NOT NULL THEN
            RAISE NOTICE 'Creating a valid vehicle only OK';
        ELSE
            RAISE WARNING 'Creating a valid vehicle only NOT OK';
        END IF;

        CALL createVehicle('FF44DF', 111111113, 1, 111222333, 34, 12, 35);

        SELECT matricula INTO registration
        FROM veiculo
        WHERE matricula = 'FF44DF';

        SELECT id INTO green_zone
        FROM zona_verde
        WHERE veiculo = 'FF44DF';

        IF registration IS NOT NULL AND green_zone IS NOT NULL THEN
            RAISE NOTICE 'Creating a valid vehicle alongside a green zone OK';
        ELSE
            RAISE WARNING 'Creating a valid vehicle alongside a green zone NOT OK';
        END IF;

        CALL createVehicle('FF17FF', NULL, NULL, NULL);
        RAISE WARNING 'Adding a vehicle with an already existing registration NOT OK';
        --ROLLBACK;
        EXCEPTION
            WHEN RAISE_EXCEPTION THEN
                RAISE NOTICE 'Adding a vehicle with an already existing registration OK';
                --ROLLBACK;
    END;
    $$;


--------------- PONTO I ---------------

CREATE OR REPLACE PROCEDURE todos_alarmes_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        registration VARCHAR(6);
    BEGIN

        INSERT INTO equipamento_eletronico VALUES(135464, 'Activo');
        INSERT INTO veiculo VALUES('FJ45LF', 111111116, 135464, 121222333);
        INSERT INTO bip_equipamento_eletronico VALUES(3774375, 135464, '2034-03-04 10:43:35', 1);
        INSERT INTO alarmes VALUES(764645, 3774375);

        SELECT matricula INTO registration
        FROM todos_alarmes
        WHERE matricula = 'FJ45LF';

        IF registration IS NOT NULL THEN
            RAISE NOTICE 'Added values that affected the view OK';
        ELSE
            RAISE WARNING 'Added values that affected the view NOT OK';
        END IF;
        --ROLLBACK;
    END;
    $$;

--------------- PONTO J ---------------

CREATE OR REPLACE PROCEDURE insert_view_alarme_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        added_date TIMESTAMP;
    BEGIN

        INSERT INTO todos_alarmes VALUES('RB16RB', 'Max Verstappen', 45, 32, '2022-11-03 04:43:12');

        SELECT marca_temporal INTO added_date
        FROM todos_alarmes
        WHERE matricula = 'RB16RB';

        IF added_date = '2022-11-03 04:43:12' THEN
            RAISE NOTICE 'Added the values through the view OK';
        ELSE
            RAISE WARNING 'Added the values through the view NOT OK';
        END IF;

        INSERT INTO todos_alarmes VALUES('HF45KS', 'Joao Sapato', 45, 32, '2022-11-03 04:43:12');
        RAISE WARNING 'Cannot add to view if vehicle or driver do not exist NOT OK';
        --ROLLBACK;
        EXCEPTION
            WHEN RAISE_EXCEPTION THEN
               RAISE NOTICE 'Cannot add to view if vehicle or driver do not exist OK';
               --ROLLBACK;
    END;
    $$;

--------------- PONTO K ---------------

CREATE OR REPLACE PROCEDURE deleteInvalids_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        invalid_request INT;
        time TIMESTAMP = CURRENT_TIMESTAMP;
    BEGIN

        INSERT INTO invalid_requests VALUES(DEFAULT, 43, '1243-12-21 03:45:14', 43, 56);
        CALL deleteInvalids();

        SELECT id INTO invalid_request
        FROM invalid_requests
        WHERE equipamento = 43 AND marca_temporal = '1243-12-21 03:45:14' AND latitude = 43 AND longitude = 56;

        IF invalid_request IS NULL THEN
            RAISE NOTICE 'Deleting invalid request older than 15 days OK';
        ELSE
            RAISE WARNING 'Deleting invalid request older than 15 days NOT OK';
        END IF;

        INSERT INTO invalid_requests VALUES(DEFAULT, 43, time, 43, 56);
        CALL deleteInvalids();

        SELECT id INTO invalid_request
        FROM invalid_requests
        WHERE equipamento = 43 AND marca_temporal = time AND latitude = 43 AND longitude = 56;

        IF invalid_request IS NOT NULL THEN
            RAISE NOTICE 'Not deleting invalid request older than 15 days OK';
        ELSE
            RAISE WARNING 'Not deleting invalid request older than 15 days NOT OK';
        END IF;
        --ROLLBACK;
    END;
    $$;


--------------- PONTO L ---------------

CREATE OR REPLACE PROCEDURE delete_clientes_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        client INT;
        active BOOLEAN;
    BEGIN

        CALL insert_cliente_particular(345321334, 'Joao Gabarola', 'Lisboa', '962345321', NULL, 345641345);
        DELETE FROM cliente WHERE nif = 345321334;

        SELECT cc INTO client
        FROM cliente_particular
        WHERE cc = 345641345;

        SELECT ativo INTO active
        FROM cliente
        WHERE nif = 345321334;

        IF client IS NOT NULL AND active = FALSE THEN
            RAISE NOTICE 'Deleting clients OK';
        ELSE
            RAISE WARNING 'Deleting clients NOT OK';
        END IF;
        --ROLLBACK;
    END;
    $$;

--------------- PONTO M ---------------

CREATE OR REPLACE PROCEDURE alarmCounter_testing() LANGUAGE plpgsql
    AS
    $$
    DECLARE
        alarm_count INT;
    BEGIN

        INSERT INTO equipamento_eletronico VALUES(234543, 'Activo');
        INSERT INTO bip_equipamento_eletronico VALUES(454643, 234543, '2022-03-30 10:34:35', 1);
        INSERT INTO veiculo VALUES('AS45FR', 111111116, 234543, 111222333);

        SELECT alarms INTO alarm_count
        FROM veiculo
        WHERE matricula = 'AS45FR';

        IF alarm_count = 0 THEN
            RAISE NOTICE 'Adding alarm to a newly added vehicle OK';
        ELSE
            RAISE WARNING 'Adding alarm to a newly added vehicle NOT OK';
        END IF;

        INSERT INTO alarmes VALUES(23445, 454643);

        SELECT alarms INTO alarm_count
        FROM veiculo
        WHERE matricula = 'AS45FR';

        IF alarm_count >= 1 THEN
            RAISE NOTICE 'Incrementing alarm number on vehicle OK';
        ELSE
            RAISE WARNING 'Incrementing alarm number on vehicle NOT OK';
        END IF;
        --ROLLBACK;
    END;
    $$;


BEGIN TRANSACTION ISOLATION LEVEL REPEATABLE READ;

CALL client_testing();
CALL alarm_number_testing();
CALL processRequests_testing();

CALL newBIP_testing();
CALL createVehicle_testing();
CALL todos_alarmes_testing();
CALL insert_view_alarme_testing();
CALL deleteInvalids_testing();
CALL delete_clientes_testing();
CALL alarmCounter_testing();
ROLLBACK;