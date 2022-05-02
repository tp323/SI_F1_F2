--apaga todos os dados existentes nas tabelas
BEGIN transaction;
DELETE FROM Requests;
DELETE FROM Invalid_Requests;
DELETE FROM Alarmes;
DELETE FROM Zona_Verde;
DELETE FROM Veiculo;
DELETE FROM Condutor;
DELETE FROM Bip_Equipamento_Eletronico;
DELETE FROM Coordenadas;
DELETE FROM Equipamento_Eletronico;
DELETE FROM Cliente_Institucional;

ALTER table if EXISTS Cliente DROP constraint if EXISTS ref_cliente_part;

DELETE FROM Cliente_Particular;
DELETE FROM Cliente;

ALTER table if EXISTS Cliente
ADD constraint ref_cliente_part foreign KEY (ref_cliente) references Cliente_Particular(CC) DEFERRABLE INITIALLY DEFERRED;

END TRANSACTION;