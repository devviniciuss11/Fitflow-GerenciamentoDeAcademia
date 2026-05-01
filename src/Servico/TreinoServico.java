package Servico;

import Entidade.Treino;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import Repositorio.AlunoRepositorio;
import Repositorio.TreinoRepositorio;

public class TreinoServico{

    private TreinoRepositorio treinoRepositorio;
    private AlunoRepositorio alunoRepositorio;
    private PersonalServico personalServico;

    public TreinoServico(TreinoRepositorio repositorio, PersonalServico personalServico, AlunoRepositorio alunoRepositorio) {
        this.treinoRepositorio = repositorio;
        this.personalServico = personalServico;
        this.alunoRepositorio = alunoRepositorio;
    }

    private final HashSet<Integer> idsUsados = new HashSet<>();
    private final Random random = new Random();

    public int geradorID() {
        int novoID;
        do {
            novoID = random.nextInt(10000);
        } while (idsUsados.contains(novoID));
        idsUsados.add(novoID);
        return novoID;
    }

    private String validarTreino(Treino treino) {
        if (treino == null) {
            return "Treino não pode ser nulo.";
        }

        StringBuilder erros = new StringBuilder();

        if (alunoRepositorio.buscarPorId(treino.getIdAluno()) == null) {
            erros.append("Aluno não encontrado!\n");
        }

        if (personalServico.buscarPorIdPer(treino.getIdPersonal()) == null) {
            erros.append("Personal não encontrado!\n");
        }

        return erros.length() > 0 ? erros.toString().trim() : null;
    }

    public String cadastrarTreino(Treino treino) {
        String erro = validarTreino(treino);
        if (erro != null) {
            return erro;
        }

        treino.setId(geradorID());
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

            return "Treino não encontrado!";

        } catch (NullPointerException e) {
            return "Erro: serviço não inicializado.";
        } catch (Exception e) {
            return "Erro inesperado ao atualizar treino.";
        }
    }
}