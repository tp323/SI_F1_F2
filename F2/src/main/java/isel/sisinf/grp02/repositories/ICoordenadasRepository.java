package isel.sisinf.grp02.repositories;

import isel.sisinf.grp02.orm.Coordenadas;

import java.util.Collection;

public interface ICoordenadasRepository extends IRepository<Coordenadas, Collection<Coordenadas>, Long>{
    Coordenadas findByLatLong (Float lat, Float log);
    Coordenadas findLast();

}
