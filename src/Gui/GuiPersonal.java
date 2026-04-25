package Gui;
import Servico.PersonalServico;
import java.util.Scanner;


public class GuiPersonal {
    Scanner sc = new Scanner(System.in);
    PersonalServico servico = new PersonalServico();
    GuiTreino GuiTreino = new GuiTreino();

    public void menu(){
        int op = -1;
        while (op != 7){
            System.out.println("-------------MENU PERSONAL-------------");
            System.out.println("[1] - Cadastrar Novo Personal");
            System.out.println("[2] - Listar Personais Cadastrados");
            System.out.println("[3] - Remover Personal");
            System.out.println("[4] - Alterar Dados de um Personal");
            System.out.println("[5] - Vicnular Aluno ao Personal");
            System.out.println("[6] - Ver Alunos de um Personal");
            System.out.println("[7] - Ir Para o Menu de Treino");
            System.out.println("[8] - Voltar ao Menu Principal");
            System.out.println("Escolha uma opção: ");

            try {
                op =sc.nextInt();
                sc.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("ERRO: Digite apenas números!");
                sc.nextLine();
                continue;

            }

            switch(op){
                case 1 -> servico.cadastrarPersonal();
                case 2 -> servico.listarPersonais();
                case 3 -> servico.excluirPersonal();
                case 4 -> servico.alterarPersonal();
                case 5 -> servico.vincularAlunoAoPersonal();
                case 6 -> servico.listarAlunosDoPersonal();
                case 7 -> GuiTreino.menuTreino();
                case 8 -> { return; }
                default -> System.out.println("Opção Inválida! Tente Novamente. ");

            }

        }

    }

}
