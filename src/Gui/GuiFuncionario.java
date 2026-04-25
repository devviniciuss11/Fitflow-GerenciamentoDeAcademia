package Gui;

import Servico.FuncionarioServico;
import java.util.Scanner;

public class GuiFuncionario {
    Scanner sc = new Scanner(System.in);
    FuncionarioServico funcionarioServico = new FuncionarioServico();

    public void menufAdm() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("------------MENU FUNCIONÁRIO-------------");
            System.out.println(" [1] - Cadastrar Funcionário");
            System.out.println(" [2] - Listar Funcionário");
            System.out.println(" [3] - Remover Funcionário");
            System.out.println(" [4] - Alterar dados do Funcionário");
            System.out.println(" [5] - Registrar Prescença do Funcionário");
            System.out.println(" [0] - Sair do Menu");
            System.out.println("-----------------------------------------");


            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    FuncionarioServico funcionarioServico = new FuncionarioServico();
                    funcionarioServico.cadastrarFuncionarios();
                    break;

                case 2:
                    FuncionarioServico funcionarioServico2 = new FuncionarioServico();
                    funcionarioServico2.ListarFuncionarios();
                    break;

                case 3:
                    FuncionarioServico funcionarioServico3 = new FuncionarioServico();
                    funcionarioServico3.RemoverFuncionario();
                    break;

                case 4:
                    FuncionarioServico funcionarioServico4 = new FuncionarioServico();
                    funcionarioServico4.AlterarFuncionario();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public void meunuFuncionarios(){
        int opcao = -1;

        while (opcao != 0) {

            System.out.println("------------MENU FUNCIONÁRIO-------------");
            System.out.println(" [1] - Registrar Prescença do Funcionário");
            System.out.println(" [0] - Sair do Menu");
            System.out.println("-----------------------------------------");


            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    System.out.println("Registrar Presenca do Funcionario");
                    break;

                case 0:
                    System.out.println("Saindo...");
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }

    }
}