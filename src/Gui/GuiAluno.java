package Gui;

import Servico.AlunoServico;

import java.util.Scanner;

public class GuiAluno {
    Scanner sc1 = new Scanner(System.in);
    public void menu(){
        int opc1 =1;
        while (opc1 ==1){
            System.out.println("           MENU ALUNO           ");
            System.out.println(" [1] - Cadastrar Novo Aluno");
            System.out.println(" [2] - Listar Alunos Cadastrados");
            System.out.println(" [3] - Remover Aluno");
            System.out.println(" [4] - Alterar Dados de um Aluno");
            System.out.println(" [5] - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            System.out.println("5- Sair");
            int op;
            try {
                op =sc1.nextInt();
            }catch (java.util.InputMismatchException e){
                System.out.println("digite apenas numeros");
                sc1.nextLine();
                return;
            }
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
                    System.out.println("Saindo...");
                    opc1 = 0;

            }

        }




    }
}
