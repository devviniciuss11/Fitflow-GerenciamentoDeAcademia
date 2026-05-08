package Entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "treino")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_aluno", nullable = false)
    private int idAluno;

    @Column(name = "id_personal", nullable = false)
    private int idPersonal;

    @Column(name = "data_treino", nullable = false, length = 40)
    private String data;

    @Column(name = "horario", nullable = false, length = 40)
    private String horario;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    public Treino() {
    }

    public Treino(Integer id, int idAluno, int idPersonal, String data, String horario, String descricao) {
        this.id = id;
        this.idAluno = idAluno;
        this.idPersonal = idPersonal;
        this.data = data;
        this.horario = horario;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
