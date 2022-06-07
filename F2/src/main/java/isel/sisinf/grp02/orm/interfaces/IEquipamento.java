package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.*;

import java.util.Set;

public interface IEquipamento {

    Long getId();
    String getEstado();
    Veiculo getVeiculo();
    Set<Bip> getBips();

    void setID(Long id);
    void setEstado(String status);
    void setVeiculo(Veiculo veiculo);
    void setBips(Set<Bip> bips);
}
