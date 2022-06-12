package isel.sisinf.grp02.repositories;

import isel.sisinf.grp02.orm.Coordenadas;
import isel.sisinf.grp02.orm.ZonaVerde;

import java.util.Collection;

public interface IZonaVerdeRepository extends IRepository<ZonaVerde, Collection<ZonaVerde>, Long>{
    //ZonaVerde findByLatLongVehicleRaio (Float lat, Float log, String vei, Integer r);

}
