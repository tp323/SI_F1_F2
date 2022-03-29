BEGIN transaction;
	--Cliente_Particular(CC, NIF, nome, morada, telefone, ref cliente)
	create table IF NOT EXISTS Cliente_Particular(
		CC int primary key,
		NIF int,
		nome varchar(20),
		morada varchar(150),
		telefone varchar(10),
		refcliente varchar(20),
		FOREIGN KEY (refcliente)
     		REFERENCES Cliente_Particular (CC) ON DELETE CASCADE ON UPDATE cascade
	);

	--Cliente_Institucional(NIF, nome, morada, telefone, nome de contacto)
	create table IF NOT EXISTS Cliente_Institucional(
		NIF int primary key,
		nome varchar(20),
		morada varchar(150),
		telefone varchar(10),
		nome_de_contacto varchar(20)
	);

	--Coordenadas(id, latitude, longitude)
	create table IF NOT EXISTS Coordenadas(
		id int primary key,
		latitude numeric(3,1),
		longitude numeric(3,1)
	);

	--Frota Veiculos(Cliente)


	--Zona Verde(coordenadas, raio)
	create table IF NOT EXISTS Zona_Verde(
		coordenadas int,
		veiculo varchar(6),
		raio int,
		primary key (coordenadas, veiculo),
		FOREIGN KEY (coordenadas)
     		REFERENCES Coordenadas (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (veiculo)
     		REFERENCES Veiculo (matricula) ON DELETE CASCADE ON UPDATE cascade
	);


	--Veiculo(matricula, nome, telefone condutor atual, id equipamento, estado equipamento)
	create table IF NOT EXISTS Veiculo(
		matricula varchar(6) primary key,
		nome varchar(20),
		telefone_condutor_atual varchar(10),
		id_equipamento int,
		estado_equipamento varchar(15),
		constraint estado_equipamento check (estado IN ('Activo', 'PausaDeAlarmes', 'Inactivo'))
	);

	--Bip Equipamento Eletronico(id, marca temp, coordenadas)
	create table IF NOT EXISTS Bip_Equipamento_Eletronico(
		id int,
		veiculo varchar(6),
		marca_temporal date, 
		coordenadas int,
		primary key(id, veiculo),
		FOREIGN KEY (veiculo)
     		REFERENCES Veiculo (matricula) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (coordenadas)
     		REFERENCES Coordenadas (id) ON DELETE CASCADE ON UPDATE cascade
	);

commit transaction;
