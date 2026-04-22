package Servico;

import Entidade.Aluno;
import Entidade.Pagamento;
import Entidade.Plano;
import Repositorio.AlunoRepositorio;
import Repositorio.PagamentoRepositorio;

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

        int idPagamento = new Random().nextInt(10000);
        Pagamento novoPagamento = new Pagamento(idPagamento, alunoEncontrado, planoEncontrado, Pagamento.StatusPagamento.PAGO);

        pagamentoRepositorio.salvar(novoPagamento);

        System.out.println("O Pagamento foi aprovado e registrado com sucesso!");
        System.out.println(novoPagamento);
    }

    public void listarPagamento(){
        System.out.println("    HISTÓRICO DE PAGAMENTOS    ");
        if (pagamentoRepositorio.listarTodos().isEmpty()){
            System.out.println("Nenhum pagamento foi registrado ainda.");
        }else {
            for (Pagamento p : pagamentoRepositorio.listarTodos()) {
                System.out.println(p);
            }
        }
    }
}

