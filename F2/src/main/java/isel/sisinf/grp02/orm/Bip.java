package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IBip;
import jakarta.persistence.*;

import java.sql.Timestamp;


@NamedStoredProcedureQuery(
        name = Bip.alarm_number,
        procedureName = "alarm_number",
        resultClasses = Integer.class,
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class)
        }
)

@Entity
@NamedQuery(name = "Bip.findByKey",
        query = "SELECT b FROM Bip b WHERE b.id =:key")
@NamedQuery(name="Bip.findAll",
        query="SELECT b FROM Bip b")

@Table(name = "bip_equipamento_eletronico")
public class Bip implements IBip {

    public static final String alarm_number = "alarm_number";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento", referencedColumnName = "id")
    private EquipamentoEletronico equipamento;

    @Column(name = "marca_temporal", nullable = false)
    private Timestamp marca_temporal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordenadas", referencedColumnName = "id")
    private Coordenadas coordenadas;

    @Column(name = "alarme", nullable = false)
    private boolean alarme;


    public Long getID() { return id; }
    public EquipamentoEletronico getEquipamento() { return equipamento; }
    public Timestamp getMarcaTemporal() { return marca_temporal; }
    public Coordenadas getCoordenadas() { return coordenadas; }
    public Boolean getAlarme() { return alarme; }


    public void setID(Long id) { this.id = id; }
    public void setEquipamento(EquipamentoEletronico equipamento) { this.equipamento = equipamento; }
    public void setMarcaTemporal(Timestamp marcaTemporal) { this.marca_temporal = marcaTemporal; }
    public void setCoordenadas(Coordenadas coordenadas) { this.coordenadas = coordenadas; }
    public void setAlarme(Boolean alarme) { this.alarme = alarme; }

    @Override
    public String toString() {
        return "Bip{" +
                "id=" + id +
                ", equipamento=" + equipamento +
                ", marca_temporal='" + marca_temporal + '\'' +
                ", coordenadas=" + coordenadas +
                ", alarme=" + alarme +
                '}';
    }

    public String[] toArray() {
        return new String[]{
                Long.toString(id),
                Long.toString(equipamento.getId()),
                String.valueOf(marca_temporal),
                Long.toString(coordenadas.getId()),
                Boolean.toString(alarme)
        };
    }
}
