package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Coordenadas;
import isel.sisinf.grp02.JPAObjects.Veiculo;

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
