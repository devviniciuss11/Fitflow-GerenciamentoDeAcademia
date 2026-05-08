package Entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "desempenho")
public class Desempenho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 14)
    private String cpf;

    @Column(name = "peso", nullable = false)
    private double peso;

    @Column(name = "altura", nullable = false)
    private double altura;

    @Column(name = "imc", nullable = false)
    private double imc;

    @Column(name = "data_avaliacao", nullable = false)
    private LocalDate dataAvaliacao;

    public Desempenho() {
    }

    public Desempenho(Integer id, String cpf, double peso, double altura, String nome) {
        this.nome = nome;
        this.id = id;
        this.cpf = cpf;
        this.peso = peso;
        this.altura = altura;
        this.dataAvaliacao = LocalDate.now();
        this.imc = calcularImc(peso, altura);
    }

    private double calcularImc(double peso, double altura) {
        if (altura > 0) {
            return peso / (altura * altura);
        }
        return 0;
    }

    public String getClassificacaoImc() {
        if (imc < 18.5) return "Abaixo do peso";
        if (imc >= 18.5 && imc < 24.9) return "Peso normal";
        if (imc >= 25 && imc < 29.9) return "Sobrepeso";
        return "Obesidade";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
        this.imc = calcularImc(this.peso, this.altura);
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
        this.imc = calcularImc(this.peso, this.altura);
    }

    public double getImc() {
        return imc;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "ID: " + id + " | Aluno CPF: " + cpf + " | Peso: " + peso + "kg | Altura: " + altura + "m | IMC: " + String.format("%.2f", imc) + " (" + getClassificacaoImc() + ") | Data: " + dataAvaliacao.format(fmt);
    }
}
