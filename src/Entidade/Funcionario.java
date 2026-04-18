package Entidade;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Funcionario extends Pessoa {
    private String cargo;
    private Double salario;
    private Double horarioTrabalho;
    private Set<LocalDate> diasTrabalhados = new HashSet<>();
    @Override
    public void VerLogin(){
        System.out.println("Email para realizar o login:"+this.getSenha());
        System.out.println("Senha para realizar o login:"+this.getSenha());
    }

    public Funcionario(
            int id, String nome, String cpf, LocalDate dataNascimento,
            String email, String telefone, String senha, String cargo, Double salario, Double horarioTrabalho
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

    public Set<LocalDate> getDiasTrabalhados() {
        return diasTrabalhados;
    }

    public void setDiasTrabalhados(Set<LocalDate> diasTrabalhados) {
        this.diasTrabalhados = diasTrabalhados;
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
