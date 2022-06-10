package isel.sisinf.grp02;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.Cliente;
import org.junit.Test;

import static org.junit.Assert.*;

/*** tests based on initial data from db set by initial insert ***/
public class AppTest {

    private final JPAContext ctx = new JPAContext();

    @Test
    public void ClienteFindByKey() {
        assertNotNull(ctx.getClientes().findByKey(111222333));
        assertEquals( ctx.getClientes().findByKey(111222333).getNif(),111222333);
        assertEquals( ctx.getClientes().findByKey(111222333).getNome(),"O maior da minha aldeia");
        assertEquals( ctx.getClientes().findByKey(111222333).getMorada(),"Vila Nova de Robiães");
        assertEquals( ctx.getClientes().findByKey(111222333).getTelefone(),"911222111");
        assertNull( ctx.getClientes().findByKey(111222333).getRefCliente());
    }

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
        }finally {
            ctx.close();
        }
    }

    @Test
    public void ClienteUpdate() throws Exception{
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            ctx.createCliente(c);
            ctx.commit();
            ctx.beginTransaction();
            Cliente nc = new Cliente(254256431, "Rapaz de outra Aldeia", "Aldeia 2", "923453431");
            ctx.updateCliente(nc);
            assertNotNull(ctx.getClientes().findByKey(254256431));
            assertEquals( ctx.getClientes().findByKey(254256431).getNif(),254256431);
            assertEquals( ctx.getClientes().findByKey(254256431).getNome(),"Rapaz de outra Aldeia");
            assertEquals( ctx.getClientes().findByKey(254256431).getMorada(),"Aldeia 2");
            assertEquals( ctx.getClientes().findByKey(254256431).getTelefone(),"923453431");
            assertNull( ctx.getClientes().findByKey(254256431).getRefCliente());
            ctx.deleteCliente(nc);
            assertNull(ctx.getClientes().findByKey(254256431));
            ctx.commit();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }finally {
            ctx.close();
        }
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            ctx.createCliente(c);
            ctx.commit();
            ctx.beginTransaction();
            Cliente nc = new Cliente(254256431, "Rapaz de outra Aldeia", "Aldeia 2", "923453431");
            ctx.updateCliente(nc);
            assertNotNull(ctx.getClientes().findByKey(254256431));
            assertEquals( ctx.getClientes().findByKey(254256431).getNif(),254256431);
            assertEquals( ctx.getClientes().findByKey(254256431).getNome(),"Rapaz de outra Aldeia");
            assertEquals( ctx.getClientes().findByKey(254256431).getMorada(),"Aldeia 2");
            assertEquals( ctx.getClientes().findByKey(254256431).getTelefone(),"923453431");
            assertNull( ctx.getClientes().findByKey(254256431).getRefCliente());
            ctx.deleteCliente(nc);
            assertNull(ctx.getClientes().findByKey(254256431));
            ctx.commit();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }finally {
            ctx.close();
        }
    }

    @Test
    public void ClienteDelete() throws Exception{
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            //ctx.createCliente(c);
            //ctx.commit();
            //ctx.beginTransaction();

            assertNotNull(ctx.getClientes().findByKey(254256431));
            ctx.deleteCliente(c);
            ctx.commit();
            assertNull(ctx.getClientes().findByKey(254256431));
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }finally {
            ctx.close();
        }
    }
}