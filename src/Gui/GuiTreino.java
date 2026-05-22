package Gui;

import Entidade.Treino;
import Repositorio.AlunoRepositorio;
import Repositorio.TreinoRepositorio;
import Servico.PersonalServico;
import Servico.TreinoServico;

import java.util.List;
import java.util.Scanner;

public class GuiTreino {

    private final Scanner sc = new Scanner(System.in);
    private TreinoServico servico;

    public GuiTreino(TreinoServico servico) {
        this.servico = servico;
    }

    public GuiTreino() {
        TreinoRepositorio repositorio = new TreinoRepositorio();
        PersonalServico personalServico = new PersonalServico();
        AlunoRepositorio alunoRepositorio = new AlunoRepositorio();
        this.servico = new TreinoServico(repositorio, personalServico, alunoRepositorio);
    }

    public void menuTreino() {
        int opc = -1;
        while (opc != 5) {
            System.out.println("\n========== MENU TREINOS ==========");
            System.out.println(" [1] - Cadastrar Ficha de Treino.");
            System.out.println(" [2] - Listar Fichas de Treino.");
            System.out.println(" [3] - Remover Ficha de Treino.");
            System.out.println(" [4] - Alterar Ficha de Treino.");
            System.out.println(" [5] - Voltar ao Menu Principal.");
            System.out.println("==================================");

            opc = lerInteiro("Escolha uma opção: ");

            switch (opc) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    remover();
                    break;
                case 4:
                    atualizar();
                    break;
                case 5:
                    System.out.println("Voltando para o Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void listar() {
        System.out.println("\n======== LISTA DE TREINOS ========");

        try {
            List<Treino> lista = servico.listarTreinos();

            if (lista == null || lista.isEmpty()) {
                System.out.println("Nenhum Treino Encontrado!");
                return;
            }

            for (Treino t : lista) {
                System.out.println("ID: " + t.getId());
                System.out.println("Aluno: " + t.getIdAluno());
                System.out.println("Personal: " + t.getIdPersonal());
                System.out.println("Dia: " + t.getData());
                System.out.println("Horário: " + t.getHorario());
                System.out.println("Descrição: " + t.getDescricao());
                System.out.println("----------------------");
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar treinos: " + e.getMessage());
        }
    }

    private void cadastrar() {
        System.out.println("\n======== CADASTRO DE TREINO ========");

        int idAluno = lerInteiro("ID do Aluno: ");
        int idPersonal = lerInteiro("ID do Personal: ");

        System.out.println("Escolha o dia para esse treino:");
        System.out.println("Segunda | Terça | Quarta | Quinta | Sexta");
        String data = sc.nextLine();

        System.out.print("Horário: ");
        String horario = sc.nextLine();

        System.out.print("Músculo(s): ");
        String descricao = sc.nextLine();

        Treino treino = new Treino(null, idAluno, idPersonal, data, horario, descricao);

        String resultado = servico.cadastrarTreino(treino);
        System.out.println(resultado);
    }

    private void remover() {
        int id = lerInteiro("ID do treino a remover: ");

        if (servico.removerTreino(id)) {
            System.out.println("A Ficha De Treino está Sendo Removida.");
        } else {
            System.out.println("Ficha De Treino Nao Encontrada Tente novamente!");
        }
    }

    private void atualizar() {
        System.out.println("\n======== ALTERAÇÃO DE TREINO ========");

        List<Treino> lista = servico.listarTreinos();
        if (lista == null || lista.isEmpty()) {
            System.out.println("Nenhum treino Encontrado!");
            return;
        }

        listar();

        int id = lerInteiro("Digite o ID do treino que deseja alterar: ");
        int idAluno = lerInteiro("Novo ID Aluno: ");
        int idPersonal = lerInteiro("Novo ID Personal: ");

        System.out.print("Nova Data (ex.: Segunda): ");
        String data = sc.nextLine();

        System.out.print("Novo Horário (ex.: 18:00): ");
        String horario = sc.nextLine();

        System.out.print("Nova Descrição do Treino: ");
        String descricao = sc.nextLine();

        Treino treinoAtualizado = new Treino(id, idAluno, idPersonal, data, horario, descricao);

        String resultado = servico.atualizarTreino(id, treinoAtualizado);
        System.out.println(resultado);
    }

    private int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = sc.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
            }
        }
    }
}
