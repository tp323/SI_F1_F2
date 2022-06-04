package isel.sisinf.grp02.JPAObjects;

import java.util.Set;

public interface ICliente {
    int getNif();
    String getNome();
    String getMorada();
    String getTelefone();
    Boolean getAtivo();
    Cliente_Particular getRefCliente();
    Set<Veiculo> getVeiculos();

    void setNif(Integer nif);
    void setNome(String nome);
    void setMorada(String morada);
    void setTelefone(String telefone);
    void setRefCliente(Cliente_Particular refCliente);
    void setAtivo(Boolean ativo);
}
