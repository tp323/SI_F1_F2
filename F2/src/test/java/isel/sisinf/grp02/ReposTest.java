package isel.sisinf.grp02;


import isel.sisinf.grp02.data_acess.JPAContext;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReposTest {
    private final JPAContext ctx = new JPAContext();

    @Test
    public void ClientFindbyKey() {
        assertNotNull(ctx.getClientes().findByKey(111222333));
    }

    @Test
    public void ClienteParticularFindbyKey() {
        assertNotNull(ctx.getClientesParticulares().findByKey(121222333));
    }

    @Test
    public void VeiculoFindbyKey() {
        assertNotNull(ctx.getVeiculos().findByKey("FF17FF"));
    }


}
