package isel.sisinf.grp02.JPAObjects;

import isel.sisinf.grp02.JPAObjects.mappers.IZonaVerde;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "ZonaVerde.findByKey",
        query = "SELECT c FROM ZonaVerde c WHERE c.id =: key")

@Table(name = "coordenadas")
public class ZonaVerde implements IZonaVerde {

    @Override
    public String toString() {
        return "ZonaVerde{" +
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordenadas", referencedColumnName = "id")
    private Coordenadas coordenadas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo", referencedColumnName = "matricula")
    private Veiculo veiculo;

    @Override
    public Long getID() {return id;}
    @Override
    public Coordenadas getCoodenadas() {return coordenadas;}
    @Override
    public Veiculo getVeiculo() {return veiculo;}
    @Override
    public int getRaio() {return raio;}

    @Override
    public void setID(Long id) { this.id = id; }
    @Override
    public void setCoordenadas(Coordenadas coordenadas) { this.coordenadas = coordenadas; }
    @Override
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    @Override
    public void getRaio(int raio) { this.raio = raio; }
}
