package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.Cliente;
import isel.sisinf.grp02.orm.Cliente_Particular;
import jakarta.xml.bind.annotation.XmlType;

public class testalineas {
    public static void test() throws Exception {
        //d
        //create
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            //dCreate(ctx);
            //dDelete(ctx);
            dUpdate(ctx);
            ctx.commit();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        test();
    }

    public static void dCreate(JPAContext ctx){
        Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", 923453432);
        Cliente_Particular cp = new Cliente_Particular();
        cp.setCC(987987987);
        cp.setCliente(c);
        c.setClienteParticular(cp);
        System.out.println(ctx.createCliente(c));
        System.out.println(ctx.createClienteParticular(cp));
    }

    public static void dDelete(JPAContext ctx){
        Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", 923453432);
        Cliente_Particular cp = new Cliente_Particular();
        cp.setCC(987987987);
        cp.setCliente(c);
        c.setClienteParticular(cp);
        ctx.deleteCliente(c);
        ctx.deleteClienteParticular(cp);
    }

    public static void dUpdate(JPAContext ctx){
        Cliente c = ctx.getClientes().findByKey(121222333);
        Cliente nc = new Cliente(121222333, "Rapaz da Aldeia", "Aldeia", 923453432);
        Cliente_Particular cp = ctx.getClientesParticulares().findByKey(100000000);
        nc.setRefCliente(cp);
        ctx.updateCliente(nc);
    }
}
