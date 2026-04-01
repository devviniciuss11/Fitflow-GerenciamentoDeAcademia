package Gui;
import Servico.PlanoServico;
import java.util.Scanner;
import Entidade.Plano;
import java.util.List;

public class GuiPlano {
    private Scanner sc = new Scanner(System.in);
    private PlanoServico servico;

    public GuiPlano(PlanoServico servico){
        this.servico = servico;
    }

    public void menuPlano(){
        int opc = -1;
        while(opc != 0){
            System.out.println("========== MENU PLANOS ==========");
            System.out.println(" [1] Cadastra Plano personalizado. ");
            System.out.println(" [2] Listar Planos disponiveis. ");
            System.out.println(" [3] Remover Planos. ");
            System.out.println(" [4] Alterar Planos. ");
            System.out.println(" [0] Sair. ");
            System.out.println("=================================");

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
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listar(){

        List<Plano> planos = servico.listarPlano();

        System.out.println("\n ======== LISTA DE PLANOS ========");

        if (planos.isEmpty()) {
            System.out.println("NÃO ENCONTRADO!❌");
            return;
        }
        for (Plano p : planos) {
            System.out.println(p);
        }
    }

    private void cadastrar(){
        System.out.println("\n ======== CADASTRO DO PLANO ========");

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Valor: ");
        double valor = sc.nextDouble();

        System.out.println("Duração: ");
        int duracao = sc.nextInt();

        System.out.println("Descricao ");
        String descricao = sc.nextLine();

        Plano plano = new Plano(id, nome, valor, descricao, duracao);

        servico.cadastrarPlano(plano);
        System.out.println("Qtd depois de cadastrar: "
                + servico.listarPlano().size());
        System.out.println("CADASTRO REALIZADO! ✅");
    }
    private void remover(){
        System.out.print("ID do plano: ");
        int id = sc.nextInt();

        boolean removido = servico.removerPlano(id);

        if (removido) {
            System.out.println("PLANO REMOVIDO COM SUCESSO! ✅");
        } else {
            System.out.println("NÃO ENCONTRADO!❌");
        }
    }

    private void atualizar(){
        System.out.print("ID do plano: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Novo Nome: ");
        String nome = sc.nextLine();

        System.out.print("Novo Valor: ");
        double valor = sc.nextDouble();

        System.out.println("Novo Duração: ");
        int duracao = sc.nextInt();

        System.out.println("Nova Descrição: ");
        String descricao = sc.nextLine();

        Plano plano = new Plano(id, nome, valor, descricao, duracao);

        boolean atualizado = servico.atualizarPlano(plano);

        if (atualizado) {
            System.out.println("PLANO ATUALIZADO COM SUCESSO!✅");
        } else {
            System.out.println("NÃO ENCONTRADO!❌");
        }
    }
}
