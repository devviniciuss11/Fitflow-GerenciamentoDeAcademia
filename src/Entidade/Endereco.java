package Entidade;

public class Endereco {
    private String Cep;
    private String Bairro;
    private String NomeRua;
    private String complemento;
    private int NumCasa;

    @Override
    public String toString() {
        return "Endereco{" +
                "Cep='" + Cep +
                ", Bairro='" + Bairro +
                ", NomeRua='" + NomeRua +
                ", complemento='" + complemento +
                ", NumCasa=" + NumCasa +
                '}';
    }

    public Endereco(String cep, String bairro, String nomeRua, String complemento, int numCasa) {
        Cep = cep;
        Bairro = bairro;
        NomeRua = nomeRua;
        this.complemento = complemento;
        NumCasa = numCasa;
    }
    public Endereco() {
    }

    public String getCep() {
        return Cep;
    }
    public void setCep(String cep) {
        Cep = cep;
    }
    public String getBairro() {
        return Bairro;
    }
    public void setBairro(String bairro) {
        Bairro = bairro;
    }
    public String getNomeRua() {
        return NomeRua;
    }
    public void setNomeRua(String nomeRua) {
        NomeRua = nomeRua;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public int getNumCasa() {
        return NumCasa;
    }
    public void setNumCasa(int numCasa) {
        NumCasa = numCasa;
    }
}
