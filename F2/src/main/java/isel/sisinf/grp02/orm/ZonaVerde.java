package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IZonaVerde;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@NamedQuery(name = "Zona_Verde.findByKey",
        query = "SELECT zv FROM ZonaVerde zv WHERE zv.id =:key")
@NamedQuery(name="Zona_Verde.findAll",
        query="SELECT zv FROM ZonaVerde zv")
@NamedQuery(name="Zona_Verde.findByParameters",
        query="SELECT zv FROM ZonaVerde zv WHERE zv.coordenadas =:coordenadas AND zv.veiculo =:veiculo AND zv.raio=:r")

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ZonaVerde zonaVerde = (ZonaVerde) obj;
        return raio == zonaVerde.raio &&
                //Objects.equals(id, zonaVerde.id) &&
                Objects.equals(coordenadas, zonaVerde.coordenadas) &&
                Objects.equals(veiculo, zonaVerde.veiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordenadas.getId(), veiculo.getMatricula(), raio);
    }

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
