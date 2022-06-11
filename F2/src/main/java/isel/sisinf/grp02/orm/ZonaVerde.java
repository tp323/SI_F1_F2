package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IZonaVerde;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Zona_Verde.findByKey",
        query = "SELECT c FROM ZonaVerde c WHERE c.id =:key")
@NamedQuery(name="Zona_Verde.findAll",
        query="SELECT v FROM ZonaVerde v")

@Table(name = "Zona_Verde")
public class ZonaVerde implements IZonaVerde {

    public ZonaVerde(){}

    public ZonaVerde(Coordenadas coordenadas, Veiculo veiculo, int raio){
        this.coordenadas = coordenadas;
        this.veiculo = veiculo;
        this.raio = raio;
    }

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordenadas", referencedColumnName = "id")
    private Coordenadas coordenadas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo", referencedColumnName = "matricula")
    private Veiculo veiculo;

    @Column(name = "raio", nullable = false)
    private int raio;


    public Long getID() {return id;}
    public Coordenadas getCoodenadas() {return coordenadas;}
    public Veiculo getVeiculo() {return veiculo;}
    public int getRaio() {return raio;}


    public void setID(Long id) { this.id = id; }
    public void setCoordenadas(Coordenadas coordenadas) { this.coordenadas = coordenadas; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    public void getRaio(int raio) { this.raio = raio; }

    @Override
    public String toString() {
        return "ZonaVerde{" +
                "id=" + id +
                ", coordenadas=" + coordenadas.getId() +
                ", veiculo=" + veiculo.getMatricula() +
                ", raio=" + raio +
                '}';
    }

    public String[] toArray() {
        return new String[]{
                Long.toString(id),
                Long.toString(coordenadas.getId()),
                veiculo.getMatricula(),
                Integer.toString(raio)

        };
    }
}
