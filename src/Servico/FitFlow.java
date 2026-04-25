package Servico;

import Gui.*;
import Repositorio.TreinoRepositorio;

import java.util.Scanner;

public class FitFlow {
    public void main() {
        Scanner sc = new Scanner(System.in);
        int opc2 = 1;
        while (opc2 == 1) {
            System.out.println("[1] - Gerenciar Alunos");
            System.out.println("[2] - Gerenciar Personais");
            System.out.println("[3] - Gerenciar Treinos");
            System.out.println("[4] - Gerenciar Planos");
            System.out.println("[5] - Gerenciar Pagamentos");
            System.out.println("[6] - Gerenciar Desempenho");
            System.out.println("[7] - Gerenciar Funcionarios");
            System.out.println("[8] - Voltar");
            int opc = sc.nextInt();
            switch (opc) {
                case 1:
                    GuiAluno guiAluno = new GuiAluno();
                    guiAluno.menuDoAlunoAdm();
                    break;
                case 2:
                    GuiPersonal guiPersonal = new GuiPersonal();
                    guiPersonal.menuPAdm();
                    break;
                case 3:
                    TreinoRepositorio repositorio = new TreinoRepositorio();
                    TreinoServico treinoServico = new TreinoServico(repositorio);
                    GuiTreino guiTreino = new GuiTreino(treinoServico);
                    guiTreino.menuTreino();
                    break;
                case 4:
                    PlanoServico servico = new PlanoServico();
                    GuiPlano gui = new GuiPlano(servico);
                    gui.menuPlano();
                    break;
                case 5:
                    GuiPagamento guiPagamento = new GuiPagamento();
                    guiPagamento.menuPagamento();
                    break;
                case 6:
                    GuiDesempenho guiDesempenho = new GuiDesempenho();
                    guiDesempenho.menu();
                    break;

                case 7 :
                    GuiFuncionario guiFuncionario = new GuiFuncionario();
                    guiFuncionario.menufAdm();
                case 8:
                    System.out.println("Saindo.....");
                    opc2 = 0;
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }
}
