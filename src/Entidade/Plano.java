package Entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plano")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "valor", nullable = false)
    private double valor;

    @Column(name = "duracao", nullable = false)
    private int duracao;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    public Plano() {
    }

    public Plano(Integer id, String nome, double valor, String descricao, int duracao) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "| Nome: " + nome +
                "| Valor: R$ " + valor +
                "| Duracao: " + duracao +
                "| Descricao: " + descricao;
    }
}
