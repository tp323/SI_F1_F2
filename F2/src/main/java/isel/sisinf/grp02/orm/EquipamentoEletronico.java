package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IEquipamento;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Object that represents an equipamento eletronico, with all its attributes
 */
@Entity
@NamedQuery(name = "Equipamento_Eletronico.findByKey",
        query = "SELECT c FROM EquipamentoEletronico c WHERE c.id =:key")
@NamedQuery(name="Equipamento_Eletronico.findAll",
        query="SELECT c FROM EquipamentoEletronico c")

@Table(name = "equipamento_eletronico")
public class EquipamentoEletronico implements IEquipamento {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    public enum EstadosValidos {
        Activo("Activo"),
        PausaDeAlarmes("PausaDeAlarmes"),
        Inactivo("Inactivo");

        private String estado;

        EstadosValidos(String estado) {
            this.estado = estado;
        }

        public String getEstado() {
            return estado;
        }

        public static boolean isEstadoValido(String estadoToEvaluate) {
            for(EstadosValidos estadosValidos : values()) {
                if(estadosValidos.getEstado().equals(estadoToEvaluate)) return true;
            }
            return false;
        }
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EquipamentoEletronico that = (EquipamentoEletronico) obj;
        return id == that.id &&
                Objects.equals(estado, that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estado, veiculo.getMatricula(), bips.hashCode());
    }

    @Override
    public String toString() {
        return "EquipamentoEletronico{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                '}';
    }

    public String[] toArray() {
        return new String[]{
                Long.toString(id),
                estado
        };
    }
}
