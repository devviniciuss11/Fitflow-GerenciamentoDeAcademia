package Entidade;

public class Plano {
    private String nome;
    private double valor;
    private int duracao;
    private String descricao;
    private int id;

    public Plano(int id, String nome, double valor, String descricao, int duracao) {
        this.nome = nome;
        this.valor = valor;
        this.duracao = duracao;
        this.descricao = descricao;
        this.id = id;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getId(){
        return id;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return "ID: " + id +
                "| Nome: " + nome +
                "| Valor: R$ " + valor +
                "| Duração: " + duracao +
                "| Descrição: " + descricao;
    }
}
