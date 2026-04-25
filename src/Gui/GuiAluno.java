package Gui;

import Servico.AlunoServico;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GuiAluno {
    Scanner sc1 = new Scanner(System.in);
    public void menuDoAlunoAdm(){
        int opc1 =1;
        while (opc1 ==1){
            System.out.println("-------------MENU ALUNO-------------");
            System.out.println("[1] - Cadastrar Novo Aluno");
            System.out.println("[2] - Listar Alunos Cadastrados");
            System.out.println("[3] - Remover Aluno");
            System.out.println("[4] - Alterar Dados de Um Aluno");
            System.out.println("[5] - Presença de Um Aluno");
            System.out.println("[6] - Treino");
            System.out.println("[7] - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            int op;
            try {
                op =sc1.nextInt();
            }catch (InputMismatchException e){
                System.out.println("digite apenas numeros");
                sc1.nextLine();
                return;
            }try{
                switch (op){
                    case 1:
                        AlunoServico alunoServico = new AlunoServico();
                        alunoServico.cadastrarAluno();
                        break;
                    case 2:
                        AlunoServico alunoServico1 = new AlunoServico();
                        alunoServico1.MostrarAlunos();
                        break;
                    case 3:
                        AlunoServico alunoServico2 = new AlunoServico();
                        alunoServico2.excluirAluno();
                        break;
                    case 4:
                        AlunoServico alunoServico3 = new AlunoServico();
                        alunoServico3.alteraAluno();
                        break;

                    case 5:
                        AlunoServico alunoServico4 = new AlunoServico();
                        alunoServico4.presencaAluno();
                        break;

                    case 6:
                        GuiTreino guiTreino = new GuiTreino();
                        guiTreino.menuTreino();
                    case 7:
                        System.out.println("Saindo...");
                        opc1 = 0;

                        break;
                    default:
                        throw new IllegalStateException("Opcao Invalida: " + op);
                }

            }catch (Exception e){
                System.out.println("Opcao Invalida");
                sc1.nextLine();
            }


        }




    }

    public void menuDoAluno(){
        int opc = 1;
        while(opc != 0){

            System.out.println("-------------MENU ALUNO-------------");

            System.out.println("[1] - Check-in Diario");
            System.out.println("[2] - Treino");
            System.out.println("[3] - Escolher Plano Pra Comprar");
            System.out.println("[3] - Alterar Tipo De PLano");
            System.out.println("[0] - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

        }
    }
}
