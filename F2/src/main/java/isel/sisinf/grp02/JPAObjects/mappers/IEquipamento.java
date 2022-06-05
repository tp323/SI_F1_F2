package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Bip;
import isel.sisinf.grp02.JPAObjects.Veiculo;

import java.util.Set;

public interface IEquipamento {

    Long getID();
    String getStatus();
    Veiculo getVeiculo();
    Set<Bip> getBips();

    void setID(Long id);
    void setStatus(String status);
    void setVeiculo(Veiculo veiculo);
    void setBips(Set<Bip> bips);
}
