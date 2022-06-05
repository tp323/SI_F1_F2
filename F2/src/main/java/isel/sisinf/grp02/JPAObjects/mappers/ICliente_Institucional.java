package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Cliente;

public interface ICliente_Institucional {

    Cliente getCliente();
    String getNomeContacto();


    void setCliente(Cliente cliente);
    void setNomeContacto(String nomeContacto);
}
