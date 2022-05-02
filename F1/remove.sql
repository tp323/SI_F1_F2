--remove modelo fisico
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

