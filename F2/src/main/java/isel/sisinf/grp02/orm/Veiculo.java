package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IVeiculo;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@NamedStoredProcedureQuery(
        name = "createVehiclenoZona_Verde",
        procedureName = Veiculo.createVehicle,
        resultClasses = Integer.class,
        parameters = {
                //newRegistration varchar(6), newDriver int, newEquip int, newClient int, raio int = null, lat numeric = null, long numeric = null
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),    //newRegistration
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),   //newDriver
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),   //newEquip
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),   //newClient
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class)


        }
)


@NamedStoredProcedureQuery(
        name = Veiculo.createVehicle+"withZonaVerde",
        procedureName = Veiculo.createVehicle,
        resultClasses = Integer.class,
        parameters = {
                //newRegistration varchar(6), newDriver int, newEquip int, newClient int, raio int = null, lat numeric = null, long numeric = null
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),    //newRegistration
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),   //newDriver
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class),   //newEquip
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),   //newClient
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),   //raio
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Float.class),   //lat
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Float.class)   //long

        }
)

@NamedQuery(name="Veiculo.findByKey", query="SELECT v FROM Veiculo v WHERE v.matricula =:key")
@NamedQuery(name="Veiculo.findAll", query="SELECT v FROM Veiculo v")

@Table(name = "veiculo")
public class Veiculo implements IVeiculo {

    public Veiculo(){}

    public Veiculo(String matricula, Condutor condutor, EquipamentoEletronico equipamento, Cliente cliente) {
        this.matricula = matricula;
        this.condutor = condutor;
        this.equipamento = equipamento;
        this.cliente = cliente;
    }

    public static final String createVehicle = "createVehicle";

    @Id
    @Column(name = "matricula", nullable = false, length = 6)
    private String matricula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "condutor", referencedColumnName = "cc", nullable = false)
    private Condutor condutor;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipamento", referencedColumnName = "id", nullable = false)
    private EquipamentoEletronico equipamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente", referencedColumnName = "nif", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.PERSIST)
    private Set<ZonaVerde> zonasVerdes;

    @Column(name = "alarms", nullable = false)
    private Integer alarms = 0;

    public String getMatricula() { return matricula; }
    public Condutor getCondutor() { return condutor; }
    public EquipamentoEletronico getEquipamento() { return equipamento; }
    public Cliente getCliente() { return cliente; }
    public Integer getAlarms() { return alarms; }

    public Set<ZonaVerde> getZonasVerdes() { return zonasVerdes; }
    public void setMatricula(String matricula) {this.matricula = matricula;}
    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }
    public void setEquipamento(EquipamentoEletronico equipamento) {
        this.equipamento = equipamento;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public void setAlarms(Integer alarms) {
        this.alarms = alarms;
    }
    public void setZonasVerdes(Set<ZonaVerde> zonasVerdes) { this.zonasVerdes = zonasVerdes; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Veiculo veiculo = (Veiculo) obj;
        return Objects.equals(matricula, veiculo.matricula) &&
                Objects.equals(condutor, veiculo.condutor) &&
                Objects.equals(equipamento, veiculo.equipamento) &&
                Objects.equals(cliente, veiculo.cliente) &&
                Objects.equals(alarms, veiculo.alarms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula, condutor, equipamento, cliente, zonasVerdes, alarms);
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "matricula='" + matricula + '\'' +
                ", condutor=" + condutor.getCC() +
                ", equipamento=" + equipamento.getId() +
                ", cliente=" + cliente.getNif() +
                ", alarms=" + alarms +
                '}';
    }

    public String[] toArray() {
        return new String[]{
                matricula,
                Integer.toString(condutor.getCC()),
                Long.toString(equipamento.getId()),
                Integer.toString(cliente.getNif()),
                Integer.toString(alarms)
        };
    }
}