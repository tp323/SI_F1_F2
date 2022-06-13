package isel.sisinf.grp02;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/*** tests based on initial data from db set by initial insert ***/
public class AppTest {

    @Test
    public void ClienteCreate(){
        try(JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            ctx.createCliente(c);
            Cliente nc = ctx.readCliente(254256431);
            assertNotNull(nc);
            assertEquals(nc,c);
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteParticularCreateWithoutRef(){
        try(JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            ctx.buildClienteFromInput(254256431, "Rapaz da Aldeia", "Aldeia", "923453432",0,787565333);
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            ClienteParticular cp = new ClienteParticular(787565333,c);
            ClienteParticular ncp = ctx.readClienteParticular(787565333);
            Cliente nc = ctx.readCliente(254256431);
            assertNotNull(nc);
            assertEquals(c, nc);
            assertEquals(cp, ncp);
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteParticularCreateWithRef(){
        try(JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            ctx.buildClienteFromInput(254256431, "Rapaz da Aldeia", "Aldeia", "923453432",121222333,787565333);
            Cliente c = new Cliente(254256431, "Rapaz da Aldeia", "Aldeia", "923453432");
            c.setRefCliente(ctx.readClienteParticular(121222333));
            ClienteParticular cp = new ClienteParticular(787565333,c);
            ClienteParticular ncp = ctx.readClienteParticular(787565333);
            Cliente nc = ctx.readCliente(254256431);
            assertNotNull(ncp);
            assertNotNull(nc);
            assertEquals(c, nc);
            assertEquals(cp, ncp);
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteUpdateWithoutRef(){
        try(JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            ctx.updateClienteFromInput(111222333, "Rapaz da Aldeia", "Aldeia", "923453432",0,121222333);
            Cliente c = new Cliente(111222333, "Rapaz da Aldeia", "Aldeia", "923453432");
            Cliente nc = ctx.readCliente(111222333);
            ClienteParticular ncp = ctx.readClienteParticular(121222333);
            assertNotNull(nc);
            assertNotNull(ncp);
            c.setClienteParticular(ncp);
            assertEquals(c, nc);
            ctx.rollback();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteUpdateWitRef() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            ctx.updateClienteFromInput(111222333, "Rapaz da Aldeia", "Aldeia", "923453432", 999999999, 121222333);
            Cliente c = new Cliente(111222333, "Rapaz da Aldeia", "Aldeia", "923453432");
            Cliente nc = ctx.readCliente(111222333);
            ClienteParticular ncp = ctx.readClienteParticular(121222333);
            assertNotNull(nc);
            assertNotNull(ncp);
            c.setClienteParticular(ncp);
            c.setRefCliente(ctx.readClienteParticular(999999999));
            assertEquals(c, nc);
            ctx.rollback();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void ClienteDelete() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            if(ctx.readClienteParticular(787565333)==null) {
                ctx.buildClienteFromInput(254256431, "Rapaz da Aldeia", "Aldeia", "923453432", 121222333, 787565333);
                ctx.commit();
                ctx.beginTransaction();
            }
            Cliente c = ctx.readCliente(254256431);
            ClienteParticular cp = ctx.readClienteParticular(787565333);
            assertNotNull(c);
            assertNotNull(cp);
            ctx.deleteClienteParticularFromInput(254256431);
            Cliente nc = ctx.readCliente(254256431);
            ClienteParticular ncp = ctx.readClienteParticular(787565333);
            assertNull(nc);
            assertNull(ncp);
            ctx.rollback();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void TotalAlarmsForVehicle() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            int numbAlarms = ctx.function_getAlarmNumber("FF17FF", 2015);
            assertEquals(1, numbAlarms);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void TotalAlarmsForYear() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            int numbAlarms = ctx.function_getAlarmNumber(2015);
            assertEquals(1, numbAlarms);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    //TODO(test f with and without optimistic locking)

    @Test
    public void CreateVehicleWithProcedureWithNewZonaVerdeWithProcedure() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            ctx.createVehicleWithProcedure("zz24zz",111111113,3,999999999, 3,new BigDecimal(6), new BigDecimal(6));

            Condutor cond = ctx.readCondutor(111111113);
            EquipamentoEletronico eq = ctx.readEquipamentoEletronico(3);
            Cliente c = ctx.readCliente(999999999);
            Veiculo v = new Veiculo("zz24zz",cond,eq,c);
            Coordenadas coord = new Coordenadas(6,6);

            coord.setId(ctx.getCoordenadas().findByLatLong(6f,6f).getId());

            ZonaVerde zv = new ZonaVerde(coord,v,3);

            List<ZonaVerde> t = ctx.getZonasVerdes().findByParameters(6f,6f,"zz24zz",3);


            assertEquals(v, ctx.readVeiculo("zz24zz"));
            assertEquals(coord,ctx.getCoordenadas().findByLatLong(6f,6f));
            //TODO(CHECK ZONAS VERDES findByParameter does not work)
            //assertEquals(zv,ctx.getZonasVerdes().findByParameters(6f,6f,"zz24zz",3));
            ctx.rollback();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    @Test
    public void CreateVehicleWithProcedureWithoutNewZonaVerdeWithProcedure() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            ctx.createVehicleWithProcedure("zz24zz",111111113,3,999999999, null, null, null);

            Condutor cond = ctx.readCondutor(111111113);
            assertNotNull(cond);
            EquipamentoEletronico eq = ctx.readEquipamentoEletronico(3);
            Cliente c = ctx.readCliente(999999999);
            Veiculo v = new Veiculo("zz24zz",cond,eq,c);

            assertEquals(v, ctx.readVeiculo("zz24zz"));
            ctx.rollback();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    @Test
    public void CreateVehicleWithProcedureWithNewZonaVerdeWithoutProcedure() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            ctx.createVehicleWithoutProcedure("zz24zz",111111113,3,999999999, 3, new BigDecimal(6), new BigDecimal(6));

            Condutor cond = ctx.readCondutor(111111113);
            EquipamentoEletronico eq = ctx.readEquipamentoEletronico(3);
            Cliente c = ctx.readCliente(999999999);
            Veiculo v = new Veiculo("zz24zz",cond,eq,c);
            Coordenadas coord = new Coordenadas(6,6);

            coord.setId(ctx.getCoordenadas().findByLatLong(6f,6f).getId());

            ZonaVerde zv = new ZonaVerde(coord,v,3);

            List<ZonaVerde> t = ctx.getZonasVerdes().findByParameters(6f,6f,"zz24zz",3);


            assertEquals(v, ctx.readVeiculo("zz24zz"));
            assertEquals(coord,ctx.getCoordenadas().findByLatLong(6f,6f));
            //TODO(CHECK ZONAS VERDES findByParameter does not work)
            //assertEquals(zv,ctx.getZonasVerdes().findByParameters(6f,6f,"zz24zz",3));
            ctx.rollback();


        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    @Test
    public void CreateVehicleWithProcedureWithoutNewZonaVerdeWithoutProcedure() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            ctx.createVehicleWithoutProcedure("zz24zz",111111113,3,999999999, null, null, null);

            Condutor cond = ctx.readCondutor(111111113);
            assertNotNull(cond);
            EquipamentoEletronico eq = ctx.readEquipamentoEletronico(3);
            Cliente c = ctx.readCliente(999999999);
            Veiculo v = new Veiculo("zz24zz",cond,eq,c);

            assertEquals(v, ctx.readVeiculo("zz24zz"));
            ctx.rollback();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}