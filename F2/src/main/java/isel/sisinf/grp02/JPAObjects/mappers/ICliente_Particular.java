package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Cliente;

public interface ICliente_Particular {

    long getCC();
    Cliente getClient();

    void setCC(long cc);
    void setClient(Cliente client);
}
