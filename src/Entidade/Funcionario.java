package Entidade;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "funcionario")
public class Funcionario extends Pessoa {

    @Column(name = "cargo", length = 80)
    private String cargo;

    @Column(name = "salario")
    private Double salario;

    @Column(name = "horario_trabalho")
    private Double horarioTrabalho;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "funcionario_dias_trabalhados", joinColumns = @JoinColumn(name = "funcionario_id"))
    @Column(name = "dia_trabalho")
    private Set<LocalDate> diasTrabalhados = new HashSet<>();

    public Funcionario() {
    }

    @Override
    public void VerLogin() {
        System.out.println("Email para realizar o login:" + this.getSenha());
        System.out.println("Senha para realizar o login:" + this.getSenha());
    }

    @Override
    public String toString() {
        return "ID: " + getId() + ", Nome: " + getNome() + ",CPF: " + getCpf() + ",Data de Nascimento: " + getDataNascimento() + ",Email: " + getEmail() + ",Telefone: " + getTelefone() + ",Senha: " + getSenha() + ",Cargo: " + getCargo() + ",Salario: " + getSalario() + ",Horario de Trabalho: " + getHorarioTrabalho() + "Endereco : " + getEndereco();
    }

    public Funcionario(Integer id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha,
                       String cargo, Double salario, Double horarioTrabalho, Endereco endereco) {
        super(id, nome, cpf, dataNascimento, email, telefone, senha, endereco);
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
        return horarioTrabalho == null ? 0.0 : horarioTrabalho;
    }

    public void setHorarioTrabalho(double horarioTrabalho) {
        this.horarioTrabalho = horarioTrabalho;
    }
}
