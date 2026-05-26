package Gui;

import Entidade.Plano;
import Servico.PlanoServico;

import java.util.List;
import java.util.Scanner;

public class GuiPlano {
    private final Scanner sc = new Scanner(System.in);
    private final PlanoServico servico;
    private static final String MSG_NAO_ENCONTRADO_LISTA = "NAO ENCONTRADO";
    private static final String MSG_NAO_ENCONTRADO = "NAO ENCONTRADO!";

    public GuiPlano(PlanoServico servico) {
        this.servico = servico;
    }

    public void menuPlano() {
        int opc = -1;
        while (opc != 0) {
            System.out.println("------------- MENU PLANO -------------");
            System.out.println("[1] - Cadastrar Plano Personalizado.");
            System.out.println("[2] - Listar Planos Disponiveis.");
            System.out.println("[3] - Remover Planos.");
            System.out.println("[4] - Alterar Planos.");
            System.out.println("[0] - Sair.");

            opc = lerInteiro("Escolha uma opcao: ");

            switch (opc) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    remover();
                    break;
                case 4:
                    atualizar();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcao invalida! Tente novamente.");
            }
        }
    }

    private void listar() {
        System.out.println("\n ======== LISTA DE PLANOS ========");
        try {
            List<Plano> planos = servico.listarPlano();
            if (planos.isEmpty()) {
                System.out.println(MSG_NAO_ENCONTRADO_LISTA);
                return;
            }
            for (Plano p : planos) {
                System.out.println(p);
            }
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel listar os planos agora.");
        }
    }

    private void cadastrar() {
        System.out.println("\n======== CADASTRO DO PLANO ========");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        double valor = lerDouble("Valor: ");
        int duracao = lerInteiro("Duracao: ");

        System.out.print("Descricao: ");
        String descricao = sc.nextLine();

        Plano plano = new Plano(null, nome, valor, descricao, duracao);

        try {
            String resultado = servico.cadastrarPlano(plano);
            System.out.println(resultado);
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel cadastrar o plano. Verifique os dados e tente novamente.");
        }
    }

    private void remover() {
        int id = lerInteiro("ID do plano: ");

        try {
            boolean removido = servico.removerPlano(id);
            if (removido) {
                System.out.println("PLANO REMOVIDO COM SUCESSO!");
            } else {
                System.out.println(MSG_NAO_ENCONTRADO);
            }
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel remover o plano agora.");
        }
    }

    private void atualizar() {
        int id = lerInteiro("ID do plano: ");

        System.out.print("Novo Nome: ");
        String nome = sc.nextLine();

        double valor = lerDouble("Novo Valor: ");
        int duracao = lerInteiro("Nova Duracao: ");

        System.out.print("Nova Descricao: ");
        String descricao = sc.nextLine();

        Plano plano = new Plano(id, nome, valor, descricao, duracao);

        try {
            boolean atualizado = servico.atualizarPlano(plano);
            if (atualizado) {
                System.out.println("PLANO ATUALIZADO COM SUCESSO!");
            } else {
                System.out.println(MSG_NAO_ENCONTRADO);
            }
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel atualizar o plano agora.");
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

    private double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = sc.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros.");
            }
        }
    }
}
