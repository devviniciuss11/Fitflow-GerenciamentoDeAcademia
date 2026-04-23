package Gui;

import Servico.PagamentoServico;
import java.util.Scanner;

public class GuiPagamento {
    private Scanner sc = new Scanner(System.in);
    private PagamentoServico servico = new PagamentoServico();

    public void menuPagamento() {
        int opc = -1;
        while (opc != 0) {
            System.out.println("      MENU PAGAMENTO     ");
            System.out.println(" [1] - Realizar Novo Pagamento");
            System.out.println(" [2] - Ver Histórico de Pagamentos");
            System.out.println(" [3] - Remover Pagamento (Deletar)");
            System.out.println(" [4] - Alterar Status de Pagamento (Atualizar)");
            System.out.println(" [5] - Extrato dos Pagamentos (Soma)");
            System.out.println(" [0] - Voltar ao Menu Principal");
            System.out.print(" Escolha uma opção: ");

            try {
                opc = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("ERRO: Digite apenas números!");
                sc.nextLine();
                continue;
            }

            switch (opc) {
                case 1:
                    servico.realizarPagamento();
                    break;
                case 2:
                    servico.listarPagamento();
                    break;
                case 3:
                    servico.removerPagamento();
                    break;
                case 4:
                    servico.alterarStatusPagamento();
                    break;
                case 5:
                    servico.extratoDosPagamentos();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }
}
