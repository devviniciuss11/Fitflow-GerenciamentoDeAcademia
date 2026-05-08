package Servico;

import Entidade.Treino;
import Repositorio.AlunoRepositorio;
import Repositorio.TreinoRepositorio;

import java.util.List;

public class TreinoServico {

    private TreinoRepositorio treinoRepositorio;
    private AlunoRepositorio alunoRepositorio;
    private PersonalServico personalServico;

    public TreinoServico(TreinoRepositorio repositorio, PersonalServico personalServico, AlunoRepositorio alunoRepositorio) {
        this.treinoRepositorio = repositorio;
        this.personalServico = personalServico;
        this.alunoRepositorio = alunoRepositorio;
    }

    private String validarTreino(Treino treino) {
        if (treino == null) {
            return "Treino nao pode ser nulo.";
        }

        StringBuilder erros = new StringBuilder();

        if (alunoRepositorio.buscarPorId(treino.getIdAluno()) == null) {
            erros.append("Aluno nao encontrado!\n");
        }

        if (personalServico.buscarPorIdPer(treino.getIdPersonal()) == null) {
            erros.append("Personal nao encontrado!\n");
        }

        return erros.length() > 0 ? erros.toString().trim() : null;
    }

    public String cadastrarTreino(Treino treino) {
        String erro = validarTreino(treino);
        if (erro != null) {
            return erro;
        }

        treino.setId(null);
        treinoRepositorio.salvar(treino);
        return "Treino cadastrado com sucesso!";
    }

    public List<Treino> listarTreinos() {
        return treinoRepositorio.listarTodos();
    }

    public boolean removerTreino(int id) {
        return treinoRepositorio.remover(id);
    }

    public String atualizarTreino(int id, Treino treinoAtualizado) {
        try {
            String erro = validarTreino(treinoAtualizado);
            if (erro != null) {
                return erro;
            }

            treinoAtualizado.setId(id);

            if (treinoRepositorio.atualizar(treinoAtualizado)) {
                return "Treino atualizado com sucesso!";
            }

            return "Treino nao encontrado!";

        } catch (NullPointerException e) {
            return "Erro: servico nao inicializado.";
        } catch (Exception e) {
            return "Erro inesperado ao atualizar treino.";
        }
    }
}
