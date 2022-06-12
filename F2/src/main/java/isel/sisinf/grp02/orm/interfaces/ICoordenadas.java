package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Bip;
import isel.sisinf.grp02.orm.ZonaVerde;

import java.util.Set;

public interface ICoordenadas {
    Long getId();
    float getLatitude();
    float getLongitude();
    Bip getBip();
    Set<ZonaVerde> getZonaVerde();

    void setId(Long id);
    void setLatitude(float latitude);
    void setLongitude(float longitude);
    void setBip(Bip bip);
    void setZonaVerde(Set<ZonaVerde> zona);
}
