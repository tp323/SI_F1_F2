package isel.sisinf.grp02.JPAObjects;

import isel.sisinf.grp02.JPAObjects.mappers.ICoordenadas;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name="Condutor.findByKey", query="SELECT v FROM Condutor v WHERE v.cc =:key")
@Table(name = "condutor")
public class Condutor implements ICoordenadas.ICondutor {
    @Id
    @Column(name = "cc", nullable = false)
    private Integer cc;

    @Column(name = "nome", nullable = false, length = 20)
    private String nome;

    @Column(name = "contacto", length = 10)
    private String contacto;

    @OneToMany(mappedBy = "condutor")
    private Set<Veiculo> veiculos = new LinkedHashSet<>();


    public Integer getCC() {return cc;}
    public String getNome() {return nome;}
    public String getContacto() {return contacto;}
    public Set<Veiculo> getVeiculos() {return veiculos;}

    public void setCC(Integer cc) {this.cc = cc;}
    public void setNome(String nome) {this.nome = nome;}
    public void setContacto(String contacto) {this.contacto = contacto;}
    public void setVeiculos(Set<Veiculo> veiculos) {this.veiculos = veiculos;}

}
