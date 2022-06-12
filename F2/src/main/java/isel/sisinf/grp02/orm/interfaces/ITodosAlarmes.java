package isel.sisinf.grp02.orm.interfaces;

import java.sql.Timestamp;

public interface ITodosAlarmes {

    String getMatricula();
    String getNome();
    float getLatitude();
    float getLongitude();
    Timestamp getMarcaTemporal();

    void setMatricula(String matricula);
    void setNome(String nome);
    void setLatitude(float latitude);
    void setLongitude(float longitude);
    void setMarcaTemporal(Timestamp marcaTemporal);
}
