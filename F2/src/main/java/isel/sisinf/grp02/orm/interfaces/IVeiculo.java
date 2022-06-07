package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Cliente;
import isel.sisinf.grp02.orm.Condutor;
import isel.sisinf.grp02.orm.Equipamento_Eletronico;
import isel.sisinf.grp02.orm.Zona_Verde;

import java.util.Set;

public interface IVeiculo {
    String getMatricula();
    Condutor getCondutor();
    Equipamento_Eletronico getEquipamento();
    Cliente getCliente();
    Integer getAlarms();
    Set<Zona_Verde> getZonasVerdes();

    void setMatricula(String matricula);
    void setCondutor(Condutor condutor);
    void setEquipamento(Equipamento_Eletronico equipamento);
    void setCliente(Cliente cliente);
    void setAlarms(Integer alarms);
    void setZonasVerdes(Set<Zona_Verde> zonasVerdes);
}
