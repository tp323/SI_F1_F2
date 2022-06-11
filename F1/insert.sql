begin transaction;

	--Cliente(NIF, nome, morada, telefone, ref cliente)
INSERT INTO Cliente (NIF, nome, morada, telefone, ref_cliente)
VALUES (111222333, 'O maior da minha aldeia', 'Vila Nova de Robiães', '+351911222111', DEFAULT),
(121222333, 'Limitada', 'Ali', '+351911333222', 121222333),
(100000000, 'O menor da minha aldeia', 'Vila Velha de Robiães', '+351911332111', DEFAULT),
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
VALUES (111111113,'Charles Leclerc','+351922555888'),(111111114,'Carlos Sainz','+351922755688'),(111111115,'Max Verstappen','+351912555688'),
(111111116,'Fernando Alonso','+351922555788');

	--Veiculo(matricula, condutor, equipamento, cliente)
INSERT INTO Veiculo(matricula, condutor, equipamento, cliente)
VALUES ('FF17FF',111111113,1,111222333), ('FF18FF',111111114,2,111222333), ('RB16RB',111111115,3,121222333);

	--Zona Verde(veiculo, coordenadas, raio)
INSERT INTO Zona_Verde(veiculo, coordenadas, raio)
VALUES ('FF17FF',2,3);

commit;