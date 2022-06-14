package isel.sisinf.grp02.repositories;

import isel.sisinf.grp02.orm.Coordenadas;
import isel.sisinf.grp02.orm.Veiculo;
import isel.sisinf.grp02.orm.ZonaVerde;

import java.util.Collection;
import java.util.List;

public interface IZonaVerdeRepository extends IRepository<ZonaVerde, Collection<ZonaVerde>, Long>{
    ZonaVerde findByParameters (Coordenadas coordenadas, Veiculo veiculo, Integer r);

}
