package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Cliente_Institucional;
import isel.sisinf.grp02.JPAObjects.Cliente_Particular;
import isel.sisinf.grp02.JPAObjects.Veiculo;

import java.util.Set;

public interface ICliente {
    int getNif();
    String getNome();
    String getMorada();
    String getTelefone();
    Boolean getAtivo();
    Cliente_Particular getRefCliente();
    Cliente_Particular getClienteParticular();
    Cliente_Institucional getClienteInstitucional();
    Set<Veiculo> getVeiculos();

    void setNif(Integer nif);
    void setNome(String nome);
    void setMorada(String morada);
    void setTelefone(String telefone);
    void setRefCliente(Cliente_Particular refCliente);
    void setClienteParticular(Cliente_Particular cliente_particular);
    void setClienteInstitucional(Cliente_Institucional cliente_institucional);
    void setAtivo(Boolean ativo);
    void setVeiculos(Set<Veiculo> veiculos);
}