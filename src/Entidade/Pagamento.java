package Entidade;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private Aluno aluno;
    private Plano plano;
    private double valor;
    private LocalDate dataPagamento;
    private LocalDate dataVencimento;
    private StatusPagamento status;
    private String metodoPagamento;

    public enum StatusPagamento {
        PENDENTE,
        PAGO,
        CANCELADO
    }

    public Pagamento(int id, Aluno aluno, Plano plano, StatusPagamento status, String metodoPagamento){
        this.id = id;

        this.aluno = aluno;

        this.plano = plano;

        this.valor = plano.getValor();

        this.dataPagamento = LocalDate.now();

        this.dataVencimento = this.dataPagamento.plusDays(plano.getDuracao());

        this.status = status;

        this.metodoPagamento = metodoPagamento;
    }

    public int getId(){ return id; }

    public Aluno getAluno() { return aluno; }

    public Plano getPlano() { return plano; }

    public double getValor() { return valor; }

    public LocalDate getDataPagamento() { return dataPagamento; }

    public LocalDate getDataVencimento() { return dataVencimento; }

    public StatusPagamento getStatus() { return status; }

    public void setStatus(StatusPagamento status) { this.status = status; }

    public String getMetodoPagamento() { return metodoPagamento; }

    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    @Override

    public String toString() {
        return " ID: " + id +
                " | Aluno: " + aluno.getNome() +
                " | Plano: " + plano.getNome() +
                " | Valor: R$ " + valor +
                " | Método: " + metodoPagamento +
                " | Pagamento: " + dataPagamento +
                " | Vencimento: " + dataVencimento +
                " | Status: " + status;
    }
}