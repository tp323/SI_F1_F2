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
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class)
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

    @Override
    public String toString() {
        return "Bip{" +
                "id=" + id +
                ", marca_temporal=" + marca_temporal +
                ", alarme=" + alarme +
                ", equipamento=" + equipamento +
                ", coordenadas=" + coordenadas +
                '}';
    }

    public String[] toArray() {
        return new String[]{
                Long.toString(id),
                String.valueOf(marca_temporal),
                Boolean.toString(alarme),
                Long.toString(equipamento.getId()),
                Long.toString(coordenadas.getId())
        };
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "marca_temporal", nullable = false)
    private Timestamp marca_temporal;

    @Column(name = "alarme", nullable = false)
    private boolean alarme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento", referencedColumnName = "id")
    private Equipamento_Eletronico equipamento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordenadas", referencedColumnName = "id")
    private Coordenadas coordenadas;


    public Long getID() { return id; }
    public Equipamento_Eletronico getEquipamento() { return equipamento; }
    public Timestamp getMarcaTemporal() { return marca_temporal; }
    public Coordenadas getCoordenadas() { return coordenadas; }
    public Boolean getAlarme() { return alarme; }


    public void setID(Long id) { this.id = id; }
    public void setEquipamento(Equipamento_Eletronico equipamento) { this.equipamento = equipamento; }
    public void setMarcaTemporal(Timestamp marcaTemporal) { this.marca_temporal = marcaTemporal; }
    public void setCoordenadas(Coordenadas coordenadas) { this.coordenadas = coordenadas; }
    public void setAlarme(Boolean alarme) { this.alarme = alarme; }
}
