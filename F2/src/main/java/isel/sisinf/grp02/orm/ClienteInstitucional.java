package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.IClienteInstitucional;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@NamedQuery(name="Cliente_Institucional.findByKey", query="SELECT c FROM ClienteInstitucional c WHERE c.cliente =:key")
@NamedQuery(name="Cliente_Institucional.findAll",
        query="SELECT ci FROM ClienteInstitucional ci")

@Table(name = "cliente_institucional")
public class ClienteInstitucional implements IClienteInstitucional {

    @Column(name = "nome_contacto", nullable = false, length = 25)
    private String nomeContacto;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente", referencedColumnName = "nif")
    private Cliente cliente;


    public String getNomeContacto() { return nomeContacto; }
    public Cliente getCliente() { return cliente; }

    public void setNomeContacto(String nomeContacto) { this.nomeContacto = nomeContacto; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClienteInstitucional that = (ClienteInstitucional) obj;
        return Objects.equals(nomeContacto, that.nomeContacto) &&
                Objects.equals(cliente, that.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeContacto, cliente.getNif());
    }

    @Override
    public String toString() {
        return "Cliente[" +
                "nomeContacto='" + nomeContacto + '\'' +
                ", cliente='" + cliente.getNif() +
                ']';
    }

    public String[] toArray() {
        return new String[]{
                nomeContacto,
                Integer.toString(cliente.getNif())
        };
    }
}
