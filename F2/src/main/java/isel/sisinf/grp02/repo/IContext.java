package isel.sisinf.grp02.repo;

import isel.sisinf.grp02.JPAObjects.ICondutor;

public interface IContext extends AutoCloseable {

    void beginTransaction();
    void commit();
    void flush();

    IClienteRepository getClientes();
    IVeiculoRepository getVeiculos();
    ICondutorRepository getCondutores();

}
