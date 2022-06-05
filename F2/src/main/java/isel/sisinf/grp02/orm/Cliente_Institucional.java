package isel.sisinf.grp02.orm;

import isel.sisinf.grp02.orm.interfaces.ICliente_Institucional;
import jakarta.persistence.*;

@Entity
@NamedQuery(name="Cliente_Institucional.findByKey", query="SELECT c FROM Cliente_Institucional c WHERE c.cliente =:key")


@Table(name = "cliente_institucional")
public class Cliente_Institucional implements ICliente_Institucional {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente", referencedColumnName = "nif")
    private Cliente cliente;

    @Column(name = "nome_contacto")
    private String nomeContacto;


    public String getNomeContacto() { return nomeContacto; }
    public Cliente getCliente() { return cliente; }

    public void setNomeContacto(String nomeContacto) { this.nomeContacto = nomeContacto; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
