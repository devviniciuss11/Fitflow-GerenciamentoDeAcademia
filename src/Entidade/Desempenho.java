package Entidade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Desempenho {
    private int id;
    private String cpfAluno;
    private double peso;
    private double altura;
    private double imc;
    private LocalDate dataAvaliacao;

    public Desempenho(int id, String cpfAluno, double peso, double altura) {
        this.id = id;
        this.cpfAluno = cpfAluno;
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

    public int getId() { return id; }
    public String getCpfAluno() { return cpfAluno; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) {
        this.peso = peso;
        this.imc = calcularImc(this.peso, this.altura); // Atualiza IMC automaticamente
    }

    public double getAltura() { return altura; }
    public void setAltura(double altura) {
        this.altura = altura;
        this.imc = calcularImc(this.peso, this.altura); // Atualiza IMC automaticamente
    }

    public double getImc() { return imc; }
    public LocalDate getDataAvaliacao() { return dataAvaliacao; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "ID: " + id + " | Aluno CPF: " + cpfAluno + " | Peso: " + peso + "kg | Altura: " + altura + "m | IMC: " + String.format("%.2f", imc) + " (" + getClassificacaoImc() + ") | Data: " + dataAvaliacao.format(fmt);
    }
}