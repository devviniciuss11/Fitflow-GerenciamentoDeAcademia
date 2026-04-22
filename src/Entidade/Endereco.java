package Entidade;

public class Endereco {
    private String NomeRua;
    private String Cep;
    private String Bairro;
    private int NumCasa;
    private String Complemento;

    public Endereco(String nomeRua, String cep, String bairro, int numCasa, String complemento) {
        NomeRua = nomeRua;
        Cep = cep;
        Bairro = bairro;
        NumCasa = numCasa;
        Complemento = complemento;
    }

    public String getNomeRua() {
        return NomeRua;
    }

    public void setNomeRua(String nomeRua) {
        NomeRua = nomeRua;
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

    public int getNumCasa() {
        return NumCasa;
    }

    public void setNumCasa(int numCasa) {
        NumCasa = numCasa;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }
}
