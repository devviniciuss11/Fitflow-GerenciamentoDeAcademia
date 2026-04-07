package Entidade;

import java.time.LocalDate;


public abstract class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate  dataNascimento;
    private String email;
    private String telefone;
    private String senha;

    public Pessoa(int id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }
    public abstract void VerLogin();

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getSenha() {
        return senha;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

}


