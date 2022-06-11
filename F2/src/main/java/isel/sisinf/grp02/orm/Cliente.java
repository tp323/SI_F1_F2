package isel.sisinf.grp02.orm;


import isel.sisinf.grp02.orm.interfaces.ICliente;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name="Cliente.findByKey",
        query="SELECT c FROM Cliente c WHERE c.nif =:key")

@NamedQuery(name="Cliente.findAll",
        query="SELECT c FROM Cliente c")

@Table(name = "cliente")
public class Cliente implements ICliente {
    public Cliente() {}

    public Cliente(int nif, String nome, String morada, String telefone, boolean ativo) {
        this.nif = nif;
        this.nome = nome;
        this.morada = morada;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    public Cliente(int nif, String nome, String morada, String telefone) {
        this.nif = nif;
        this.nome = nome;
        this.morada = morada;
        this.telefone = telefone;
    }

    @Id
    @Column(name = "nif", nullable = false)
    private int nif;

    @Column(name = "nome", nullable = false, length = 25)
    private String nome;

    @Column(name = "morada", nullable = false, length = 150)
    private String morada;

    @Column(name = "telefone", nullable = false, length = 13)
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_cliente")
    private ClienteParticular refCliente = null;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = false;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private ClienteParticular cliente_particular;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private ClienteInstitucional cliente_institucional;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private Set<Veiculo> veiculos = new LinkedHashSet<>();

    public int getNif() {return nif;}
    public String getNome() {return nome;}
    public String getMorada() {return morada;}
    public String getTelefone() {return telefone;}
    public boolean getAtivo() {return ativo;}
    public ClienteParticular getRefCliente() {return refCliente;}
    public ClienteParticular getClienteParticular() { return cliente_particular; }
    public ClienteInstitucional getClienteInstitucional() { return cliente_institucional; }
    public Set<Veiculo> getVeiculos() {return veiculos;}


    public void setNif(Integer nif) {this.nif = nif;}
    public void setNome(String nome) {this.nome = nome;}
    public void setMorada(String morada) {this.morada = morada;}
    public void setTelefone(String telefone) {this.telefone = telefone;}
    public void setRefCliente(ClienteParticular refCliente) {this.refCliente = refCliente;}
    public void setClienteParticular(ClienteParticular cliente_particular) { this.cliente_particular = cliente_particular; }
    public void setClienteInstitucional(ClienteInstitucional cliente_institucional) { this.cliente_institucional = cliente_institucional; }
    public void setAtivo(boolean ativo) {this.ativo = ativo;}
    public void setVeiculos(Set<Veiculo> veiculos) {this.veiculos = veiculos;}

    @Override
    public String toString() {
        return "Cliente[" +
                "nif=" + nif +
                ", nome='" + nome + '\'' +
                ", morada='" + morada + '\'' +
                ", telefone='" + telefone + '\'' +
                ", refCliente=" + refCliente +
                ", ativo=" + ativo +
                ']';
    }

    public String[] toArray() {
        return new String[]{
                Integer.toString(nif),
                this.nome,
                this.morada,
                this.telefone,
                refCliente == null ? "null" : Integer.toString(refCliente.getCC()),
                Boolean.toString(this.ativo)
        };
    }
}