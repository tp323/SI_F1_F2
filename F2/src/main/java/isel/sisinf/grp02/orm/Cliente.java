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

    public String[] toArray() {
        return new String[]{
                Integer.toString(nif),
                this.nome,
                this.morada,
                this.telefone,
                Boolean.toString(this.ativo),
                refCliente == null ? "null" : Integer.toString(refCliente.getCC())
        };
    }

    @Id
    @Column(name = "nif", nullable = false) /*** passar para length 8 na DB ou manter 9 com num de seg ***/
    private int nif;

    @Column(name = "nome", nullable = false, length = 25)
    private String nome;

    @Column(name = "morada", nullable = false, length = 150)
    private String morada;

    @Column(name = "telefone", nullable = false, length = 10) /*** passar para length 13 na DB ***/
    private String telefone;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_cliente")
    private Cliente_Particular refCliente = null;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private Cliente_Particular cliente_particular;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private Cliente_Institucional cliente_institucional;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST)
    private Set<Veiculo> veiculos = new LinkedHashSet<>();

    public int getNif() {return nif;}
    public String getNome() {return nome;}
    public String getMorada() {return morada;}
    public String getTelefone() {return telefone;}
    public boolean getAtivo() {return ativo;}
    public Cliente_Particular getRefCliente() {return refCliente;}
    public Cliente_Particular getClienteParticular() { return cliente_particular; }
    public Cliente_Institucional getClienteInstitucional() { return cliente_institucional; }
    public Set<Veiculo> getVeiculos() {return veiculos;}


    public void setNif(Integer nif) {this.nif = nif;}
    public void setNome(String nome) {this.nome = nome;}
    public void setMorada(String morada) {this.morada = morada;}
    public void setTelefone(String telefone) {this.telefone = telefone;}
    public void setRefCliente(Cliente_Particular refCliente) {this.refCliente = refCliente;}
    public void setClienteParticular(Cliente_Particular cliente_particular) { this.cliente_particular = cliente_particular; }
    public void setClienteInstitucional(Cliente_Institucional cliente_institucional) { this.cliente_institucional = cliente_institucional; }
    public void setAtivo(boolean ativo) {this.ativo = ativo;}
    public void setVeiculos(Set<Veiculo> veiculos) {this.veiculos = veiculos;}
}