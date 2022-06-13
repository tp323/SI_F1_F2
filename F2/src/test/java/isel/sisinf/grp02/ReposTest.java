package isel.sisinf.grp02;


import isel.sisinf.grp02.data_acess.JPAContext;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ReposTest {
    private final JPAContext ctx = new JPAContext();

    @Test
    public void ClientRepoExistsWithData() {
        ctx.connect();
        assertNotNull(ctx.getClientes().findAll());
        ctx.close();
    }

    @Test
    public void ClienteParticularRepoExistsWithData() {
        ctx.connect();
        assertNotNull(ctx.getClientesParticulares().findAll());
        ctx.close();
    }

    @Test
    public void ClienteInstitucionalRepoExistsWithData() {
        ctx.connect();
        assertNotNull(ctx.getClientesInstitucionais().findAll());
        ctx.close();
    }

    @Test
    public void EquipamentosRepoExistsWithData() {
        ctx.connect();
        assertNotNull(ctx.getEquipamentos().findAll());
        ctx.close();
    }

    @Test
    public void VeiculoRepoExistsWithData() {
        ctx.connect();
        assertNotNull(ctx.getVeiculos().findAll());
        ctx.close();
    }

    @Test
    public void CondutoresRepoExistsWithData() {
        ctx.connect();
        assertNotNull(ctx.getCondutores().findAll());
        ctx.close();
    }

    @Test
    public void ZonasVerdesRepoExistsWithData() {
        ctx.connect();
        assertNotNull(ctx.getZonasVerdes().findAll());
        ctx.close();
    }

    @Test
    public void CoordenadasRepoExistsWithData() {
        ctx.connect();
        assertNotNull(ctx.getCoordenadas().findAll());
        ctx.close();
    }

    @Test
    public void BipRepoExistsWithData() {
        ctx.connect();
        assertNotNull(ctx.getBips().findAll());
        ctx.close();
    }
}
