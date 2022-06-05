package isel.sisinf.grp02.JPAObjects;

import isel.sisinf.grp02.JPAObjects.mappers.IEquipamento;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Object that represents an equipamento eletronico, with all its attributes
 */
@Entity
@NamedQuery(name = "Equipamento_Eletronico.findByKey",
        query = "SELECT c FROM Equipamento_Eletronico c WHERE c.id =: key")

@Table(name = "equipamento_eletronico")
public class Equipamento_Eletronico implements IEquipamento {

    @Override
    public String toString() {
        return "Equipamento_Eletronico{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", veiculo=" + veiculo +
                ", bips=" + bips +
                '}';
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "estado", nullable = false)
    private String estado;

    @OneToOne(mappedBy = "equipamento")
    private Veiculo veiculo;

    @OneToMany(mappedBy = "equipamento")
    private Set<Bip> bips = new LinkedHashSet<>();

    @Override
    public Long getID() { return id; }
    @Override
    public String getStatus() { return estado; }
    @Override
    public Veiculo getVeiculo() { return veiculo; }
    @Override
    public Set<Bip> getBips() { return bips; }

    @Override
    public void setID(Long id) { this.id = id; }
    @Override
    public void setStatus(String status) { this.estado = status; }
    @Override
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    @Override
    public void setBips(Set<Bip> bips) { this.bips = bips; }
}
