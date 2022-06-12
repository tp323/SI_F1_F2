package isel.sisinf.grp02;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.Cliente;
import isel.sisinf.grp02.orm.ClienteParticular;
import org.junit.Test;

import static org.junit.Assert.*;

/*** tests based on initial data from db set by initial insert ***/
public class AppTest {

    private final JPAContext ctx = new JPAContext();

    @Test
    public void ClienteCreate(){
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            ctx.createCliente(c);
            Cliente nc = ctx.readCliente(254256431);
            assertNotNull(nc);
            assertEquals(nc,c);
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteParticularCreateWithoutRef(){
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            ctx.buildClienteFromInput(254256431, "Rapaz da Aldeia", "Aldeia", "923453432",0,787565333);
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            ClienteParticular cp = new ClienteParticular(787565333,c);
            ClienteParticular ncp = ctx.readClienteParticular(787565333);
            Cliente nc = ctx.readCliente(254256431);
            assertNotNull(nc);
            assertTrue(c.equals(nc));
            assertTrue(cp.equals(ncp));
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteParticularCreateWithRef(){
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            ctx.buildClienteFromInput(254256431, "Rapaz da Aldeia", "Aldeia", "923453432",121222333,787565333);
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            c.setRefCliente(ctx.readClienteParticular(121222333));
            ClienteParticular cp = new ClienteParticular(787565333,c);
            ClienteParticular ncp = ctx.readClienteParticular(787565333);
            Cliente nc = ctx.readCliente(254256431);
            assertNotNull(ncp);
            assertNotNull(nc);
            assertTrue(c.equals(nc));
            assertTrue(cp.equals(ncp));
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteUpdateWithoutRef(){
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            ctx.updateClienteFromInput(254256431, "Rapaz da Aldeia", "Aldeia", "923453432",0,100000000);
            Cliente c = new Cliente(254256431, "Rapaz", "Ali", "222333444");
            Cliente nc = ctx.readCliente(254256431);
            ClienteParticular ncp = ctx.readClienteParticular(100000000);
            assertNotNull(nc);
            assertNotNull(ncp);
            c.setClienteParticular(ncp);
            assertTrue(c.equals(nc));
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteUpdateWitRef() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            ctx.updateClienteFromInput(254256431, "Rapaz da Aldeia", "Aldeia", "923453432", 999999999, 100000000);
            Cliente c = new Cliente(254256431, "Rapaz", "Ali", "222333444");
            Cliente nc = ctx.readCliente(254256431);
            ClienteParticular ncp = ctx.readClienteParticular(100000000);
            assertNotNull(nc);
            assertNotNull(ncp);
            c.setClienteParticular(ncp);
            assertTrue(c.equals(nc));
            ctx.rollback();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}