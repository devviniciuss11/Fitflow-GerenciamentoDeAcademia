package Entidade;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "personal")
public class Personal extends Pessoa {

    @Column(name = "craf", unique = true, length = 30)
    private String craf;

    @Column(name = "salario")
    private Double salario;

    @Column(name = "horario_trabalho")
    private Double horarioTrabalho;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "personal_alunos", joinColumns = @JoinColumn(name = "personal_id"))
    @Column(name = "nome_aluno", nullable = false, length = 120)
    private List<String> alunosDele = new ArrayList<>();

    public Personal() {
    }

    @Override
    public void VerLogin() {
        System.out.println("Email para realizar o login:" + this.getSenha());
        System.out.println("Senha para realizar o login:" + this.getSenha());
    }

    @Override
    public String toString() {
        return "ID: " + getId() + ", Nome: " + getNome() + ",CPF: " + getCpf() + ",Data de Nascimento: " + getDataNascimento() + ",Email: " + getEmail() + ",Telefone: " + getTelefone() + ",Senha: " + getSenha() + ",CRAF: " + getCraf() + ",Salario: " + getSalario() + ",Horario de Trabalho: " + getHorarioTrabalho() + "Endereco : " + getEndereco();
    }

    public Personal(Integer id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha) {
        super(id, nome, cpf, dataNascimento, email, telefone, senha, new Endereco());
    }

    public Personal(Integer id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha, Endereco endereco) {
        super(id, nome, cpf, dataNascimento, email, telefone, senha, endereco);
    }

    public Personal(Integer id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha,
                    String craf, double salario, double horarioTrabalho, ArrayList<String> alunosdele) {
        super(id, nome, cpf, dataNascimento, email, telefone, senha, new Endereco());
        this.craf = craf;
        this.salario = salario;
        this.horarioTrabalho = horarioTrabalho;
        this.alunosDele = (alunosdele == null) ? new ArrayList<>() : new ArrayList<>(alunosdele);
    }

    public Personal(Integer id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha,
                    String craf, double salario, double horarioTrabalho, ArrayList<String> alunosdele, Endereco endereco) {
        super(id, nome, cpf, dataNascimento, email, telefone, senha, endereco);
        this.craf = craf;
        this.salario = salario;
        this.horarioTrabalho = horarioTrabalho;
        this.alunosDele = (alunosdele == null) ? new ArrayList<>() : new ArrayList<>(alunosdele);
    }

    public String getCraf() {
        return craf;
    }

    public void setCraf(String craf) {
        this.craf = craf;
    }

    public double getSalario() {
        return salario == null ? 0.0 : salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getHorarioTrabalho() {
        return horarioTrabalho == null ? 0.0 : horarioTrabalho;
    }

    public void setHorarioTrabalho(double horarioTrabalho) {
        this.horarioTrabalho = horarioTrabalho;
    }

    public ArrayList<String> getAlunosdele() {
        return new ArrayList<>(alunosDele);
    }

    public void setAlunosdele(ArrayList<String> alunosdele) {
        this.alunosDele = (alunosdele == null) ? new ArrayList<>() : new ArrayList<>(alunosdele);
    }

    public void adicionarAluno(String nome) {
        alunosDele.add(nome);
    }

    public void removerAluno(String nome) {
        alunosDele.remove(nome);
    }

    public void atualizarAluno(String nome, String novoNome) {
        int indice = alunosDele.indexOf(nome);
        if (indice >= 0) {
            alunosDele.set(indice, novoNome);
        }
    }
}
