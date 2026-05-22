package Gui;

import Entidade.Plano;
import Servico.PlanoServico;

import java.util.List;
import java.util.Scanner;

public class GuiPlano {
    private Scanner sc = new Scanner(System.in);
    private PlanoServico servico;
    private static final String MSG_NAO_ENCONTRADO_LISTA = "NÃO ENCONTRADO";
    private static final String MSG_NAO_ENCONTRADO = "NÃO ENCONTRADO!";

    public GuiPlano(PlanoServico servico) {
        this.servico = servico;
    }

    public void menuPlano() {
        int opc = -1;
        while (opc != 0) {
            System.out.println("------------- MENU PLANO -------------");
            System.out.println("[1] - Cadastrar Plano Personalizado.");
            System.out.println("[2] - Listar Planos Disponíveis.");
            System.out.println("[3] - Remover Planos.");
            System.out.println("[4] - Alterar Planos.");
            System.out.println("[0] - Sair.");

            opc = sc.nextInt();

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
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void listar() {
        List<Plano> planos = servico.listarPlano();

        System.out.println("\n ======== LISTA DE PLANOS ========");

        if (planos.isEmpty()) {
            System.out.println(MSG_NAO_ENCONTRADO_LISTA);
            return;
        }
        for (Plano p : planos) {
            System.out.println(p);
        }
    }

    private void cadastrar() {
        System.out.println("\n======== CADASTRO DO PLANO ========");

        sc.nextLine();
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Valor: ");
        double valor = sc.nextDouble();

        System.out.println("Duração: ");
        int duracao = sc.nextInt();
        sc.nextLine();

        System.out.println("Descrição: ");
        String descricao = sc.nextLine();

        Plano plano = new Plano(null, nome, valor, descricao, duracao);

        String resultado = servico.cadastrarPlano(plano);
        System.out.println(resultado);
    }

    private void remover() {
        System.out.print("ID do plano: ");
        int id = sc.nextInt();

        boolean removido = servico.removerPlano(id);

        if (removido) {
            System.out.println("PLANO REMOVIDO COM SUCESSO! ");
        } else {
            System.out.println(MSG_NAO_ENCONTRADO);
        }
    }

    private void atualizar() {
        System.out.print("ID do plano: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Novo Nome: ");
        String nome = sc.nextLine();

        System.out.print("Novo Valor: ");
        double valor = sc.nextDouble();

        System.out.println("Nova Duração: ");
        int duracao = sc.nextInt();
        sc.nextLine();

        System.out.println("Nova Descrição: ");
        String descricao = sc.nextLine();

        Plano plano = new Plano(id, nome, valor, descricao, duracao);

        boolean atualizado = servico.atualizarPlano(plano);

        if (atualizado) {
            System.out.println("PLANO ATUALIZADO COM SUCESSO!");
        } else {
            System.out.println(MSG_NAO_ENCONTRADO);
        }
    }
}
