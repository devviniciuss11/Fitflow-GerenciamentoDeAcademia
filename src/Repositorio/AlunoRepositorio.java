package Repositorio;

import Entidade.Aluno;
import Entidade.Pessoa;

import java.util.ArrayList;

public class AlunoRepositorio {
    public static ArrayList<Aluno> alunos = new ArrayList<>();

    public void save(Aluno aluno){
        alunos.add(aluno);
    }


}

