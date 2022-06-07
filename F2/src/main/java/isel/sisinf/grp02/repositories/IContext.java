package isel.sisinf.grp02.repositories;

import isel.sisinf.grp02.orm.interfaces.IEquipamento;

public interface IContext extends AutoCloseable {

    void beginTransaction();
    void commit();
    void rollback();
    void flush();

    IClienteRepository getClientes();
    ICliente_ParticularRepository getClientesParticulares();
    ICliente_InstitucionalRepository getClientesInstitucionais();
    IEquipamentoRepository getEquipamentos();
    IVeiculoRepository getVeiculos();
    ICondutorRepository getCondutores();
    IZonaVerdeRepository getZonasVerdes();

}
