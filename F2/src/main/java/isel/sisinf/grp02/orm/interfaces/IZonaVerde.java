package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Coordenadas;
import isel.sisinf.grp02.orm.Veiculo;

public interface IZonaVerde {
    Long getID();
    Coordenadas getCoodenadas();
    Veiculo getVeiculo();
    int getRaio();

    void setID(Long id);
    void setCoordenadas(Coordenadas coordenadas);
    void setVeiculo(Veiculo veiculo);
    void getRaio(int raio);
}
