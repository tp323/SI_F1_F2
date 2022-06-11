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
            //ctx.procedure_createVehicle("123456", 111111115, 3, 100000000, 01, 01 ,01);
            //hnoproc(ctx);
            //ctx.createView();
            ctx.commit();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        //test();
        try(JPAContext ctx = new JPAContext()) {
            System.out.println(Integer.toString(ctx.procedure_getAlarmNumber("FF17FF", 2015)));
            ctx.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void dDelete(JPAContext ctx){
        int cc = 121222333;
        // TODO(throw exception if cc not in db)
        ClienteParticular cp = ctx.readClienteParticular(cc);
        Cliente c = cp.getCliente();
        if(c.getRefCliente()!=null){
            ClienteParticular ref = c.getRefCliente();
            c.setRefCliente(null);
            ctx.deleteClienteParticular(ref);
        }
        ctx.deleteClienteParticular(c.getClienteParticular());
        ctx.deleteCliente(c);
    }

    public static void dUpdate(JPAContext ctx){
        int nif = 121222333;
        String nome = "Rapaz da Aldeia";
        String morada = "Aldeia";
        String num = "923453432";
        int ref = 100000000;

        Cliente c = ctx.getClientes().findByKey(nif);
        Cliente nc = new Cliente(nif, nome, morada, num);
        ClienteParticular cp = ctx.getClientesParticulares().findByKey(ref);
        nc.setRefCliente(cp);
        ctx.updateCliente(nc);
    }

    public static void e(JPAContext ctx){

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
