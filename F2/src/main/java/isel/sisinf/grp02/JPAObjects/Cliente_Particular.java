package isel.sisinf.grp02.JPAObjects;

import isel.sisinf.grp02.JPAObjects.mappers.ICliente_Particular;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Cliente_Particular.findByKey",
        query = "SELECT c FROM Cliente_particular c WHERE c.cc =:key")


@Table(name = "cliente_particular")
public class Cliente_Particular implements ICliente_Particular {
    @Id
    @Column(name = "cc", nullable = false)
    private Long cc;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "nif")
    private Cliente client;

    @Override
    public long getCC() {
        return this.cc;
    }

    @Override
    public Cliente getClient() {
        return this.client;
    }

    @Override
    public void setCC(long cc) {
        this.cc = cc;
    }

    @Override
    public void setClient(Cliente client) {
        this.client = client;
    }
}