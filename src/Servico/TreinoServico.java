package Servico;

import Entidade.Treino;
import java.util.List;

import Repositorio.TreinoRepositorio;

public class TreinoServico {
    private TreinoRepositorio repositorio;
    private AlunoServico alunoServico;
    private PersonalServico personalServico;

    public TreinoServico(TreinoRepositorio repositorio) {
        this.repositorio = repositorio;

    }

    public String cadastrarTreino(Treino treino) {
        repositorio.salvar(treino);

        return null;
    }

    public List<Treino> listarTreinos() {
        return repositorio.listarTodos();
    }

    public boolean removerTreino(int id) {
        return repositorio.remover(id);
    }

    public boolean atualizarTreino(Treino treino) {
        return repositorio.atualizar(treino);
    }
}
