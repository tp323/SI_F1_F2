package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.ITodosAlarmes;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@NamedQuery(name = "todos_alarmes.findByKey",
        query = "SELECT t FROM TodosAlarmes t WHERE t.matricula =:key")
@NamedQuery(name="todos_alarmes.findAll",
        query="SELECT t FROM TodosAlarmes t")

@Table(name = "todos_alarmes")
public class TodosAlarmes implements ITodosAlarmes {

    public TodosAlarmes(){}

    public TodosAlarmes(String matricula, String nome, float latitude, float longitude, Timestamp marcaTemporal) {
        this.matricula = matricula;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.marcaTemporal = marcaTemporal;
    }

    @Id
    @Column(name = "matricula", nullable = false, length = 6)
    private String matricula;

    @Column(name = "nome", nullable = false, length = 20)
    private String nome;

    @Column(name = "latitude", nullable = false)
    private float latitude;

    @Column(name = "longitude", nullable = false)
    private float longitude;

    @Column(name = "marca_temporal", nullable = false)
    private Timestamp marcaTemporal;


    @Override
    public String getMatricula() {
        return matricula;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }

    @Override
    public Timestamp getMarcaTemporal() {
        return marcaTemporal;
    }

    @Override
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public void setMarcaTemporal(Timestamp marcaTemporal) {
        this.marcaTemporal = marcaTemporal;
    }

    @Override
    public String toString() {
        return "TodosAlarmes{" +
                "matricula='" + matricula + '\'' +
                ", nome='" + nome + '\'' +
                ", latitude=" + latitude +
                ", longitude='" + longitude +
                ", marca_temporal='" + marcaTemporal + '\'' +
                '}';
    }

    public String[] toArray() {
        return new String[]{
                matricula,
                nome,
                Float.toString(latitude),
                Float.toString(longitude),
                marcaTemporal.toString()
        };
    }
}
