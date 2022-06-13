package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.*;

import java.math.BigDecimal;

public class testalineas {
    public static void test() throws Exception {
        //d
        //create
        try(JPAContext ctx = new JPAContext()) {
            //ctx.beginTransaction();
            //ctx.procedure_createVehicle("123456", 111111115, 3, 100000000, 01, 01 ,01);
            //e(ctx);
            //hnoproc(ctx);
            //hcomproc(ctx);
            //ctx.createView();
            //Coordenadas coord = new Coordenadas(6,6);

            //ctx.commit();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        test();
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
        System.out.println((ctx.function_getAlarmNumber("FF17FF", 2015)));
    }


    public static void hcomproc(JPAContext ctx) {
        String matricula = "zz24zz";
        int ccCondutor = 111111113;
        int idEquip = 3;
        int nifCliente = 999999999;
        BigDecimal latitude = new BigDecimal(6);
        BigDecimal longitude = new BigDecimal("9.0");
        Integer raio = 3;
        //ctx.procedure_createVehicle(matricula,ccCondutor,idEquip,nifCliente, null, null,null);

        ctx.procedure_createVehicle(matricula,ccCondutor,idEquip,nifCliente, raio, latitude,longitude);
    }
}
