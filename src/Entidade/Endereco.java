package Entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    @Column(name = "cep", length = 12)
    private String cep;

    @Column(name = "bairro", length = 120)
    private String bairro;

    @Column(name = "nome_rua", length = 120)
    private String nomeRua;

    @Column(name = "complemento", length = 120)
    private String complemento;

    @Column(name = "num_casa")
    private Integer numCasa;

    @Override
    public String toString() {
        return "Endereco{" +
                "cep='" + cep + '\'' +
                ", bairro='" + bairro + '\'' +
                ", nomeRua='" + nomeRua + '\'' +
                ", complemento='" + complemento + '\'' +
                ", numCasa=" + numCasa +
                '}';
    }

    public Endereco(String cep, String bairro, String nomeRua, String complemento, Integer numCasa) {
        this.cep = cep;
        this.bairro = bairro;
        this.nomeRua = nomeRua;
        this.complemento = complemento;
        this.numCasa = numCasa;
    }

    public Endereco() {
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNomeRua() {
        return nomeRua;
    }

    public void setNomeRua(String nomeRua) {
        this.nomeRua = nomeRua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Integer getNumCasa() {
        return numCasa;
    }

    public void setNumCasa(Integer numCasa) {
        this.numCasa = numCasa;
    }
}
