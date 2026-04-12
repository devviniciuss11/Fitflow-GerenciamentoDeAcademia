package Entidade;

import java.time.LocalDate;

public class Funcionario extends Pessoa {
    private String cargo;
    private Double salario;
    private double horarioTrabalho;
    @Override
    public void VerLogin(){
        System.out.println("Email para realizar o login:"+this.getSenha());
        System.out.println("Senha para realizar o login:"+this.getSenha());
    }

    public Funcionario(
            int id, String nome, String cpf, LocalDate dataNascimento,
            String email, String telefone, String senha
    ){
        super(id, nome, cpf, dataNascimento, email, telefone, senha);
        this.cargo = cargo;
        this.salario = salario;
        this.horarioTrabalho = horarioTrabalho;
    }


    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public double getHorarioTrabalho() {
        return horarioTrabalho;
    }

    public void setHorarioTrabalho(double horarioTrabalho) {
        this.horarioTrabalho = horarioTrabalho;
    }
}
