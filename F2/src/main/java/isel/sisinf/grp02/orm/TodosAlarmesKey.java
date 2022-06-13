package isel.sisinf.grp02.orm;

import java.io.Serializable;
import java.sql.Timestamp;

public class TodosAlarmesKey implements Serializable {
    private String matricula;

    private String nome;

    private float latitude;

    private float longitude;

    private Timestamp marcaTemporal;

    public TodosAlarmesKey(String matricula, String nome, float latitude, float longitude, Timestamp marcaTemporal) {
        this.matricula = matricula;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.marcaTemporal = marcaTemporal;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public Timestamp getMarcaTemporal() {
        return marcaTemporal;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setMarcaTemporal(Timestamp marcaTemporal) {
        this.marcaTemporal = marcaTemporal;
    }
}
