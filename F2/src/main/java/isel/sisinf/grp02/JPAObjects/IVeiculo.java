package isel.sisinf.grp02.JPAObjects;


public interface IVeiculo {
    String getMatricula();
    Condutor getCondutor();
    Equipamento_Eletronico getEquipamento();
    Cliente getCliente();
    Integer getAlarms();

    void setMatricula(String matricula);
    void setCondutor(Condutor condutor);
    void setEquipamento(Equipamento_Eletronico equipamento);
    void setCliente(Cliente cliente);
    void setAlarms(Integer alarms);
}
