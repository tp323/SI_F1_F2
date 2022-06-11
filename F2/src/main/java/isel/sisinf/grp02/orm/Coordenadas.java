package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.ICoordenadas;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Coordenadas.findByKey",
        query = "SELECT c FROM Coordenadas c WHERE c.id =:key")
@NamedQuery(name="Coordenadas.findAll",
        query="SELECT v FROM Coordenadas v")

@Table(name = "coordenadas")
public class Coordenadas implements ICoordenadas {

    public Coordenadas(){}

    public Coordenadas(float latitude,float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Coordenadas{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "latitude", nullable = false, precision = 1)
    private Float latitude;

    @Column(name = "longitude", nullable = false, precision = 1)
    private Float longitude;

    @OneToMany(mappedBy = "coordenadas", cascade = CascadeType.PERSIST)
    private Set<Zona_Verde> zonas = new LinkedHashSet<>();

    @OneToOne(mappedBy = "coordenadas", cascade = CascadeType.PERSIST)
    private Bip bip;

    public Long getId() { return id; }
    public Float getLatitude() { return latitude; }
    public Float getLongitude() { return longitude; }
    public Bip getBip() { return bip; }
    public Set<Zona_Verde> getZonaVerde() { return zonas; }


    public void setId(Long id) { this.id = id; }
    public void setLatitude(Float latitude) { this.latitude = latitude; }
    public void setLongitude(Float longitude) { this.longitude = longitude; }
    public void setBip(Bip bip) { this.bip = bip; }
    public void setZonaVerde(Set<Zona_Verde> zonas) { this.zonas = zonas; }

    @Override
    public String toString() {
        return "Coordenadas{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public String[] toArray() {
        return new String[]{
                Long.toString(id),
                Float.toString(latitude),
                Float.toString(longitude)
        };
    }
}
