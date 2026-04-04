package Entidade;

public class Treino {
    private int id;
    private int idAluno;
    private int idPersonal;
    private String data;
    private String horario;
    private String descricao;

    public Treino(int id, int idAluno, int idPersonal, String data, String horario, String descricao) {
        this.id = id;
        this.idAluno = idAluno;
        this.idPersonal = idPersonal;
        this.data = data;
        this.horario = horario;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

