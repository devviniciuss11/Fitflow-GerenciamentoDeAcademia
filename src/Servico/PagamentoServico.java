package Servico;

import Entidade.Aluno;
import Entidade.Pagamento;
import Entidade.Plano;
import Repositorio.AlunoRepositorio;
import Repositorio.PagamentoRepositorio;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class PagamentoServico {

    Scanner sc = new Scanner(System.in);
    PagamentoRepositorio pagamentoRepositorio = new PagamentoRepositorio();
    PlanoServico planoServico = new PlanoServico();

    public void realizarPagamento() {
        System.out.println("      REALIZAR PAGAMENTO      ");
        System.out.println(" Digite o CPF do Aluno: ");
        String cpfAluno = sc.nextLine();

        Aluno alunoEncontrado = null;
        for (Aluno a : AlunoRepositorio.alunos) {
            if (a.getCpf().equals(cpfAluno)){
                alunoEncontrado = a;
                break;
            }
        }

        if(alunoEncontrado == null) {
            System.out.println(" O Aluno não foi encontrado. Por favor, verifique o CPF. ");
            return;
        }

        System.out.println("    PLANOS DISPONÍVEIS    ");
        for (Plano p : planoServico.listarPlano()) {
            System.out.println(p.getId() + " - " + p.getNome() + " (R$ " + p.getValor() + ")");
        }

        System.out.println("Digite o ID do Plano que o aluno vai pagar: ");
        int idPlano = sc.nextInt();
        sc.nextLine();

        Plano planoEncontrado = planoServico.buscarPorId(idPlano);

        if (planoEncontrado == null) {
            System.out.println("  PLANO NÃO ENCONTRADO  ");
            return;
        }

        System.out.println("  Escolha o método de pagamento: ");
        System.out.println(" [1] - PIX");
        System.out.println(" [2] - Boleto");
        System.out.println(" [3] - Cartão de Crédito/Débito");
        System.out.print(" Opção: ");
        int opcMetodo = sc.nextInt();
        sc.nextLine();

        String metodoEscolhido = "";
        switch (opcMetodo) {
            case 1:
                metodoEscolhido = "PIX";
                System.out.println("    PAGAMENTO VIA PIX    ");
                System.out.println("Chave PIX: 123456");
                break;
            case 2:
                metodoEscolhido = "Boleto";
                System.out.println("    Pagamento via Boleto    ");
                System.out.println("Código de Barras: 123456");
                break;
            case 3:
                metodoEscolhido = "Cartão";
                System.out.println("      Pagamento via Cartão    ");
                System.out.print("Digite o número do cartão: ");
                sc.nextLine();
                System.out.print("Digite o CVV: ");
                sc.nextLine();
                break;
            default:
                System.out.println("Opção inválida, definindo como PIX por padrão.");
                metodoEscolhido = "PIX";
        }

        System.out.println(" O pagamento foi efetivado com sucesso agora?");
        System.out.println("[1] Sim (Ficará ATIVO/PAGO)");
        System.out.println("[2] Não (Ficará PENDENTE)");
        System.out.print("Opção: ");
        int opcStatus = sc.nextInt();
        sc.nextLine();

        Pagamento.StatusPagamento statusDefinido = (opcStatus == 1) ? Pagamento.StatusPagamento.PAGO : Pagamento.StatusPagamento.PENDENTE;

        if(statusDefinido == Pagamento.StatusPagamento.PAGO) {
            System.out.println("  Pagamento efetuado!");
        } else {
            System.out.println("  Pagamento não efetuado. Status ficará como PENDENTE.");
        }

        int idPagamento = new Random().nextInt(10000);

        Pagamento novoPagamento = new Pagamento(idPagamento, alunoEncontrado, planoEncontrado, statusDefinido, metodoEscolhido);

        pagamentoRepositorio.salvar(novoPagamento);

        System.out.println("  Registro criado no sistema com sucesso!  ");
        System.out.println(novoPagamento);
    }

    public void listarPagamento(){
        atualizarRegraSeisMeses();

        System.out.println("    HISTÓRICO DE PAGAMENTOS    ");
        if (pagamentoRepositorio.listarTodos().isEmpty()){
            System.out.println("Nenhum pagamento foi registrado ainda.");
        }else {
            for (Pagamento p : pagamentoRepositorio.listarTodos()) {
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
            System.out.println(" Pagamento não encontrado!");
        }
    }

    public void alterarStatusPagamento() {
        System.out.print("  Digite o ID do pagamento para alterar o status: ");
        int id = sc.nextInt();
        sc.nextLine();

        Pagamento encontrado = null;
        for (Pagamento p : pagamentoRepositorio.listarTodos()) {
            if (p.getId() == id) {
                encontrado = p;
                break;
            }
        }

        if (encontrado != null) {
            System.out.println("Status atual: " + encontrado.getStatus());
            System.out.println("Novo status: [1] PENDENTE  [2] PAGO  [3] CANCELADO");
            System.out.print("Opção: ");
            int opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1 -> encontrado.setStatus(Pagamento.StatusPagamento.PENDENTE);
                case 2 -> encontrado.setStatus(Pagamento.StatusPagamento.PAGO);
                case 3 -> encontrado.setStatus(Pagamento.StatusPagamento.CANCELADO);
                default -> System.out.println("Opção inválida.");
            }
            System.out.println("  Status atualizado!");
        } else {
            System.out.println("  Pagamento não encontrado.");
        }
    }
}
