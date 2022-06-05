package isel.sisinf.grp02.repositories;

public interface IContext extends AutoCloseable {

    void beginTransaction();
    void commit();
    void flush();

    IClienteRepository getClientes();
    IVeiculoRepository getVeiculos();
    ICondutorRepository getCondutores();

}
