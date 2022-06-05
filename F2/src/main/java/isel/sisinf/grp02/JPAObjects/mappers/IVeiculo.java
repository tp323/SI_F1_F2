package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Cliente;
import isel.sisinf.grp02.JPAObjects.Condutor;
import isel.sisinf.grp02.JPAObjects.Equipamento_Eletronico;

public interface IVeiculo {

    String getMatricula();
    Condutor getCoundutor();
    Equipamento_Eletronico getEquipamento();
    Cliente getCliente();
    int getAlarm();

    void setMatricula(String matricula);
    void setCondutor(Condutor condutor);
    void setEquipamento(Equipamento_Eletronico equipamento);
    void setCliente(Cliente cliente);
}
