package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Bip;
import isel.sisinf.grp02.orm.Zona_Verde;

import java.util.Set;

public interface ICoordenadas {
    Long getId();
    Float getLatitude();
    Float getLongitude();
    Bip getBip();
    Set<Zona_Verde> getZonaVerde();

    void setId(Long id);
    void setLatitude(Float latitude);
    void setLongitude(Float longitude);
    void setBip(Bip bip);
    void setZonaVerde(Set<Zona_Verde> zona);
}
