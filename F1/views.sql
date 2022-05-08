CREATE OR REPLACE VIEW todos_alarmes AS
	SELECT matricula, nome, latitude, longitude, marca_temporal
	FROM alarmes al 
	inner join bip_equipamento_eletronico bip on al.bip=bip.id 
	inner join equipamento_eletronico eq on equipamento = eq.id 
	inner join veiculo v on eq.id = v.equipamento
	inner join condutor cond on v.condutor=cond.cc
	inner join coordenadas coord on coordenadas = coord.id;


--test insert_view_alarme
--insert into todos_alarmes(matricula, nome, latitude, longitude, marca_temporal) values ('ff22ff', 'elu', 2.0, 3.4, '2016-01-10 13:34:14');

--todos_alarmes(matricula, nome, latitude, longitude, marca_temporal)
CREATE OR REPLACE FUNCTION insert_view_alarme()
RETURNS trigger AS $$
	DECLARE
	equip int:= null;
	cond int:= random_number(9);
	eq_id int:= null;
	coord_id int:= null;
	bip_id int:= null;
	cliente_hardcoded int:= 111222333;
    BEGIN
		INSERT INTO Equipamento_Eletronico(estado) values('Inactivo') returning id into eq_id;
		insert into Condutor(CC, nome, contacto) values(cond, new.nome, null);
       	insert into veiculo(matricula, condutor, equipamento, cliente) values(new.matricula, cond, eq_id, cliente_hardcoded);
		INSERT INTO Coordenadas(latitude, longitude) VALUES (new.latitude, new.longitude) returning id into coord_id;
		INSERT into Bip_Equipamento_Eletronico(equipamento, marca_temporal, coordenadas) VALUES (eq_id, new.marca_temporal,coord_id) returning id into bip_id;
		INSERT INTO alarmes(bip) VALUES (bip_id);
		return new;
	END;
$$LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER insert_view_alarmes
	INSTEAD OF INSERT ON todos_alarmes
	FOR EACH ROW
	EXECUTE FUNCTION insert_view_alarme();


CREATE OR REPLACE FUNCTION random_number(length integer)
RETURNS int AS $$
	DECLARE
		numbers text[] := '{0,1,2,3,4,5,6,7,8,9}';
		numb text := '';
		n integer := 0;
		cnt integer=0;
	BEGIN
		IF length < 0 then
			raise exception 'Given length cannot be less than 0';
		END IF;
		for n in 1..length loop
			numb := numb || numbers[1+random()*(array_length(numbers, 1)-1)];
			cnt = cnt +1;
		END loop;
	RETURN numb;
	END;
$$LANGUAGE plpgsql;
