package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Cliente;

import java.util.Set;

public interface ICliente_Particular {

    int getCC();
    Cliente getCliente();
    Set<Cliente> getReferred();

    void setCC(int cc);
    void setCliente(Cliente client);
    void setReferred(Set<Cliente> referred);
}
