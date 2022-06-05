package isel.sisinf.grp02;

import isel.sisinf.grp02.JPAObjects.Cliente;
import isel.sisinf.grp02.JPAObjects.Equipamento_Eletronico;
import isel.sisinf.grp02.JPAObjects.Veiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true );
    }

    @Test
    public void testConnection() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sijpa");
        EntityManager em = emf.createEntityManager();

        try {
            //CREATE
            System.out.println("--# CREATE equipamento_eletronico");
            em.getTransaction().begin();

            Equipamento_Eletronico cn = new Equipamento_Eletronico();
            cn.setEstado("Inactivo");
            em.persist(cn);
            em.getTransaction().commit();

            System.out.println("ID Generated:" + cn.getId());

            //READ
            System.out.println("\n--# READ equipamento_eletronico");

            String sql = "SELECT c FROM Equipamento_Eletronico c";
            Query query = em.createQuery(sql);
            List<Equipamento_Eletronico> equipamento_eletronico = query.getResultList();

            for (Equipamento_Eletronico c : equipamento_eletronico) {
                System.out.printf("%d ", c.getId());
                System.out.printf("%s \n", c.getEstado());

            }

            //DELETE
            em.getTransaction().begin();
            em.remove(em.find(Equipamento_Eletronico.class,cn.getId()));
            em.flush(); //Send changes to database
            em.getTransaction().commit();
            em.clear();
            System.out.println("\n--# Removed!!");


            System.out.println("\n--# READ cliente");

            String sqlt = "SELECT c FROM Cliente c";
            Query queryt = em.createQuery(sqlt);
            List<Cliente> Cliente = queryt.getResultList();

            for (Cliente c : Cliente) {
                System.out.printf("%d ", c.getNif());
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
