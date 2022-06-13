package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.ICoordenadas;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name = "Coordenadas.findByKey",
        query = "SELECT c FROM Coordenadas c WHERE c.id =:key")
@NamedQuery(name="Coordenadas.findAll",
        query="SELECT c FROM Coordenadas c")
@NamedQuery(name="Coordenadas.findByLatLong",
        query="SELECT c FROM Coordenadas c WHERE c.latitude =:lat AND c.longitude =:log")
@NamedQuery(name="Coordenadas.findLast",
        query="SELECT c FROM Coordenadas c ORDER BY c.id")

@Table(name = "coordenadas")
public class Coordenadas implements ICoordenadas {

    public Coordenadas(){}

    public Coordenadas(float latitude,float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false, precision = 1)
    private float latitude;

    @Column(name = "longitude", nullable = false, precision = 1)
    private float longitude;

    @OneToMany(mappedBy = "coordenadas", cascade = CascadeType.PERSIST)
    private Set<ZonaVerde> zonas = new LinkedHashSet<>();

    @OneToOne(mappedBy = "coordenadas", cascade = CascadeType.PERSIST)
    private Bip bip;

    public Long getId() { return id; }
    public float getLatitude() { return latitude; }
    public float getLongitude() { return longitude; }
    public Bip getBip() { return bip; }
    public Set<ZonaVerde> getZonaVerde() { return zonas; }


    public void setId(Long id) { this.id = id; }
    public void setLatitude(float latitude) { this.latitude = latitude; }
    public void setLongitude(float longitude) { this.longitude = longitude; }
    public void setBip(Bip bip) { this.bip = bip; }
    public void setZonaVerde(Set<ZonaVerde> zonas) { this.zonas = zonas; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordenadas that = (Coordenadas) obj;
        return Float.compare(that.latitude, latitude) == 0 &&
                Float.compare(that.longitude, longitude) == 0 &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, zonas.hashCode(), bip.getID());
    }

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
