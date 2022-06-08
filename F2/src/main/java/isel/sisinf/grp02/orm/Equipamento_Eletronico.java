package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IEquipamento;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Object that represents an equipamento eletronico, with all its attributes
 */
@Entity
@NamedQuery(name = "Equipamento_Eletronico.findByKey",
        query = "SELECT c FROM Equipamento_Eletronico c WHERE c.id =:key")
@NamedQuery(name="Equipamento_Eletronico.findAll",
        query="SELECT c FROM Equipamento_Eletronico c")

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

    @OneToOne(mappedBy = "equipamento", cascade = CascadeType.PERSIST)
    private Veiculo veiculo;

    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.PERSIST)
    private Set<Bip> bips = new LinkedHashSet<>();

    public Long getId() { return id; }
    public String getEstado() { return estado; }
    public Veiculo getVeiculo() { return veiculo; }
    public Set<Bip> getBips() { return bips; }

    public void setID(Long id) { this.id = id; }
    public void setEstado(String status) { this.estado = status; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
    public void setBips(Set<Bip> bips) { this.bips = bips; }
}
