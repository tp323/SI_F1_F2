package isel.sisinf.grp02.repositories;

public interface IContext extends AutoCloseable {

    void beginTransaction();
    void commit();
    void rollback();
    void flush();

    IClienteRepository getClientes();
    ICliente_ParticularRepository getClientesParticulares();
    ICliente_InstitucionalRepository getClientesInstitucionais();

    IVeiculoRepository getVeiculos();
    ICondutorRepository getCondutores();

}
