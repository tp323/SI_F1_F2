package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.ICoordenadas;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Coordenadas.findByKey",
        query = "SELECT c FROM Coordenadas c WHERE c.id =:key")

@Table(name = "coordenadas")
public class Coordenadas implements ICoordenadas {

    @Override
    public String toString() {
        return "Coordenadas{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", raio=" + raio +
                ", zona=" + zona +
                ", bip=" + bip +
                '}';
    }

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @Column(name = "raio", nullable = false)
    private int raio;

    @OneToOne(mappedBy = "coordenadas")
    private Zona_Verde zona;

    @OneToOne(mappedBy = "coordenadas")
    private Bip bip;

    public Long getId() { return id; }
    public Float getLatitude() { return latitude; }
    public Float getLongitude() { return longitude; }
    public Bip getBip() { return bip; }
    public Zona_Verde getZonaVerde() { return zona; }


    public void setId(Long id) { this.id = id; }
    public void setLatitude(Float latitude) { this.latitude = latitude; }
    public void setLongitude(Float longitude) { this.longitude = longitude; }
    public void setBip(Bip bip) { this.bip = bip; }
    public void setZonaVerde(Zona_Verde zona) { this.zona = zona; }
}
