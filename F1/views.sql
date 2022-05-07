CREATE OR REPLACE VIEW todos_alarmes AS
SELECT matricula, nome, latitude, longitude, marca_temporal
FROM alarmes al 
inner join bip_equipamento_eletronico bip on al.bip=bip.id 
inner join equipamento_eletronico eq on equipamento = eq.id 
inner join veiculo v on eq.id = v.equipamento
inner join condutor cond on v.condutor=cond.cc
inner join coordenadas coord on coordenadas = coord.id;

