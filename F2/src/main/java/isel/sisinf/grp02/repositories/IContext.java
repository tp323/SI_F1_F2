package isel.sisinf.grp02.repositories;


public interface IContext extends AutoCloseable {

    void beginTransaction();
    void commit();
    void rollback();
    void flush();
    void connect();

    IClienteRepository getClientes();
    ICliente_ParticularRepository getClientesParticulares();
    ICliente_InstitucionalRepository getClientesInstitucionais();
    IEquipamentoRepository getEquipamentos();
    IVeiculoRepository getVeiculos();
    ICondutorRepository getCondutores();
    IZonaVerdeRepository getZonasVerdes();
    ICoordenadasRepository getCoordenadas();
    IBipRepository getBips();

}
