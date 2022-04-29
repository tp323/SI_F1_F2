begin transaction;

ALTER table if EXISTS Cliente DROP constraint if EXISTS ref_cliente_part;

	--Cliente(NIF, nome, morada, telefone, ref cliente)
INSERT INTO Cliente (NIF, nome, morada, telefone, ref_cliente)
VALUES (111222333, 'O maior da minha aldeia', 'Vila Nova de Robiães', 911222111, DEFAULT),
(121222333, 'Limitada', 'Ali', 911333222, 12122233);

	--Cliente_Particular(CC, cliente)
INSERT INTO Cliente_Particular(CC, cliente)
VALUES (12122233, 111222333);

ALTER table if EXISTS Cliente
ADD constraint ref_cliente_part foreign KEY (ref_cliente) references Cliente_Particular(CC) DEFERRABLE INITIALLY DEFERRED;


	--Cliente_Institucional(nome de contacto, cliente)
INSERT INTO Cliente_Institucional(nome_contacto, cliente)
VALUES ('aquele', 121222333);

	--Equipamento(id, estado)
INSERT INTO Equipamento_Eletronico(id, estado)
VALUES (1, 'Activo');

	--Coordenadas(id, latitude, longitude)
INSERT INTO Coordenadas(id, latitude, longitude)
VALUES (DEFAULT, 0.1, 0.2),(DEFAULT, 0, 0);

	--Bip Equipamento Eletronico(id, equipamento, marca temp, coordenadas)
INSERT into Bip_Equipamento_Eletronico(id, equipamento, marca_temporal, coordenadas)
VALUES (1,1,'12:00:01',1);

	--Condutor(CC, nome, contacto)
INSERT INTO Condutor(CC, nome, contacto)
VALUES (111222111,'Charles Leclerc',922555888),(111333111,'Carlos Sainz',922555688),
(111444111,'Fernando Alonso',922555788);

	--Veiculo(matricula, condutor, equipamento, cliente)
INSERT INTO Veiculo(matricula, condutor, equipamento, cliente)
VALUES ('FF17FF',111222111,1,111222333);

	--Zona Verde(veiculo, coordenadas, raio)
INSERT INTO Zona_Verde(veiculo, coordenadas, raio)
VALUES ('FF17FF',2,3);
commit transaction;