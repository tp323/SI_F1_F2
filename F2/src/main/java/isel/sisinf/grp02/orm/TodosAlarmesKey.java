package isel.sisinf.grp02.orm;

import java.io.Serializable;
import java.sql.Timestamp;

public class TodosAlarmesKey implements Serializable {
    private String matricula;
    private String nome;
    private float latitude;
    private float longitude;
    private Timestamp marcaTemporal;

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
}
