package Gui;


import Entidade.Treino;
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
        this.servico = servico;
    }

    public  void menuTreino() {
        int opc = -1;
        // Mude de 0 para 5 aqui:
        while (opc != 5) {
            System.out.println("\n========== MENU TREINOS ==========");
            System.out.println(" [1] Cadastrar Treino. ");
            System.out.println(" [2] Listar Treinos. ");
            System.out.println(" [3] Remover Treino. ");
            System.out.println(" [4] Alterar Treino. ");
            System.out.println(" [5] Voltar para o menuInicial Principal. ");
            System.out.println("==================================");

            opc = sc.nextInt();

            switch (opc) {
                case 1: cadastrar(); break;
                case 2: listar(); break;
                case 3: remover(); break;
                case 4: atualizar(); break;
                case 5:
                    System.out.println("Voltando para o menuInicial Principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listar() {
        List<Treino> treinos = servico.listarTreinos();
        System.out.println("\n ======== LISTA DE TREINOS ========");

        if (treinos.isEmpty()) {
            System.out.println("NENHUM TREINO ENCONTRADO!");
            return;
        }
        for (Treino t : treinos) {
            // Adicionado t.getData() ou t.getDiaSemana() para aparecer na tela
            System.out.println();
        }
    }

    private void cadastrar() {
        System.out.println("\n ======== CADASTRO DE TREINO ========");

        System.out.print("ID do Treino: ");
        int id = sc.nextInt();
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


        Treino treino;

        treino = new Treino(id, idAluno, idPersonal, data, horario, descricao);
        servico.cadastrarTreino(treino);

        System.out.println("TREINO CADASTRADO COM SUCESSO!");
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
        System.out.print("ID do treino que deseja alterar: ");
        int id = sc.nextInt();
        sc.nextLine();

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

        Treino treino = new Treino(id, idAluno, idPersonal, data, horario, descricao);

        if (servico.atualizarTreino(treino)) {
            System.out.println("TREINO ATUALIZADO!");
        } else {
            System.out.println("TREINO NÃO ENCONTRADO!");
        }
    }
}
