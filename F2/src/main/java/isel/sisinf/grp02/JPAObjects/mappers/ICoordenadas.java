package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Bip;
import isel.sisinf.grp02.JPAObjects.ZonaVerde;

public interface ICoordenadas {
    Long getID();
    Float getLatitude();
    Float getLongitude();
    Bip getBip();
    ZonaVerde getZonaVerde();

    void setID(Long id);
    void setLatitude(Float latitude);
    void setLongitude(Float longitude);
    void setBip(Bip bip);
    void setZonaVerde(ZonaVerde zona);

}
