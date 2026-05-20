package Servico;

import Entidade.Aluno;
import Entidade.Pagamento;
import Entidade.Plano;
import Repositorio.AlunoRepositorio;
import Repositorio.PagamentoRepositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PagamentoServico {

    Scanner sc = new Scanner(System.in);
    PagamentoRepositorio pagamentoRepositorio = new PagamentoRepositorio();
    PlanoServico planoServico = new PlanoServico();
    AlunoRepositorio alunoRepositorio = new AlunoRepositorio();

    public void realizarPagamento() {
        System.out.println("      REALIZAR PAGAMENTO      ");
        System.out.println(" Digite o CPF do Aluno: ");
        String cpfAluno = sc.nextLine();

        Aluno alunoEncontrado = alunoRepositorio.buscarPorCpf(cpfAluno);

        if (alunoEncontrado == null) {
            System.out.println(" O Aluno nao foi encontrado. Por favor, verifique o CPF. ");
            return;
        }

        System.out.println("    PLANOS DISPONIVEIS    ");
        for (Plano p : planoServico.listarPlano()) {
            System.out.println(p.getId() + " - " + p.getNome() + " (R$ " + p.getValor() + ")");
        }

        System.out.println("Digite o ID do Plano que o aluno vai pagar: ");
        int idPlano = sc.nextInt();
        sc.nextLine();

        Plano planoEncontrado = planoServico.buscarPorId(idPlano);

        if (planoEncontrado == null) {
            System.out.println("  PLANO NAO ENCONTRADO  ");
            return;
        }

        System.out.println("  Escolha o metodo de pagamento: ");
        System.out.println(" [1] - PIX");
        System.out.println(" [2] - Boleto");
        System.out.println(" [3] - Cartao de Credito/Debito");
        System.out.print(" Opcao: ");
        int opcMetodo = sc.nextInt();
        sc.nextLine();

        String metodoEscolhido;
        switch (opcMetodo) {
            case 1:
                metodoEscolhido = "PIX";
                System.out.println("    PAGAMENTO VIA PIX    ");
                System.out.println("Chave PIX: 123456");
                break;
            case 2:
                metodoEscolhido = "Boleto";
                System.out.println("    Pagamento via Boleto    ");
                System.out.println("Codigo de Barras: 123456");
                break;
            case 3:
                metodoEscolhido = "Cartao";
                System.out.println("      Pagamento via Cartao    ");
                System.out.print("Digite o numero do cartao: ");
                sc.nextLine();
                System.out.print("Digite o CVV: ");
                sc.nextLine();
                break;
            default:
                System.out.println("Opcao invalida, definindo como PIX por padrao.");
                metodoEscolhido = "PIX";
        }

        System.out.println(" O pagamento foi efetivado com sucesso agora?");
        System.out.println("[1] Sim (Ficara ATIVO/PAGO)");
        System.out.println("[2] Nao (Ficara PENDENTE)");
        System.out.print("Opcao: ");
        int opcStatus = sc.nextInt();
        sc.nextLine();

        Pagamento.StatusPagamento statusDefinido = (opcStatus == 1) ? Pagamento.StatusPagamento.PAGO : Pagamento.StatusPagamento.PENDENTE;

        if (statusDefinido == Pagamento.StatusPagamento.PAGO) {
            System.out.println("  Pagamento efetuado!");
        } else {
            System.out.println("  Pagamento nao efetuado. Status ficara como PENDENTE.");
        }

        Pagamento novoPagamento = new Pagamento(null, alunoEncontrado, planoEncontrado, statusDefinido, metodoEscolhido);

        pagamentoRepositorio.salvar(novoPagamento);

        System.out.println("  Registro criado no sistema com sucesso!  ");
        System.out.println(
                " ID: " + novoPagamento.getId() +
                        " | Aluno: " + alunoEncontrado.getNome() +
                        " | Plano: " + planoEncontrado.getNome() +
                        " | Valor: R$ " + novoPagamento.getValor() +
                        " | Metodo: " + novoPagamento.getMetodoPagamento() +
                        " | Pagamento: " + novoPagamento.getDataPagamento() +
                        " | Vencimento: " + novoPagamento.getDataVencimento() +
                        " | Status: " + novoPagamento.getStatus()
        );
    }

    public void listarPagamento() {
        atualizarRegraSeisMeses();

        System.out.println("Listar Histórico de Pagamentos");
        List<Pagamento> pagamentos = pagamentoRepositorio.listarTodos();
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento foi Registrado ainda");
        } else {
            for (Pagamento p : pagamentos) {
                System.out.println(p);
            }
        }
    }

    private void atualizarRegraSeisMeses() {
        LocalDate hoje = LocalDate.now();
        for (Pagamento p : pagamentoRepositorio.listarTodos()) {
            if (p.getStatus() == Pagamento.StatusPagamento.PENDENTE) {
                if (p.getDataVencimento().plusMonths(6).isBefore(hoje)) {
                    p.setStatus(Pagamento.StatusPagamento.CANCELADO);
                    pagamentoRepositorio.atualizar(p);
                }
            }
        }
    }

    public void extratoDosPagamentos() {
        System.out.println("    EXTRATO DE PAGAMENTOS (RECEITAS)    ");
        double totalArrecadado = 0;

        for (Pagamento p : pagamentoRepositorio.listarTodos()) {
            if (p.getStatus() == Pagamento.StatusPagamento.PAGO) {
                totalArrecadado += p.getValor();
            }
        }
        System.out.println(" Valor total recebido (Status PAGO): R$ " + totalArrecadado);
    }

    public void removerPagamento() {
        System.out.print("  Digite o ID do pagamento que deseja remover: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (pagamentoRepositorio.remover(id)) {
            System.out.println(" Pagamento removido com sucesso!");
        } else {
            System.out.println(" Pagamento nao encontrado!");
        }
    }

    public void alterarStatusPagamento() {
        System.out.print("  Digite o ID do pagamento para alterar o status: ");
        int id = sc.nextInt();
        sc.nextLine();

        Pagamento encontrado = pagamentoRepositorio.buscarPorId(id);

        if (encontrado != null) {
            System.out.println("Status atual: " + encontrado.getStatus());
            System.out.println("Novo status: [1] PENDENTE  [2] PAGO  [3] CANCELADO");
            System.out.print("Opcao: ");
            int opc = sc.nextInt();
            sc.nextLine();

            boolean opcaoValida = true;

            switch (opc) {
                case 1 -> encontrado.setStatus(Pagamento.StatusPagamento.PENDENTE);
                case 2 -> encontrado.setStatus(Pagamento.StatusPagamento.PAGO);
                case 3 -> encontrado.setStatus(Pagamento.StatusPagamento.CANCELADO);
                default -> {
                    System.out.println("Informação inválida, Tente novamente!");
                    opcaoValida = false;
                }
            }

            if (opcaoValida) {
                pagamentoRepositorio.atualizar(encontrado);
                System.out.println("Pagamento Alterado Com Sucesso!");
            }

        } else {
            System.out.println("Informação inválida, Tente novamente!");
        }
    }
}