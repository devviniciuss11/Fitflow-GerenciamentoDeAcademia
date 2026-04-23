package Gui;

import Entidade.Aluno;
import Entidade.Funcionario;
import Entidade.Personal;
import Repositorio.AlunoRepositorio;
import Repositorio.FuncionarioRepositorio;
import Repositorio.PersonalRepositorio;
import Servico.AlunoServico;
import Servico.FitFlow;
import Servico.FuncionarioServico;
import Servico.PersonalServico;

import java.util.Scanner;

public class MenuInicial {

    private final Scanner sc = new Scanner(System.in);

    public void menuInicial() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("============= FITFLOW =============");
            System.out.println(" [1] - Login");
            System.out.println(" [2] - Cadastro");
            System.out.println(" [0] - Sair");
            System.out.println("==================================");
            opcao = lerOpcaoInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> menuLogin();
                case 2 -> menuCadastro();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void menuLogin() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("--------------- LOGIN --------------");
            System.out.println(" [1] - Login de Funcionário");
            System.out.println(" [2] - Login de Personal");
            System.out.println(" [3] - Login de Aluno");
            System.out.println(" [4] - Administração (ADM)");
            System.out.println(" [0] - Voltar");
            System.out.println("------------------------------------");
            opcao = lerOpcaoInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> loginFuncionario();
                case 2 -> loginPersonal();
                case 3 -> loginAluno();
                case 4 -> acessoAdm();
                case 0 -> { /* voltar */ }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void menuCadastro() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("-------------- CADASTRO --------------");
            System.out.println(" [1] - Cadastrar Funcionário");
            System.out.println(" [2] - Cadastrar Personal");
            System.out.println(" [3] - Cadastrar Aluno");
            System.out.println(" [0] - Voltar");
            System.out.println("--------------------------------------");
            opcao = lerOpcaoInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> new FuncionarioServico().cadastrarFuncionarios();
                case 2 -> new PersonalServico().cadastrarPersonal();
                case 3 -> new AlunoServico().cadastrarAluno();
                case 0 -> { /* voltar */ }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void loginFuncionario() {
        System.out.println("Login de Funcionário");
        String cpf = lerLinhaNaoVazia("CPF: ");
        String senha = lerLinhaNaoVazia("Senha: ");

        Funcionario encontrado = autenticarFuncionario(cpf, senha);
        if (encontrado == null) {
            System.out.println("Credenciais inválidas.");
            return;
        }

        System.out.println("Login realizado com sucesso! Bem-vindo(a), " + encontrado.getNome() + ".");
        new GuiFuncionario().menuf();
    }

    private void loginAluno() {
        System.out.println("Login de Aluno");
        String cpf = lerLinhaNaoVazia("CPF: ");
        String senha = lerLinhaNaoVazia("Senha: ");

        Aluno encontrado = autenticarAluno(cpf, senha);
        if (encontrado == null) {
            System.out.println("Credenciais inválidas.");
            return;
        }

        System.out.println("Login realizado com sucesso! Bem-vindo(a), " + encontrado.getNome() + ".");
        new GuiAluno().menu();
    }

    private void loginPersonal() {
        System.out.println("Login de Personal");
        System.out.println("Você pode informar CPF ou CRAF.");
        String identificador = lerLinhaNaoVazia("CPF/CRAF: ");
        String senha = lerLinhaNaoVazia("Senha: ");

        Personal encontrado = autenticarPersonalPorCpfOuCraf(identificador, senha);
        if (encontrado == null) {
            System.out.println("Credenciais inválidas.");
            return;
        }

        System.out.println("Login realizado com sucesso! Bem-vindo(a), " + encontrado.getNome() + ".");
        new GuiPersonal().menu();
    }

    // =========================
    // Autenticação (repositórios)
    // =========================

    private Funcionario autenticarFuncionario(String cpf, String senha) {
        for (Funcionario f : FuncionarioRepositorio.funcionarios) {
            if (cpf.equals(f.getCpf()) && senha.equals(f.getSenha())) {
                return f;
            }
        }
        return null;
    }

    private Aluno autenticarAluno(String cpf, String senha) {
        for (Aluno a : AlunoRepositorio.alunos) {
            if (cpf.equals(a.getCpf()) && senha.equals(a.getSenha())) {
                return a;
            }
        }
        return null;
    }

    private Personal autenticarPersonalPorCpfOuCraf(String cpfOuCraf, String senha) {
        for (Personal p : PersonalRepositorio.personais) {
            boolean idOk = cpfOuCraf.equals(p.getCpf()) || cpfOuCraf.equals(p.getCraf());
            if (idOk && senha.equals(p.getSenha())) {
                return p;
            }
        }
        return null;
    }

    // =========================
    // Leitura segura de entrada
    // =========================

    private int lerOpcaoInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = sc.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
            }
        }
    }

    private String lerLinhaNaoVazia(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = sc.nextLine().trim();
            if (!linha.isEmpty()) return linha;
            System.out.println("Campo obrigatório. Tente novamente.");
        }
    }
    
    public void acessoAdm() {
        System.out.println("Acesso Administrador");

        String senha = lerLinhaNaoVazia("Digite a senha do Administrador: ");

        while (!senha.equals("5564")) {
            System.out.println("Senha incorreta. Acesso negado!");
            senha = lerLinhaNaoVazia("Digite a senha do Administrador: ");
        }

        System.out.println("Acesso liberado! Bem-vindo(a), ADM.");
        FitFlow.main(new String[0]);
    }

    // Se você precisar rodar direto esta classe:
    public static void main(String[] args) {
        new MenuInicial().menuInicial();
    }
}
