package isel.sisinf.grp02.JPAObjects;

import isel.sisinf.grp02.JPAObjects.mappers.ICliente_Institucional;
import jakarta.persistence.*;

@Entity
@NamedQuery(name="Cliente_Institucional.findByKey", query="SELECT c FROM Cliente_Institucional c WHERE c.dadosCliente =:key")


@Table(name = "cliente")
public class Cliente_Institucional implements ICliente_Institucional {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente", referencedColumnName = "nif")
    private Cliente dadosCliente;

    @Column(name = "nomeContacto")
    private String nomeContacto;


    public String getNomeContacto() { return nomeContacto; }
    public Cliente getDadosCliente() { return dadosCliente; }

    public void setNomeContacto(String nomeContacto) { this.nomeContacto = nomeContacto; }
    public void setDadosCliente(Cliente cliente) { this.dadosCliente = cliente; }
}
