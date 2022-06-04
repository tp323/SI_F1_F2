package isel.sisinf.grp02.JPAObjects;

import jakarta.persistence.*;

@Entity
@Table(name = "veiculo")
public class Veiculo {
    @Id
    @Column(name = "matricula", nullable = false, length = 6)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    public String getId() {
        return id;
    }
    public Cliente getCliente() {return cliente;}

    public void setId(String id) {
        this.id = id;
    }
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

}