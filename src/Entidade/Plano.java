package Entidade;

import java.util.ArrayList;

public class Plano {
    private String nome;
    private double valor;
    private int duracao;
    private String descricao;

    public Plano(){}

    public Plano(String nome, double valor, int duracao, String descricao){
        this.nome = nome;
        this.valor = valor;
        this.duracao = duracao;
        this.descricao = descricao;
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
