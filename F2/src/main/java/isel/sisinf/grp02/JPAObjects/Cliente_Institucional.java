package isel.sisinf.grp02.JPAObjects;

import isel.sisinf.grp02.JPAObjects.mappers.ICliente_Institucional;
import jakarta.persistence.*;

@Entity
@NamedQuery(name="Cliente_Institucional.findByKey", query="SELECT c FROM Cliente_Institucional c WHERE c.cc =:key")


@Table(name = "cliente")
public class Cliente_Institucional implements ICliente_Institucional {

    @Id
    @Column


    @Override
    public String getNomeContacto() { return null; }
    @Override
    public Cliente getDadosCliente() { return null; }

    @Override
    public void setNomeContacto(String nomeContacto) { }
    @Override
    public void setDadosCliente(Cliente cliente) { }
}
