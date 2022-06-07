package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.ICliente_Particular;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@NamedQuery(name="Cliente_Particular.findByKey", query="SELECT c FROM Cliente_Particular c WHERE c.cc =:key")


@Table(name = "cliente_particular")
public class Cliente_Particular implements ICliente_Particular {

    @Override
    public String toString() {
        return "Cliente_Particular{" +
                "cc=" + cc +
                ", cliente=" + cliente +
                '}';
    }

    @Id
    @Column(name = "cc")
    private int cc;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente", referencedColumnName = "nif")
    private Cliente cliente;

    @OneToMany(mappedBy = "refCliente")
    private Set<Cliente> referred;


    public int getCC() { return cc; }
    public Cliente getClient() {return cliente; }
    public Set<Cliente> getReferred() {return referred; }


    public void setCC(int cc) { this.cc = cc;}
    public void setClient(Cliente client) { this.cliente = client; }
    public void setReferred(Set<Cliente> referred) { this.referred = referred; }
}