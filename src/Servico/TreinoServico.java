package Servico;

import Entidade.Treino;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import Repositorio.AlunoRepositorio;
import Repositorio.TreinoRepositorio;

public class TreinoServico {
    private TreinoRepositorio repositorio;
    private AlunoRepositorio alunoServico;
    private PersonalServico personalServico;

    public TreinoServico(TreinoRepositorio repositorio, PersonalServico personalServico, AlunoRepositorio alunoServico) {
        this.repositorio = repositorio;
        this.personalServico = personalServico;
        this.alunoServico = alunoServico;
    }
    public int geradorID() {
        HashSet<Integer> idsUsados = new HashSet<>();
        Random random = new Random();
        int novoID;

        do {
            novoID = random.nextInt(10000);
        } while (idsUsados.contains(novoID));

        idsUsados.add(novoID);
        return novoID;
    }

    public String cadastrarTreino(Treino treino) {
        try {
            StringBuilder erros = new StringBuilder();

            if (alunoServico.buscarPorId(treino.getIdAluno()) == null) {
                erros.append("Aluno não encontrado!\n");
            }

            if (personalServico.buscarPorIdPer(treino.getIdPersonal()) == null) {
                erros.append("Personal não encontrado!\n");
            }

            if (erros.length() > 0) {
                return erros.toString().trim();
            }

            treino.setId(geradorID());
            repositorio.salvar(treino);
            return "Treino cadastrado com sucesso!";

        } catch (NullPointerException e) {
            return "Erro: serviço de aluno ou personal não foi inicializado.";
        } catch (Exception e) {
            return "Erro inesperado ao cadastrar treino.";
        }
    }

    public List<Treino> listarTreinos() {
        return repositorio.listarTodos();
    }

    public boolean removerTreino(int id) {
        return repositorio.remover(id);
    }

    public String atualizarTreino(int id, Treino treinoAtualizado) {
        try {
            StringBuilder erros = new StringBuilder();

            if (alunoServico.buscarPorId(treinoAtualizado.getIdAluno()) == null) {
                erros.append("Aluno não encontrado!\n");
            }

            if (personalServico.buscarPorIdPer(treinoAtualizado.getIdPersonal()) == null) {
                erros.append("Personal não encontrado!\n");
            }

            if (erros.length() > 0) {
                return erros.toString().trim();
            }

            treinoAtualizado.setId(id);

            if (repositorio.atualizar(treinoAtualizado)) {
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
