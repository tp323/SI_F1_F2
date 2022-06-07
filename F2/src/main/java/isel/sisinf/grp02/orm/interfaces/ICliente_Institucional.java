package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Cliente;

public interface ICliente_Institucional {
    Cliente getCliente();
    String getNomeContacto();

    void setCliente(Cliente cliente);
    void setNomeContacto(String nomeContacto);
}
