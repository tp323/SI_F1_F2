--apaga todos os dados existentes nas tabelas
BEGIN transaction;
DELETE FROM Requests;
DELETE FROM Invalid_Requests;
DELETE FROM Zona_Verde;
DELETE FROM Veiculo;
DELETE FROM Condutor;
DELETE FROM Bip_Equipamento_Eletronico;
DELETE FROM Coordenadas;
DELETE FROM Equipamento_Eletronico;
DELETE FROM Cliente_Institucional;
DELETE FROM requests;
DELETE FROM invalid_requests;



DELETE FROM Cliente_Particular;
DELETE FROM Cliente;


END TRANSACTION;