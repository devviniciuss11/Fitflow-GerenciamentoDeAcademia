package Gui;

import Servico.DesempenhoServico;
import java.util.Scanner;

public class GuiDesempenho {
    Scanner sc = new Scanner(System.in);
    DesempenhoServico servico = new DesempenhoServico();

    public void menu() {
        int op = -1;
        while (op != 5) {
            System.out.println("\n========= MENU DESEMPENHO =========");
            System.out.println(" [1] - Lançar Novo Desempenho (Peso/Altura)");
            System.out.println(" [2] - Listar Desempenhos e IMC");
            System.out.println(" [3] - Remover Desempenho");
            System.out.println(" [4] - Atualizar Dados (Peso ou Altura)");
            System.out.println(" [5] - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                op = sc.nextInt();
                sc.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("ERRO: Digite apenas números!");
                sc.nextLine();
                continue;
            }

            switch (op) {
                case 1 -> servico.cadastrarDesempenho();
                case 2 -> servico.listarDesempenhos();
                case 3 -> servico.excluirDesempenho();
                case 4 -> servico.alterarDesempenho();
                case 5 -> System.out.println("Voltando ao Menu Principal...");
                default -> System.out.println("Opção Inválida! Tente Novamente.");
            }
        }
    }
}