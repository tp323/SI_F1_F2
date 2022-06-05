package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.*;

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

    interface IVeiculo {
        String getMatricula();
        Condutor getCondutor();
        Equipamento_Eletronico getEquipamento();
        Cliente getCliente();
        Integer getAlarms();
        Set<ZonaVerde> getZonasVerdes();

        void setMatricula(String matricula);
        void setCondutor(Condutor condutor);
        void setEquipamento(Equipamento_Eletronico equipamento);
        void setCliente(Cliente cliente);
        void setAlarms(Integer alarms);
        void setZonasVerdes(Set<ZonaVerde> zonasVerdes);
    }
}
