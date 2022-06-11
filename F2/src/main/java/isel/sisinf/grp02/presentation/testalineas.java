package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.Cliente;
import isel.sisinf.grp02.orm.ClienteParticular;

import java.util.LinkedList;
import java.util.List;

public class testalineas {
    public static void test() throws Exception {
        //d
        //create
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            //dCreate(ctx);
            //dDelete(ctx);
            //dUpdate(ctx);
            //ctx.procedure_createVehicle("123456", 111111115, 3, 100000000, 01, 01 ,01);
            ctx.createView();
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

    public static List<ClienteParticular> dCreate(
            JPAContext ctx,
            int nif,
            String name,
            String residence,
            String phone,
            int refClient,
            int cc
    ){
        Cliente client = new Cliente(nif, name, residence, phone, true);
        ClienteParticular cp = new ClienteParticular();
        ClienteParticular ref = new ClienteParticular();
        ref.setCC(refClient);
        client.setRefCliente(ctx.getClienteParticular().read(ref.getCC()));
        cp.setCC(cc);
        cp.setCliente(client);
        client.setClienteParticular(cp);
        ctx.beginTransaction();
        ctx.createCliente(client);
        ctx.commit();
        ctx.beginTransaction();
        int clientId = ctx.createClienteParticular(cp);
        ctx.commit();
        ctx.beginTransaction();
        ClienteParticular insertedClient = ctx.getClienteParticular().read(clientId);
        ctx.commit();
        List<ClienteParticular> clientList = new LinkedList<>();
        clientList.add(insertedClient);
        return clientList;


        /*Cliente c = new Cliente(254256432, "Rapaz da Aldeia", "Aldeia", "923453432");
        ClienteParticular cp = new ClienteParticular();
        cp.setCC(987987988);
        cp.setCliente(c);
        c.setClienteParticular(cp);
        ClienteParticular insertedClient = ctx.createClienteParticular(cp, c);

        List<ClienteParticular> clientList = new LinkedList<>();
        clientList.add(insertedClient);
        Table.createTable(clientList, new Scanner(System.in), ClienteParticular::toArray);
        return clientList;*/

        /*

        return new String[0][];*/
    }

    public static void dDelete(JPAContext ctx){
        Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
        ClienteParticular cp = new ClienteParticular();
        cp.setCC(987987987);
        cp.setCliente(c);
        c.setClienteParticular(cp);
        ctx.deleteCliente(c);
        //ctx.deleteClienteParticular(cp);
    }

    public static void dUpdate(JPAContext ctx){
        Cliente c = ctx.getClientes().findByKey(121222333);
        Cliente nc = new Cliente(121222333, "Rapaz da Aldeia", "Aldeia", "923453432");
        ClienteParticular cp = ctx.getClientesParticulares().findByKey(100000000);
        nc.setRefCliente(cp);
        ctx.updateCliente(nc);
    }
}
