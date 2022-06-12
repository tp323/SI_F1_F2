package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IClienteParticular;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name="Cliente_Particular.findByKey", query="SELECT c FROM ClienteParticular c WHERE c.cc =:key")
@NamedQuery(name="Cliente_Particular.findAll",
        query="SELECT cp FROM ClienteParticular cp")

@Table(name = "cliente_particular")
public class ClienteParticular implements IClienteParticular {

    public ClienteParticular(){}

    public ClienteParticular(int cc, Cliente cliente){
        this.cc = cc;
        this.cliente = cliente;
    }

    @Id
    @Column(name = "cc", nullable = false)
    private int cc;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente", nullable = false, referencedColumnName = "nif")
    private Cliente cliente;

    @OneToMany(mappedBy = "refCliente", cascade = CascadeType.PERSIST)
    private Set<Cliente> referred;


    public int getCC() { return cc; }
    public Cliente getCliente() {return cliente; }
    public Set<Cliente> getReferred() {return referred; }


    public void setCC(int cc) { this.cc = cc;}
    public void setCliente(Cliente client) { this.cliente = client; }
    public void setReferred(Set<Cliente> referred) { this.referred = referred; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClienteParticular that = (ClienteParticular) obj;
        return cc == that.cc &&
                Objects.equals(cliente, that.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cc, cliente, referred);
    }

    @Override
    public String toString() {
        return "ClienteParticular{" +
                "cc=" + cc +
                ", cliente=" + cliente.getNif() +
                '}';
    }

    public String[] toArray() {
        return new String[]{
                Integer.toString(cc),
                Integer.toString(cliente.getNif())
        };
    }
}