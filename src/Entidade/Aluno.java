package Entidade;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "aluno")
public class Aluno extends Pessoa {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "aluno_plano",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "plano_id")
    )
    private List<Plano> planos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "aluno_treino",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "treino_id")
    )
    private List<Treino> fichaDeTreino = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "aluno_dias_treino", joinColumns = @JoinColumn(name = "aluno_id"))
    @Column(name = "dia_treino")
    private Set<LocalDate> diasTreino = new HashSet<>();

    public Aluno() {
    }

    @Override
    public void VerLogin() {
        System.out.println("Email para realizar o login:" + this.getSenha());
        System.out.println("Senha para realizar o login:" + this.getSenha());
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFmt = (getDataNascimento() == null) ? "N/A" : getDataNascimento().format(fmt);
        return "ID: " + getId() + ", Nome: " + getNome() + ",CPF: " + getCpf() + ",Data de Nascimento: " + dataFmt + ",Email: " + getEmail() + ",Telefone: " + getTelefone() + ",Senha: " + getSenha() + ",Endereco:" + getEndereco();
    }

    public Aluno(Integer id, String nome, String cpf, LocalDate dataNascimento, String email, String telefone, String senha,
                 ArrayList<Plano> alunoPlano, ArrayList<Treino> fichaDeTreino, Endereco endereco) {
        super(id, nome, cpf, dataNascimento, email, telefone, senha, endereco);
        this.fichaDeTreino = (fichaDeTreino == null) ? new ArrayList<>() : new ArrayList<>(fichaDeTreino);
        this.planos = (alunoPlano == null) ? new ArrayList<>() : new ArrayList<>(alunoPlano);
    }

    public Set<LocalDate> getDiasTreino() {
        return diasTreino;
    }

    public void setDiasTreino(Set<LocalDate> diasTreino) {
        this.diasTreino = diasTreino;
    }

    public ArrayList<Plano> getPlanos() {
        return new ArrayList<>(planos);
    }

    public void setPlanos(ArrayList<Plano> planos) {
        this.planos = (planos == null) ? new ArrayList<>() : new ArrayList<>(planos);
    }

    public ArrayList<Treino> getFichaDeTreino() {
        return new ArrayList<>(fichaDeTreino);
    }

    public void setFichaDeTreino(ArrayList<Treino> fichaDeTreino) {
        this.fichaDeTreino = (fichaDeTreino == null) ? new ArrayList<>() : new ArrayList<>(fichaDeTreino);
    }
}
