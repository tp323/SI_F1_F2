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
    public void VeiculoRepoExistsWithData() {
        assertNotNull(ctx.getVeiculos().findAll());
    }


}
