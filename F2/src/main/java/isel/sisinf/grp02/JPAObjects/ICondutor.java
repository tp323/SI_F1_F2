package isel.sisinf.grp02.JPAObjects;

import java.util.Set;

public interface ICondutor {
    Integer getCC();
    String getNome();
    String getContacto();
    Set<Veiculo> getVeiculos();

    void setCC(Integer cc);
    void setNome(String nome);
    void setContacto(String contacto);
    void setVeiculos(Set<Veiculo> veiculos);
}
