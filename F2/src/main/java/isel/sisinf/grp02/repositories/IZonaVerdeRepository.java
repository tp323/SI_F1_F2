package isel.sisinf.grp02.repositories;

import isel.sisinf.grp02.orm.ZonaVerde;

import java.util.Collection;
import java.util.List;

public interface IZonaVerdeRepository extends IRepository<ZonaVerde, Collection<ZonaVerde>, Long>{
    List<ZonaVerde> findByParameters (Float lat, Float log, String vei, Integer r);

}
