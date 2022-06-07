package isel.sisinf.grp02;


import isel.sisinf.grp02.data_acess.JPAContext;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReposTest {
    private final JPAContext ctx = new JPAContext();

    @Test
    public void ClientRepoExistsWithData() {
        assertNotNull(ctx.getClientes().findAll());
    }

    @Test
    public void ClienteParticularRepoExistsWithData() {
        assertNotNull(ctx.getClientesParticulares().findAll());
    }

    @Test
    public void ClienteInstitucionalRepoExistsWithData() {
        assertNotNull(ctx.getClientesInstitucionais().findAll());
    }

    @Test
    public void EquipamentosRepoExistsWithData() {
        assertNotNull(ctx.getEquipamentos().findAll());
    }

    @Test
    public void VeiculoRepoExistsWithData() {
        assertNotNull(ctx.getVeiculos().findAll());
    }

    @Test
    public void CondutoresRepoExistsWithData() {
        assertNotNull(ctx.getCondutores().findAll());
    }

    @Test
    public void ZonasVerdesRepoExistsWithData() {
        assertNotNull(ctx.getZonasVerdes().findAll());
    }

    @Test
    public void CoordenadasRepoExistsWithData() {
        assertNotNull(ctx.getCoordenadas().findAll());
    }

    @Test
    public void BipRepoExistsWithData() {
        assertNotNull(ctx.getBips().findAll());
    }
}
