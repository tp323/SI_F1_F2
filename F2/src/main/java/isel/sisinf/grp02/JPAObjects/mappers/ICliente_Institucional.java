package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Cliente;

public interface ICliente_Institucional {

    Cliente getDadosCliente();
    String getNomeContacto();


    void setDadosCliente(Cliente cliente);
    void setNomeContacto(String nomeContacto);
}
