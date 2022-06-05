package isel.sisinf.grp02.JPAObjects;

import isel.sisinf.grp02.JPAObjects.mappers.IBip;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@NamedQuery(name = "Bip.findByKey",
        query = "SELECT c FROM Bip c WHERE c.id =: key")

@Table(name = "bip_equipamento_eletronico")
public class Bip implements IBip {

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

    @Override
    public Long getID() { return id; }
    @Override
    public Equipamento_Eletronico getEquipamento() { return equipamento; }
    @Override
    public Timestamp getMarcaTemporal() { return marca_temporal; }
    @Override
    public Coordenadas getCoordenadas() { return coordenadas; }
    @Override
    public Boolean getAlarme() { return alarme; }

    @Override
    public void setID(Long id) { this.id = id; }
    @Override
    public void setEquipamento(Equipamento_Eletronico equipamento) { this.equipamento = equipamento; }
    @Override
    public void setMarcaTemporal(Timestamp marcaTemporal) { this.marca_temporal = marcaTemporal; }
    @Override
    public void setCoordenadas(Coordenadas coordenadas) { this.coordenadas = coordenadas; }
    @Override
    public void setAlarme(Boolean alarme) { this.alarme = alarme; }
}
