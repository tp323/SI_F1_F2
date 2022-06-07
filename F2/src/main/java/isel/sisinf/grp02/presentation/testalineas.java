package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.Cliente;
import isel.sisinf.grp02.orm.Cliente_Particular;

import java.util.Scanner;

public class testalineas {
    public static void test() throws Exception {
        //d
        //create
        try(JPAContext ctx = new JPAContext()) {
            ctx.beginTransaction();
            dCreate(ctx);
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

    public static String[][] dCreate(
            JPAContext ctx/*,
            int nif,
            String name,
            String residence,
            String phone,
            int refClient,
            int cc*/
    ){

        /*Cliente client = new Cliente(nif, name, residence, phone, true);
        Cliente_Particular cp = new Cliente_Particular();
        Cliente_Particular ref = new Cliente_Particular();
        ref.setCC(refClient);
        client.setRefCliente(ctx.getClienteParticular().read(ref));
        cp.setCC(cc);
        cp.setCliente(client);
        client.setClienteParticular(cp);
        ctx.beginTransaction();
        Cliente_Particular insertedClient = ctx.createClienteParticular(cp, client);
        ctx.commit();*/

        Cliente c = new Cliente(254256432, "Rapaz da Aldeia", "Aldeia", "923453432");
        Cliente_Particular cp = new Cliente_Particular();
        cp.setCC(987987988);
        cp.setCliente(c);
        c.setClienteParticular(cp);
        Cliente_Particular insertedClient = ctx.createClienteParticular(cp, c);

        String[][] clientList = new String[2][];
        clientList[0] = new String[]{"NIF", "Nome", "Morada", "Telefone", "Referencia Cliente"};
        clientList[1] = insertedClient.getCliente().toArray();
        Table.createTable(clientList, new Scanner(System.in));
        return clientList;

        /*return new String[0][];*/
    }

    public static void dDelete(JPAContext ctx){
        Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
        Cliente_Particular cp = new Cliente_Particular();
        cp.setCC(987987987);
        cp.setCliente(c);
        c.setClienteParticular(cp);
        ctx.deleteCliente(c);
        ctx.deleteClienteParticular(cp);
    }

    public static void dUpdate(JPAContext ctx){
        Cliente c = ctx.getClientes().findByKey(121222333);
        Cliente nc = new Cliente(121222333, "Rapaz da Aldeia", "Aldeia", "923453432");
        Cliente_Particular cp = ctx.getClientesParticulares().findByKey(100000000);
        nc.setRefCliente(cp);
        ctx.updateCliente(nc);
    }
}
