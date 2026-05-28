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

    private static final String MSG_NENHUM_PLANO_SELECIONADO = "Nenhum Plano Selecionado. Tente Novamente!";
    private static final String MSG_OPCAO_INVALIDA = "Informação inválida, Tente novamente!";
    private static final String MSG_PAGAMENTO_ALTERADO = "Pagamento Alterado Com Sucesso!";
    private static final String MSG_PAGAMENTO_REMOVIDO = "Pagamento removido com Sucesso!";
    private static final String MSG_PAGAMENTO_NAO_ENCONTRADO = "Pagamento não Encontrado";

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

        Integer idPlano = lerInteiroOpcional("Digite o ID do Plano que o aluno vai pagar: ");
        if (idPlano == null) {
            System.out.println(MSG_NENHUM_PLANO_SELECIONADO);
            return;
        }

        Plano planoEncontrado = planoServico.buscarPorId(idPlano);

        if (planoEncontrado == null) {
            System.out.println(MSG_NENHUM_PLANO_SELECIONADO);
            return;
        }

        System.out.println("Otima Escolha!");

        System.out.println("  Escolha o metodo de pagamento: ");
        System.out.println(" [1] - PIX");
        System.out.println(" [2] - Boleto");
        System.out.println(" [3] - Cartao de Credito/Debito");
        int opcMetodo = lerInteiro(" Opcao: ");

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
                System.out.println(MSG_OPCAO_INVALIDA);
                return;
        }

        System.out.println(" O pagamento foi efetivado com sucesso agora?");
        System.out.println("[1] Sim (Ficara ATIVO/PAGO)");
        System.out.println("[2] Nao (Ficara PENDENTE)");
        int opcStatus = lerInteiro("Opcao: ");

        Pagamento.StatusPagamento statusDefinido = (opcStatus == 1)
                ? Pagamento.StatusPagamento.PAGO
                : Pagamento.StatusPagamento.PENDENTE;

        if (statusDefinido == Pagamento.StatusPagamento.PAGO) {
            System.out.println("  Pagamento efetuado!");
        } else {
            System.out.println("  Pagamento nao efetuado. Status ficara como PENDENTE.");
        }

        Pagamento novoPagamento = new Pagamento(null, alunoEncontrado, planoEncontrado, statusDefinido, metodoEscolhido);

        pagamentoRepositorio.salvar(novoPagamento);
        vincularPlanoSePagamentoPago(novoPagamento);

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
        int id = lerInteiro("  Digite o ID do pagamento que deseja remover: ");
        Pagamento pagamentoParaRemover = pagamentoRepositorio.buscarPorId(id);
        if (pagamentoParaRemover == null) {
            System.out.println(MSG_PAGAMENTO_NAO_ENCONTRADO);
            return;
        }

        if (pagamentoRepositorio.remover(id)) {
            sincronizarVinculoPlanoPosRemocao(pagamentoParaRemover);
            System.out.println(MSG_PAGAMENTO_REMOVIDO);
        } else {
            System.out.println(MSG_PAGAMENTO_NAO_ENCONTRADO);
        }
    }

    public void alterarStatusPagamento() {
        int id = lerInteiro("  Digite o ID do pagamento para alterar o status: ");

        Pagamento encontrado = pagamentoRepositorio.buscarPorId(id);

        if (encontrado != null) {
            Pagamento.StatusPagamento statusAnterior = encontrado.getStatus();
            System.out.println("Status atual: " + encontrado.getStatus());
            System.out.println("Novo status: [1] PENDENTE  [2] PAGO  [3] CANCELADO");
            int opc = lerInteiro("Opcao: ");

            boolean opcaoValida = true;

            switch (opc) {
                case 1 -> encontrado.setStatus(Pagamento.StatusPagamento.PENDENTE);
                case 2 -> encontrado.setStatus(Pagamento.StatusPagamento.PAGO);
                case 3 -> encontrado.setStatus(Pagamento.StatusPagamento.CANCELADO);
                default -> {
                    System.out.println(MSG_OPCAO_INVALIDA);
                    opcaoValida = false;
                }
            }

            if (opcaoValida) {
                pagamentoRepositorio.atualizar(encontrado);
                sincronizarVinculoPlanoPosAlteracaoStatus(encontrado, statusAnterior);
                System.out.println(MSG_PAGAMENTO_ALTERADO);
            }

        } else {
            System.out.println(MSG_OPCAO_INVALIDA);
        }
    }

    private Integer lerInteiroOpcional(String prompt) {
        System.out.print(prompt);
        String entrada = sc.nextLine().trim();

        if (entrada.isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return null;
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

    private void vincularPlanoSePagamentoPago(Pagamento pagamento) {
        if (pagamento == null) return;
        if (pagamento.getStatus() != Pagamento.StatusPagamento.PAGO) return;
        if (pagamento.getAluno() == null || pagamento.getPlano() == null) return;
        if (pagamento.getAluno().getId() == null || pagamento.getPlano().getId() == null) return;

        alunoRepositorio.vincularPlanoAoAlunoSeAindaNaoExiste(
                pagamento.getAluno().getId(),
                pagamento.getPlano().getId()
        );
    }

    private void sincronizarVinculoPlanoPosAlteracaoStatus(Pagamento pagamento, Pagamento.StatusPagamento statusAnterior) {
        if (pagamento == null) return;
        if (pagamento.getAluno() == null || pagamento.getPlano() == null) return;
        if (pagamento.getAluno().getId() == null || pagamento.getPlano().getId() == null) return;

        int alunoId = pagamento.getAluno().getId();
        int planoId = pagamento.getPlano().getId();

        if (pagamento.getStatus() == Pagamento.StatusPagamento.PAGO) {
            alunoRepositorio.vincularPlanoAoAlunoSeAindaNaoExiste(alunoId, planoId);
            return;
        }

        if (statusAnterior == Pagamento.StatusPagamento.PAGO) {
            boolean existeOutroPagamentoPago = pagamentoRepositorio.existePagamentoPagoParaAlunoEPlanoExcetoId(
                    alunoId,
                    planoId,
                    pagamento.getId()
            );

            if (!existeOutroPagamentoPago) {
                alunoRepositorio.desvincularPlanoAoAlunoSeExistir(alunoId, planoId);
            }
        }
    }

    private void sincronizarVinculoPlanoPosRemocao(Pagamento pagamentoRemovido) {
        if (pagamentoRemovido == null) return;
        if (pagamentoRemovido.getStatus() != Pagamento.StatusPagamento.PAGO) return;
        if (pagamentoRemovido.getAluno() == null || pagamentoRemovido.getPlano() == null) return;
        if (pagamentoRemovido.getAluno().getId() == null || pagamentoRemovido.getPlano().getId() == null) return;

        int alunoId = pagamentoRemovido.getAluno().getId();
        int planoId = pagamentoRemovido.getPlano().getId();

        boolean existeOutroPagamentoPago = pagamentoRepositorio.existePagamentoPagoParaAlunoEPlanoExcetoId(
                alunoId,
                planoId,
                pagamentoRemovido.getId()
        );

        if (!existeOutroPagamentoPago) {
            alunoRepositorio.desvincularPlanoAoAlunoSeExistir(alunoId, planoId);
        }
    }
}
