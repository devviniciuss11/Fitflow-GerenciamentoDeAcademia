package Gui;

import Entidade.Pagamento;
import Servico.PagamentoServico;
import java.util.Scanner;

public class GuiPagamento {
    private Scanner sc = new Scanner(System.in);
    private PagamentoServico servico = new PagamentoServico();

    public void menuPagamento() {
        int opc = -1;
        while (opc != 0) {
            System.out.println("     MENU PAGAMENTO     ");
            System.out.println(" [1] - Realizar Novo Pagamento");
            System.out.println(" [2] - Ver Histórico de Pagamento");
            System.out.println(" [0] - Voltar ao Menu Principal");
            System.out.println(" Escolha uma opção");

            try {
                opc = sc.nextInt();
                sc.nextLine();

            }catch (Exception e) {
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
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");

            }
        }

    }
}

