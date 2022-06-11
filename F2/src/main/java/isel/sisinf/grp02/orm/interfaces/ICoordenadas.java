package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Bip;
import isel.sisinf.grp02.orm.ZonaVerde;

import java.util.Set;

public interface ICoordenadas {
    Long getId();
    Float getLatitude();
    Float getLongitude();
    Bip getBip();
    Set<ZonaVerde> getZonaVerde();

    void setId(Long id);
    void setLatitude(Float latitude);
    void setLongitude(Float longitude);
    void setBip(Bip bip);
    void setZonaVerde(Set<ZonaVerde> zona);
}
