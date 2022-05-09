--remove modelo fisico

---------------TRIGGERS----------------
DROP TRIGGER IF EXISTS max3_veiculo_particular ON Veiculo;
DROP TRIGGER IF EXISTS delete_cliente ON cliente;

DROP TRIGGER IF EXISTS insert_view_alarmes ON todos_alarmes;
DROP TRIGGER IF EXISTS newBip ON bip_equipamento_eletronico;
DROP TRIGGER IF EXISTS veichuleCreated ON veiculo;
DROP TRIGGER IF EXISTS alarmAdded ON alarmes;

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
DROP FUNCTION IF EXISTS zonaverdevalida;

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

