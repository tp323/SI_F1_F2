package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.Coordenadas;
import isel.sisinf.grp02.orm.Equipamento_Eletronico;

import java.sql.Timestamp;

public interface IBip {
    Long getID();
    Equipamento_Eletronico getEquipamento();
    Timestamp getMarcaTemporal();
    Coordenadas getCoordenadas();
    Boolean getAlarme();

    void setID(Long id);
    void setEquipamento(Equipamento_Eletronico equipamento);
    void setMarcaTemporal(Timestamp marcaTemporal);
    void setCoordenadas(Coordenadas coordenadas);
    void setAlarme(Boolean alarme);
}
