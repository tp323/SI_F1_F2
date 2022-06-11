package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IZonaVerde;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Zona_Verde.findByKey",
        query = "SELECT c FROM Zona_Verde c WHERE c.id =:key")
@NamedQuery(name="Zona_Verde.findAll",
        query="SELECT v FROM Zona_Verde v")

@Table(name = "Zona_Verde")
public class Zona_Verde implements IZonaVerde {

    public Zona_Verde(){}

    public Zona_Verde(Coordenadas coordenadas, Veiculo veiculo, int raio){
        this.coordenadas = coordenadas;
        this.veiculo = veiculo;
        this.raio = raio;
    }

    @Override
    public String toString() {
        return "Zona_Verde{" +
                "id=" + id +
                ", raio=" + raio +
                ", coordenadas=" + coordenadas +
                ", veiculo=" + veiculo +
                '}';
    }

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "raio", nullable = false)
    private int raio;

    // TODO: Zonas Verdes should be able to have the same Coordenadas
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordenadas", referencedColumnName = "id")
    private Coordenadas coordenadas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo", referencedColumnName = "matricula")
    private Veiculo veiculo;


    public Long getID() {return id;}
    public Coordenadas getCoodenadas() {return coordenadas;}
    public Veiculo getVeiculo() {return veiculo;}
    public int getRaio() {return raio;}


    public void setID(Long id) { this.id = id; }
    public void setCoordenadas(Coordenadas coordenadas) { this.coordenadas = coordenadas; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    public void getRaio(int raio) { this.raio = raio; }
}
