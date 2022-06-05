package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Cliente;

import java.util.Set;

public interface ICliente_Particular {

    int getCC();
    Cliente getClient();
    Set<Cliente> getReferred();

    void setCC(int cc);
    void setClient(Cliente client);
    void setReferred(Set<Cliente> referred);
}
