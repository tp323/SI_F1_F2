package isel.sisinf.grp02.JPAObjects;


import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "nif", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 25)
    private String nome;

    @Column(name = "morada", nullable = false, length = 150)
    private String morada;

    @Column(name = "telefone", nullable = false, length = 10)
    private String telefone;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "ref_cliente")
    //private ClienteParticular refCliente;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = false;

    @OneToMany(mappedBy = "cliente")
    private Set<Veiculo> veiculos = new LinkedHashSet<>();

    public Set<Veiculo> getVeiculos() {
        return veiculos;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    //public ClienteParticular getRefCliente() {
    //    return refCliente;
    //}
    public String getTelefone() {
        return telefone;
    }
    public String getMorada() {return morada;}
    public String getNome() {return nome;}
    public Integer getId() {return id;}

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    //public void setRefCliente(ClienteParticular refCliente) {this.refCliente = refCliente;}
    public void setTelefone(String telefone) {this.telefone = telefone;}
    public void setMorada(String morada) {this.morada = morada;}
    public void setNome(String nome) {this.nome = nome;}
    public void setId(Integer id) {this.id = id;}
}