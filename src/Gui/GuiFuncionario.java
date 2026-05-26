package Gui;

import Servico.FuncionarioServico;

import java.util.Scanner;

public class GuiFuncionario {
    private final Scanner sc = new Scanner(System.in);
    private final FuncionarioServico funcionarioServico = new FuncionarioServico();

    public void menufAdm() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("------------ MENU FUNCIONARIO ------------");
            System.out.println(" [1] - Cadastrar Funcionario");
            System.out.println(" [2] - Listar Funcionario");
            System.out.println(" [3] - Remover Funcionario");
            System.out.println(" [4] - Alterar Dados do Funcionario");
            System.out.println(" [5] - Registrar Presenca do Funcionario");
            System.out.println(" [0] - Sair do Menu");
            System.out.println("-----------------------------------------");

            opcao = lerInteiro("Escolha uma opcao: ");

            try {
                switch (opcao) {
                    case 1:
                        funcionarioServico.cadastrarFuncionarios();
                        break;
                    case 2:
                        funcionarioServico.ListarFuncionarios();
                        break;
                    case 3:
                        funcionarioServico.RemoverFuncionario();
                        break;
                    case 4:
                        funcionarioServico.AlterarFuncionario();
                        break;
                    case 5:
                        funcionarioServico.presencaFuncionario();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opcao invalida! Tente novamente.");
                }
            } catch (RuntimeException e) {
                System.out.println("Nao foi possivel concluir a operacao. Verifique os dados e tente novamente.");
            }
        }
    }

    public void meunuFuncionarios() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("------------ MENU FUNCIONARIO ------------");
            System.out.println(" [1] - Registrar Presenca do Funcionario");
            System.out.println(" [0] - Sair do Menu");
            System.out.println("-----------------------------------------");

            opcao = lerInteiro("Escolha uma opcao: ");

            try {
                switch (opcao) {
                    case 1:
                        funcionarioServico.presencaFuncionario();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opcao invalida! Tente novamente.");
                }
            } catch (RuntimeException e) {
                System.out.println("Nao foi possivel concluir a operacao. Verifique os dados e tente novamente.");
            }
        }
    }

    private int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = sc.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros.");
            }
        }
    }
}
