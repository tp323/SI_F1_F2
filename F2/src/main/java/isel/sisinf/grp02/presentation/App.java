package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.JPAObjects.Cliente;
import isel.sisinf.grp02.JPAObjects.Veiculo;
import isel.sisinf.grp02.JPAObjects.equipamento_eletronico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Set;


public class App {
    public static void main( String[] args ) {
        test();
    }

    private static void test() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
        EntityManager em = emf.createEntityManager();
        try {
            //CREATE
            System.out.println("--# CREATE equipamento_eletronico");
            em.getTransaction().begin();

            equipamento_eletronico cn = new equipamento_eletronico();
            cn.setEstado("Inactivo");
            em.persist(cn);
            em.getTransaction().commit();

            System.out.println("ID Generated:" + cn.getId());

            //READ
            System.out.println("\n--# READ equipamento_eletronico");

            String sql = "SELECT c FROM equipamento_eletronico c";
            Query query = em.createQuery(sql);
            List<equipamento_eletronico> equipamento_eletronico = query.getResultList();

            for (equipamento_eletronico c : equipamento_eletronico) {
                System.out.printf("%d ", c.getId());
                System.out.printf("%s \n", c.getEstado());

            }

            //DELETE
            em.getTransaction().begin();
            em.remove(em.find(equipamento_eletronico.class,cn.getId()));
            em.flush(); //Send changes to database
            em.getTransaction().commit();
            em.clear();
            System.out.println("\n--# Removed!!");


            System.out.println("\n--# READ cliente");

            String sqlt = "SELECT c FROM Cliente c";
            Query queryt = em.createQuery(sqlt);
            List<Cliente> Cliente = queryt.getResultList();

            for (Cliente c : Cliente) {
                System.out.printf("%d ", c.getId());
                System.out.printf("%s ", c.getMorada());
                Set<Veiculo> Veiculo = c.getVeiculos();
                for (Veiculo v: Veiculo) System.out.printf("%s ", v.getId());
                System.out.println();
            }

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        finally {
            em.close();
            emf.close();
        }
    }
}
