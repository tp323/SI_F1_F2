package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.repo.JPAContext;

public class testalineas {
    public static void test() throws Exception {
        //d
        try(JPAContext ctx= new JPAContext())
        {


            ctx.beginTransaction();


            System.out.println(ctx.fromCliente(111222333));


            ctx.commit();



        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }

    }

}
