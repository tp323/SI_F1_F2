package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Bip;
import isel.sisinf.grp02.orm.Veiculo;
import isel.sisinf.grp02.orm.ZonaVerde;

import java.util.Set;

public interface ICoordenadas {
    Long getId();
    Float getLatitude();
    Float getLongitude();
    Bip getBip();
    ZonaVerde getZonaVerde();

    void setId(Long id);
    void setLatitude(Float latitude);
    void setLongitude(Float longitude);
    void setBip(Bip bip);
    void setZonaVerde(ZonaVerde zona);

    interface ICondutor {
        Integer getCC();
        String getNome();
        String getContacto();
        Set<Veiculo> getVeiculos();

        void setCC(Integer cc);
        void setNome(String nome);
        void setContacto(String contacto);
        void setVeiculos(Set<Veiculo> veiculos);
    }
}
