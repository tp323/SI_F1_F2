package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.ClienteInstitucional;
import isel.sisinf.grp02.orm.ClienteParticular;
import isel.sisinf.grp02.orm.Veiculo;

import java.util.Set;

public interface ICliente {
    int getNif();
    String getNome();
    String getMorada();
    String getTelefone();
    boolean getAtivo();
    ClienteParticular getRefCliente();
    ClienteParticular getClienteParticular();
    ClienteInstitucional getClienteInstitucional();
    Set<Veiculo> getVeiculos();

    void setNif(Integer nif);
    void setNome(String nome);
    void setMorada(String morada);
    void setTelefone(String telefone);
    void setRefCliente(ClienteParticular refCliente);
    void setClienteParticular(ClienteParticular cliente_particular);
    void setClienteInstitucional(ClienteInstitucional cliente_institucional);
    void setAtivo(boolean ativo);
    void setVeiculos(Set<Veiculo> veiculos);
}
