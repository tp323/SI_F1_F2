package isel.sisinf.grp02.orm.interfaces;

import isel.sisinf.grp02.orm.EquipamentoEletronico;

import java.sql.Timestamp;

public interface IRequests {
    Long getID();
    Long getEquipamento();
    Timestamp getMarcaTemporal();
    Float getLat();
    Float getLong();

    void setID(Long id);
    void setEquipamento(Long equipamento);
    void setMarcaTemporal(Timestamp timestamp);
    void setLat(Float latitude);
    void setLong(Float longitude);
}
