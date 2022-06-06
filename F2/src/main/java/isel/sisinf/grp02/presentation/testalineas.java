package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.Cliente;

public class testalineas {
    public static void test() throws Exception {
        //d
        try(JPAContext ctx= new JPAContext())
        {


            ctx.beginTransaction();

            System.out.println(ctx.fromCliente(111222333));
            Cliente c = new Cliente();
            c.setNif(254256431);
            c.setMorada("Aldeia");
            c.setNome("Rapaz da Aldeia");
            c.setTelefone(923453432);
            System.out.println(ctx.createCliente(c));


            ctx.commit();

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }

    }
    public static void main(String[] args) throws Exception {
        test();
    }
}
