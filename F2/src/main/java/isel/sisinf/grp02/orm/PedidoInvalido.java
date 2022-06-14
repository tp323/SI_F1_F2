package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IRequests;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@NamedQuery(name="Pedido_Invalido.findAll",
        query="SELECT p FROM PedidoInvalido p")
@Table(name = "invalid_requests")
public class PedidoInvalido implements IRequests {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "equipamento")
    private Long equipamento;

    @Column(name = "marca_temporal")
    private Timestamp marca_temporal;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Version
    private Integer version;

    @Override
    public Long getID() {
        return id;
    }
    @Override
    public Long getEquipamento() {return equipamento;}
    @Override
    public Timestamp getMarcaTemporal() {return marca_temporal;}
    @Override
    public Float getLat() {return latitude;}
    @Override
    public Float getLong() {return longitude;}

    @Override
    public void setID(Long id) {
        this.id = id;
    }
    @Override
    public void setEquipamento(Long equipamento) {
        this.equipamento = equipamento;
    }
    @Override
    public void setMarcaTemporal(Timestamp timestamp) {
        this.marca_temporal = timestamp;
    }
    @Override
    public void setLat(Float latitude) {
        this.latitude = latitude;
    }
    @Override
    public void setLong(Float longitude) {
        this.longitude = longitude;
    }
}
