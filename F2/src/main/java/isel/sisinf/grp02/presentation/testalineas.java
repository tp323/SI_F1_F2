package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.*;
import jakarta.xml.bind.annotation.XmlType;

import java.util.*;

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
            hnoproc(ctx);
            //ctx.createView();
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

    /*** SEGUIR PRESENTE LÓGICA DE ERROR HANDLING OU ENVOLVER EM TRY CATCH APENAS? ***/
    public static void hnoproc(JPAContext ctx){
        String matricula = "zz24zz";
        int ccCondutor = 111111113;
        long idEquip = 3;
        int nifCliente = 999999999;
        Float latitude = 6f;
        Float longitude = 9.0f;
        Integer raio = 3;
        Condutor condutor = ctx.readCondutor(ccCondutor);
        /***    EXCEÇÃO SE O CARRO JÁ EXISTIR PARA AQUELA MATRICULA    ***/
        if(condutor != null) {
            //TODO(ATIRAR EXCEÇÃO does not exist in db )
        }
        EquipamentoEletronico equip = ctx.getEquipamentos().findByKey(idEquip);
        if(equip != null) {
            //TODO(ATIRAR EXCEÇÃO does not exist in db )
        }
        Cliente cliente = ctx.getClientes().findByKey(nifCliente);
        if(equip != null) {
            //TODO(ATIRAR EXCEÇÃO does not exist in db )
        }
        Veiculo v = new Veiculo(matricula, condutor, equip, cliente);
        ctx.createVeiculo(v);
        if(latitude!=null && longitude != null && raio != null){
            /***    add zona verde  ***/
            /***    check if coordenadas already exist in db or just insert      ***/
            Coordenadas c = new Coordenadas(latitude,longitude);
            ZonaVerde zv = new ZonaVerde(c, v, raio);
            /***        EM PRINCIPIO O TRECHO DE CÓDIGO A SEGUIR N DEVE SER PRECISO DEVIDO AO ON CASCADE     ***/
            /*Set<Zona_Verde> setZV = new HashSet<Zona_Verde>();
            setZV.add(zv);
            v.setZonasVerdes(zv);*/
        }
    }
}
