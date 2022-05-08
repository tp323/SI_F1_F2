BEGIN transaction;
	--Cliente(NIF, nome, morada, telefone, ref cliente)
	create table IF NOT EXISTS Cliente(
		NIF int primary key,
		nome varchar(25) not null,
		morada varchar(150) not null,
		telefone varchar(10) not null,
		ref_cliente int DEFAULT null,
		ativo boolean DEFAULT TRUE not null
	);
	--Cliente_Particular(CC, cliente)
	create table IF NOT EXISTS Cliente_Particular(
		CC int primary key,
		cliente int not null,
		FOREIGN KEY (cliente)
     		REFERENCES Cliente (NIF) ON DELETE CASCADE ON UPDATE cascade
	);

ALTER table if EXISTS Cliente DROP constraint if EXISTS ref_cliente_part;

ALTER table if EXISTS Cliente
ADD constraint ref_cliente_part foreign KEY (ref_cliente) references Cliente_Particular(CC) DEFERRABLE INITIALLY DEFERRED;


	--Cliente_Institucional(nome de contacto, cliente)
	create table IF NOT EXISTS Cliente_Institucional(
		nome_contacto varchar(25) not null,
		cliente int primary key,
		FOREIGN KEY (cliente)
     		REFERENCES Cliente (NIF) ON DELETE CASCADE ON UPDATE cascade
	);

	--Equipamento(id, estado)
	create table IF NOT EXISTS Equipamento_Eletronico(
		id serial primary key,
		estado varchar(15),
		constraint estado check (estado IN ('Activo', 'PausaDeAlarmes', 'Inactivo'))
	);
	--Coordenadas(id, latitude, longitude)
	create table IF NOT EXISTS Coordenadas(
		id serial primary key,
		latitude numeric(3,1) NOT NULL,
		longitude numeric(3,1) NOT NULL
	);
	--Bip Equipamento Eletronico(id, equipamento, marca temp, coordenadas)
	create table IF NOT EXISTS Bip_Equipamento_Eletronico(
		id serial primary key , --id é relativo ao numero do bit para o atual equi eletronico
		equipamento int NOT NULL,
		marca_temporal timestamp NOT NULL,
		coordenadas int NOT NULL,
		FOREIGN KEY (equipamento)
     		REFERENCES Equipamento_Eletronico (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (coordenadas)
     		REFERENCES Coordenadas (id) ON DELETE CASCADE ON UPDATE cascade
	);
	--Condutor(CC, nome, contacto)
	create table IF NOT EXISTS Condutor(
		CC int primary key, 
		nome varchar(20) not null,
		contacto varchar(10) not null
	);
	--Veiculo(matricula, condutor, equipamento, cliente)
	create table IF NOT EXISTS Veiculo(
		matricula varchar(6) primary key,
		condutor int NOT NULL,
		equipamento int NOT NULL,
		cliente int NOT NULL,
		FOREIGN KEY (condutor)
     		REFERENCES Condutor (CC) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (equipamento)
     		REFERENCES Equipamento_Eletronico (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (cliente)
     		REFERENCES Cliente (NIF) ON DELETE CASCADE ON UPDATE cascade
	);
	--Zona Verde(veiculo, coordenadas, raio)
	create table IF NOT EXISTS Zona_Verde(
		id serial primary key,
		coordenadas int not null,
		veiculo varchar(6) NOT NULL,
		raio int NOT NULL,
		FOREIGN KEY (coordenadas)
     		REFERENCES Coordenadas (id) ON DELETE CASCADE ON UPDATE cascade,
     	FOREIGN KEY (veiculo)
     		REFERENCES Veiculo (matricula) ON DELETE CASCADE ON UPDATE cascade
	);

	--Alarmes(id, zona verde)
	create table IF NOT EXISTS Alarmes(
		id serial primary key,
		bip int not null,
     	FOREIGN KEY (bip)
     		REFERENCES Bip_Equipamento_Eletronico (id) ON DELETE CASCADE ON UPDATE cascade
	);
	
	--Requests(id, equipamento, marca temp, coordenadas)
	create table IF NOT EXISTS Requests(
		id serial primary key, --id é relativo ao numero do bit para o atual equi eletronico
		equipamento int,
		marca_temporal timestamp,
		latitude numeric(3,1),
		longitude numeric(3,1)
	);
	
	--Invalid Requests()
	create table IF NOT EXISTS Invalid_Requests(
		id serial primary key, --id é relativo ao numero do bit para o atual equi eletronico
		equipamento int,
		marca_temporal timestamp,
		latitude numeric(3,1),
		longitude numeric(3,1)
	);
	
	--Alarm number per vehicle
    create table IF NOT EXISTS N_Alarms(
        veiculo varchar(6),
        alarms int not null,
        PRIMARY KEY (veiculo),
        FOREIGN KEY (veiculo)
            REFERENCES Veiculo (matricula) ON DELETE CASCADE ON UPDATE cascade
    );
	
commit transaction;
