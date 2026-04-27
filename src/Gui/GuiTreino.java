package Gui;

import Entidade.Treino;
import Repositorio.AlunoRepositorio;
import Repositorio.TreinoRepositorio;
import Servico.PersonalServico;
import Servico.TreinoServico;

import java.util.List;
import java.util.Scanner;

public class GuiTreino {

    private Scanner sc = new Scanner(System.in);
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
            System.out.println(" [1] Cadastrar Lista De Treino. ");
            System.out.println(" [2] Listar Fichas De Treinos. ");
            System.out.println(" [3] Remover Ficha De Treino. ");
            System.out.println(" [4] Alterar Ficha de Treino. ");
            System.out.println(" [5] Voltar para o Menu Principal. ");
            System.out.println("==================================");

            opc = sc.nextInt();

            switch (opc) {
                case 1: cadastrar(); break;
                case 2: listar();    break;
                case 3: remover();   break;
                case 4: atualizar(); break;
                case 5:
                    System.out.println("Voltando para o Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listar() {
        System.out.println("\n ======== LISTA DE TREINOS ========");

        try {
            List<Treino> lista = servico.listarTreinos();

            if (lista == null || lista.isEmpty()) {
                System.out.println("NENHUM TREINO ENCONTRADO!");
                return;
            }

            for (Treino t : lista) {
                System.out.println("ID: "        + t.getId());
                System.out.println("Aluno: "     + t.getIdAluno());
                System.out.println("Personal: "  + t.getIdPersonal());
                System.out.println("Dia: "       + t.getData());
                System.out.println("Horário: "   + t.getHorario());
                System.out.println("Descrição: " + t.getDescricao());
                System.out.println("----------------------");
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar treinos: " + e.getMessage());
        }
    }

    private void cadastrar() {
        System.out.println("\n ======== CADASTRO DE TREINO ========");

        System.out.print("ID do Aluno: ");
        int idAluno = sc.nextInt();
        System.out.print("ID do Personal: ");
        int idPersonal = sc.nextInt();
        sc.nextLine();

        System.out.println("Escolha o Dia que você deseja colocar esse treino:");
        System.out.println("Segunda | Terça | Quarta | Quinta | Sexta ");
        String data = sc.nextLine();
        System.out.print("Horário: ");
        String horario = sc.nextLine();
        System.out.print("Musculo(s): ");
        String descricao = sc.nextLine();

        Treino treino = new Treino(0, idAluno, idPersonal, data, horario, descricao);

        String resultado = servico.cadastrarTreino(treino);
        System.out.println(resultado);
    }

    private void remover() {
        System.out.print("ID do treino a remover: ");
        int id = sc.nextInt();

        if (servico.removerTreino(id)) {
            System.out.println("TREINO REMOVIDO!");
        } else {
            System.out.println("TREINO NÃO ENCONTRADO!");
        }
    }

    private void atualizar() {
        int id = sc.nextInt();

        System.out.print("Novo ID Aluno: ");
        int idAluno = sc.nextInt();

        System.out.print("Novo ID Personal: ");
        int idPersonal = sc.nextInt();
        sc.nextLine();

        System.out.print("Nova Data: ");
        String data = sc.nextLine();

        System.out.print("Novo Horário: ");
        String horario = sc.nextLine();

        System.out.print("Novo Treino: ");
        String descricao = sc.nextLine();

        Treino treinoAtualizado = new Treino(id, idAluno, idPersonal, data, horario, descricao);

        String resultado = servico.atualizarTreino(id, treinoAtualizado);
        System.out.println(resultado);
    }
}