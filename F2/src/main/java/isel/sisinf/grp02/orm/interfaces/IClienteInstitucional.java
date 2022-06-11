package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Cliente;

public interface IClienteInstitucional {
    Cliente getCliente();
    String getNomeContacto();

    void setCliente(Cliente cliente);
    void setNomeContacto(String nomeContacto);
}
