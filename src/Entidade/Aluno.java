package Entidade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Aluno extends Pessoa{
    private ArrayList<Plano> planos;
    private ArrayList<Treino>fichaDeTreino;
    public Set<LocalDate> diasTreino = new HashSet<>();

    @Override
    public void VerLogin(){
        System.out.println("Email para realizar o login:"+this.getSenha());
        System.out.println("Senha para realizar o login:"+this.getSenha());
    }
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFmt = (getDataNascimento() == null) ? "N/A" : getDataNascimento().format(fmt);
        return "ID: " + getId() + ", Nome: " + getNome() + ",CPF: "+getCpf() + ",Data de Nascimento: "+dataFmt + ",Email: "+getEmail() + ",Telefone: "+getTelefone() + ",Senha: "+getSenha() + ",Endereço:"+getEndereco();
    }
    public Aluno(int id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha,ArrayList<Plano> AlunoPlano, ArrayList<Treino> fichaDeTreino, Endereco endereco){
        super(id,nome,cpf,dataNascimento,email,telefone,senha,new Endereco());
        this.fichaDeTreino = new ArrayList<>();
        this.planos = new ArrayList<>();

    }



    public Set<LocalDate> getDiasTreino() {
        return diasTreino;
    }

    public void setDiasTreino(Set<LocalDate> diasTreino) {
        this.diasTreino = diasTreino;
    }

    public ArrayList<Plano> getPlanos() {
        return planos;
    }

    public void setPlanos(ArrayList<Plano> planos) {
        this.planos = planos;
    }

    public ArrayList<Treino> getFichaDeTreino() {
        return fichaDeTreino;
    }

    public void setFichaDeTreino(ArrayList<Treino> fichaDeTreino) {
        this.fichaDeTreino = fichaDeTreino;
    }

}