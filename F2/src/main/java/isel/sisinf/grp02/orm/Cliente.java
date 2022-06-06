package isel.sisinf.grp02.orm;


import isel.sisinf.grp02.orm.interfaces.ICliente;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name="Cliente.findByKey",
        query="SELECT c FROM Cliente c WHERE c.nif =:key")

@Table(name = "cliente")
public class Cliente implements ICliente {
    public Cliente() {}

    public Cliente(int nif, String nome, String morada, int telefone, Boolean ativo) {
        this.nif = nif;
        this.nome = nome;
        this.morada = morada;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    public Cliente(int nif, String nome, String morada, int telefone) {
        this.nif = nif;
        this.nome = nome;
        this.morada = morada;
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente[" +
                "nif=" + nif +
                ", nome='" + nome + '\'' +
                ", morada='" + morada + '\'' +
                ", telefone='" + telefone + '\'' +
                ", ativo=" + ativo +
                ", refCliente=" + refCliente +
                ']';
    }



    @Id
    @Column(name = "nif", nullable = false)
    private int nif;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "morada", nullable = false)
    private String morada;

    @Column(name = "telefone", nullable = false, length = 10)
    private int telefone;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_cliente")
    private Cliente_Particular refCliente;

    @OneToOne(mappedBy = "cliente")
    private Cliente_Particular cliente_particular;

    @OneToOne(mappedBy = "cliente")
    private Cliente_Institucional cliente_institucional;

    @OneToMany(mappedBy = "cliente")
    private Set<Veiculo> veiculos = new LinkedHashSet<>();

    public int getNif() {return nif;}
    public String getNome() {return nome;}
    public String getMorada() {return morada;}
    public int getTelefone() {return telefone;}
    public Boolean getAtivo() {return ativo;}
    public Cliente_Particular getRefCliente() {return refCliente;}
    public Cliente_Particular getClienteParticular() { return cliente_particular; }
    public Cliente_Institucional getClienteInstitucional() { return cliente_institucional; }
    public Set<Veiculo> getVeiculos() {return veiculos;}


    public void setNif(Integer nif) {this.nif = nif;}
    public void setNome(String nome) {this.nome = nome;}
    public void setMorada(String morada) {this.morada = morada;}
    public void setTelefone(int telefone) {this.telefone = telefone;}
    public void setRefCliente(Cliente_Particular refCliente) {this.refCliente = refCliente;}
    public void setClienteParticular(Cliente_Particular cliente_particular) { this.cliente_particular = cliente_particular; }
    public void setClienteInstitucional(Cliente_Institucional cliente_institucional) { this.cliente_institucional = cliente_institucional; }
    public void setAtivo(Boolean ativo) {this.ativo = ativo;}
    public void setVeiculos(Set<Veiculo> veiculos) {this.veiculos = veiculos;}
}