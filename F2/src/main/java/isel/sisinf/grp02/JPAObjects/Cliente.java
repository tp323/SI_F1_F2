package isel.sisinf.grp02.JPAObjects;


import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name="Cliente.findByKey",
        query="SELECT c FROM Cliente c WHERE c.nif =:key")



@Table(name = "cliente")
public class Cliente implements ICliente{
    @Override
    public String toString() {
        return "Cliente [nif=" + nif + ", nome=" + nome + ", morada=" + morada + ", telefone=" + telefone +
                ", refCliente=" + refCliente + ", ativo=" + ativo + "]";
    }
    @Id
    @Column(name = "nif", nullable = false)
    private int nif;

    @Column(name = "nome", nullable = false, length = 25)
    private String nome;

    @Column(name = "morada", nullable = false, length = 150)
    private String morada;

    @Column(name = "telefone", nullable = false, length = 10)
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_cliente")
    private Cliente_Particular refCliente;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = false;

    @OneToMany(mappedBy = "cliente")
    private Set<Veiculo> veiculos = new LinkedHashSet<>();

    public int getNif() {return nif;}
    public String getNome() {return nome;}
    public String getMorada() {return morada;}
    public String getTelefone() {return telefone;}
    public Boolean getAtivo() {return ativo;}
    public Cliente_Particular getRefCliente() {return refCliente;}
    public Set<Veiculo> getVeiculos() {return veiculos;}


    public void setNif(Integer nif) {this.nif = nif;}
    public void setNome(String nome) {this.nome = nome;}
    public void setMorada(String morada) {this.morada = morada;}
    public void setTelefone(String telefone) {this.telefone = telefone;}
    public void setRefCliente(Cliente_Particular refCliente) {this.refCliente = refCliente;}
    public void setAtivo(Boolean ativo) {this.ativo = ativo;}
}