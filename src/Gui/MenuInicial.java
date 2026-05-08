package Gui;

import Entidade.AcessoAdm;
import Entidade.Aluno;
import Entidade.Funcionario;
import Entidade.Personal;
import Infra.HibernateUtil;
import Repositorio.AlunoRepositorio;
import Repositorio.FuncionarioRepositorio;
import Repositorio.PersonalRepositorio;
import Servico.FitFlow;

import java.util.Scanner;

public class MenuInicial {
    FitFlow fitFlow = new FitFlow();

    private final Scanner sc = new Scanner(System.in);
    private final FuncionarioRepositorio funcionarioRepositorio = new FuncionarioRepositorio();
    private final AlunoRepositorio alunoRepositorio = new AlunoRepositorio();
    private final PersonalRepositorio personalRepositorio = new PersonalRepositorio();

    public void menuInicial() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("Bem-vindo ao Sistema FitFlow!!");
            System.out.println("Escolha Uma Das Opcoes Abaixo");
            System.out.println("============= FITFLOW =============");
            System.out.println(" [1] - Login");
            System.out.println(" [0] - Sair");
            System.out.println("==================================");
            opcao = lerOpcaoInt("Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> menuLogin();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opcao invalida!");
            }
        }
    }

    private void menuLogin() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("--------------- LOGIN --------------");
            System.out.println(" [1] - Login de Funcionario");
            System.out.println(" [2] - Login de Personal");
            System.out.println(" [3] - Login de Aluno");
            System.out.println(" [4] - Administracao (ADM)");
            System.out.println(" [0] - Voltar");
            System.out.println("------------------------------------");
            opcao = lerOpcaoInt("Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> loginFuncionario();
                case 2 -> loginPersonal();
                case 3 -> loginAluno();
                case 4 -> acessoAdm();
                case 0 -> {
                }
                default -> System.out.println("Opcao invalida!");
            }
        }
    }

    private void loginFuncionario() {
        System.out.println("Login de Funcionario");
        String cpf = lerLinhaNaoVazia("CPF: ");
        String senha = lerLinhaNaoVazia("Senha: ");

        Funcionario encontrado = autenticarFuncionario(cpf, senha);
        if (encontrado == null) {
            System.out.println("Credenciais invalidas.");
            return;
        }

        System.out.println("Login realizado com sucesso! Bem-vindo(a), " + encontrado.getNome() + ".");
        new GuiFuncionario().meunuFuncionarios();
    }

    private void loginAluno() {
        System.out.println("Login de Aluno");
        String cpf = lerLinhaNaoVazia("CPF: ");
        String senha = lerLinhaNaoVazia("Senha: ");

        Aluno encontrado = autenticarAluno(cpf, senha);
        if (encontrado == null) {
            System.out.println("Credenciais invalidas.");
            return;
        }

        System.out.println("Login realizado com sucesso! Bem-vindo(a), " + encontrado.getNome() + ".");
        new GuiAluno().menuDoAluno();
    }

    private void loginPersonal() {
        System.out.println("Login de Personal");
        System.out.println("Voce pode informar CPF ou CRAF.");
        String identificador = lerLinhaNaoVazia("CPF/CRAF: ");
        String senha = lerLinhaNaoVazia("Senha: ");

        Personal encontrado = autenticarPersonalPorCpfOuCraf(identificador, senha);
        if (encontrado == null) {
            System.out.println("Credenciais invalidas.");
            return;
        }

        System.out.println("Login realizado com sucesso! Bem-vindo(a), " + encontrado.getNome() + ".");
        new GuiPersonal().menuPersoal();
    }

    private Funcionario autenticarFuncionario(String cpf, String senha) {
        return funcionarioRepositorio.autenticar(cpf, senha);
    }

    private Aluno autenticarAluno(String cpf, String senha) {
        return alunoRepositorio.autenticar(cpf, senha);
    }

    private Personal autenticarPersonalPorCpfOuCraf(String cpfOuCraf, String senha) {
        return personalRepositorio.autenticarPorCpfOuCraf(cpfOuCraf, senha);
    }

    private int lerOpcaoInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = sc.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros.");
            }
        }
    }

    private String lerLinhaNaoVazia(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = sc.nextLine().trim();
            if (!linha.isEmpty()) return linha;
            System.out.println("Campo obrigatorio. Tente novamente.");
        }
    }

    public void acessoAdm() {
        AcessoAdm acessoAdm = AcessoAdm.ADM_MASTER;
        System.out.println("Acesso AcessoAdm");

        String senha = lerLinhaNaoVazia("Digite a senha do AcessoAdm: ");

        while (!senha.equals(acessoAdm.getSenha())) {
            System.out.println("Opção Inválida");
            senha = lerLinhaNaoVazia("Digite a senha do AcessoAdm: ");
        }
        String email = lerLinhaNaoVazia("Digite o email do AcessoAdm: ");
        while (!email.equals(acessoAdm.getEmail())) {
            System.out.println("Opção Inválida");
            email = lerLinhaNaoVazia("Digite a email do AcessoAdm: ");
        }
        System.out.println("Acesso liberado! Bem-vindo(a), ADM.");
        fitFlow.main();
    }

    public static void main(String[] args) {
        try {
            new MenuInicial().menuInicial();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
