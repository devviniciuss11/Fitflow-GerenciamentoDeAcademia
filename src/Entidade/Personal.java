package Entidade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Personal extends Pessoa {
    private String craf;
    private double salario;
    private double horarioTrabalho;
    private ArrayList<String>Alunosdele;

    @Override
    public void VerLogin(){
        System.out.println("Email para realizar o login:"+this.getSenha());
        System.out.println("Senha para realizar o login:"+this.getSenha());
    }

    public Personal(int id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha){
        super(id, nome, cpf, dataNascimento, email, telefone, senha);
    }

    public Personal(int id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha,String craf, double salario, double horarioTrabalho, ArrayList<String> alunosdele) {
        super(id, nome, cpf, dataNascimento, email, telefone, senha);
        this.craf = craf;
        this.salario = salario;
        this.horarioTrabalho = horarioTrabalho;
        Alunosdele = alunosdele;
    }

    public String getCraf() {
        return craf;
    }

    public void setCraf(String craf) {
        this.craf = craf;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getHorarioTrabalho() {
        return horarioTrabalho;
    }

    public void setHorarioTrabalho(double horarioTrabalho) {
        this.horarioTrabalho = horarioTrabalho;
    }

    public ArrayList<String> getAlunosdele() {
        return Alunosdele;
    }

    public void setAlunosdele(ArrayList<String> alunosdele) {
        Alunosdele = alunosdele;
    }
    public void adicionarAluno(String nome){
        Alunosdele.add(nome);
    }
    public void removerAluno(String nome){
        Alunosdele.remove(nome);
    }
    public void atualizarAluno(String nome, String novoNome){
        Alunosdele.set(Alunosdele.indexOf(nome), novoNome);
    }

}
