package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Cliente;
import isel.sisinf.grp02.orm.Condutor;
import isel.sisinf.grp02.orm.EquipamentoEletronico;
import isel.sisinf.grp02.orm.ZonaVerde;

import java.util.Set;

public interface IVeiculo {
    String getMatricula();
    Condutor getCondutor();
    EquipamentoEletronico getEquipamento();
    Cliente getCliente();
    Integer getAlarms();
    Set<ZonaVerde> getZonasVerdes();

    void setMatricula(String matricula);
    void setCondutor(Condutor condutor);
    void setEquipamento(EquipamentoEletronico equipamento);
    void setCliente(Cliente cliente);
    void setAlarms(Integer alarms);
    void setZonasVerdes(Set<ZonaVerde> zonasVerdes);
}
