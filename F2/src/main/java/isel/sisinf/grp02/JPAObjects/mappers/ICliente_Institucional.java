package isel.sisinf.grp02.JPAObjects.mappers;

import isel.sisinf.grp02.JPAObjects.Cliente;

public interface ICliente_Institucional {

    String getNomeContacto();
    Cliente getDadosCliente();

    void setNomeContacto(String nomeContacto);
    void setDadosCliente(Cliente cliente);
}
