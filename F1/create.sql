BEGIN transaction;
	--Cliente(NIF, nome, morada, telefone, ref cliente)
	create table IF NOT EXISTS Cliente(
		NIF int primary key,
		nome varchar(20),
		morada varchar(150),
		telefone varchar(10),
		ref_cliente int--,
		--FOREIGN KEY (ref_cliente)
     	--	REFERENCES Cliente_Particular (CC) ON DELETE CASCADE ON UPDATE cascade
	);
	--Cliente_Particular(CC, cliente)
	create table IF NOT EXISTS Cliente_Particular(
		CC int primary key,
		cliente int,
		FOREIGN KEY (cliente)
     		REFERENCES Cliente (NIF) ON DELETE CASCADE ON UPDATE cascade
	);
	--Cliente_Institucional(nome de contacto, cliente)
	create table IF NOT EXISTS Cliente_Institucional(
		nome_contacto varchar(20),
		cliente int primary key,
		FOREIGN KEY (cliente)
     		REFERENCES Cliente (NIF) ON DELETE CASCADE ON UPDATE cascade
	);

	--Equipamento(id, estado)
	create table IF NOT EXISTS Equipamento_Eletronico(
		id int primary key,
		estado varchar(15),
		constraint estado check (estado IN ('Activo', 'PausaDeAlarmes', 'Inactivo'))
	);
	--Coordenadas(id, latitude, longitude)
	create table IF NOT EXISTS Coordenadas(
		id int primary key,
		latitude numeric(3,1),
		longitude numeric(3,1)
	);
	--Bip Equipamento Eletronico(id, equipamento, marca temp, coordenadas)
	create table IF NOT EXISTS Bip_Equipamento_Eletronico(
		id int,
		equipamento int,
		marca_temporal date, 
		coordenadas int,
		primary key(id, equipamento),
		FOREIGN KEY (equipamento)
     		REFERENCES Equipamento_Eletronico (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (coordenadas)
     		REFERENCES Coordenadas (id) ON DELETE CASCADE ON UPDATE cascade
	);
	--Condutor(nome, contacto)
	create table IF NOT EXISTS Condutor(
		id int primary key, 
		nome varchar(20),
		contacto varchar(10)
	);
	--Veiculo(matricula, condutor, equipamento, cliente)
	create table IF NOT EXISTS Veiculo(
		matricula varchar(6) primary key,
		condutor int,
		equipamento int,
		cliente int,
		FOREIGN KEY (condutor)
     		REFERENCES Condutor (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (equipamento)
     		REFERENCES Equipamento_Eletronico (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (cliente)
     		REFERENCES Cliente (NIF) ON DELETE CASCADE ON UPDATE cascade
	);
	--Zona Verde(veiculo, coordenadas, raio)
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


commit transaction;
