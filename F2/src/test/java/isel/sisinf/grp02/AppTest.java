package isel.sisinf.grp02;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.Cliente;
import org.junit.Test;

import static org.junit.Assert.*;

/*** tests based on initial data from db set by initial insert ***/
public class AppTest {

    private final JPAContext ctx = new JPAContext();

    @Test
    public void ClienteCreate() throws Exception{
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            ctx.createCliente(c);
            assertNotNull(ctx.getClientes().findByKey(254256431));
            assertEquals( ctx.getClientes().findByKey(254256431).getNif(),254256431);
            assertEquals( ctx.getClientes().findByKey(254256431).getNome(),"Rapaz da Aldeia");
            assertEquals( ctx.getClientes().findByKey(254256431).getMorada(),"Aldeia");
            assertEquals( ctx.getClientes().findByKey(254256431).getTelefone(),"923453432");
            assertNull( ctx.getClientes().findByKey(254256431).getRefCliente());
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteParticularCreate() throws Exception{
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            ctx.buildClienteFromInput(254256431, "Rapaz da Aldeia", "Aldeia", "923453432",0,787565333);
            assertNotNull(ctx.readClienteParticular(787565333));
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteDelete() throws Exception{
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            ctx.createCliente(c);

            assertNotNull(ctx.getClientes().findByKey(254256431));
            ctx.deleteCliente(c);
            assertFalse(ctx.getClientes().findByKey(254256431).getAtivo());
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}