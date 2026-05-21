package Servico;

import Entidade.Aluno;
import Entidade.Endereco;
import Entidade.Personal;
import Repositorio.AlunoRepositorio;
import Repositorio.PersonalRepositorio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PersonalServico {

    Scanner sc = new Scanner(System.in);
    PersonalRepositorio personalRepositorio = new PersonalRepositorio();
    AlunoRepositorio alunoRepositorio = new AlunoRepositorio();

    public void vincularAlunoAoPersonal() {
        System.out.println("Vincular Aluno ao Personal");
        System.out.println("Digite o CRAF do Personal");

        String crafBusca = sc.nextLine();

        Personal personalEncontrado = personalRepositorio.buscarPorCraf(crafBusca);

        if (personalEncontrado == null) {
            System.out.println("Personal nao encontrado");
            return;
        }

        System.out.println("Digite o CPF do Aluno que sera treinado pelo Personal:");
        String cpfAluno = sc.nextLine();

        Aluno alunoEncontrado = alunoRepositorio.buscarPorCpf(cpfAluno);

        if (alunoEncontrado == null) {
            System.out.println("Aluno nao encontrado na base de dados! Verifique o CPF.");
            return;
        }

        boolean vinculoCriado = personalEncontrado.adicionarAluno(alunoEncontrado.getNome());

        if (!vinculoCriado) {
            System.out.println("Este aluno ja esta vinculado a este personal.");
            return;
        }

        try {
            personalRepositorio.atualizar(personalEncontrado);
            System.out.println("Sucesso! O Aluno " + alunoEncontrado.getNome() + " agora treina com o Personal " + personalEncontrado.getNome() + ".");
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel concluir o vinculo. Este aluno ja pode estar vinculado a este personal.");
        }
    }

    public void listarAlunosDoPersonal() {
        System.out.println("Ver alunos de um Personal");
        System.out.println("Digitar o CRAF do Personal:");
        String crafBusca = sc.nextLine();

        Personal personalEncontrado = personalRepositorio.buscarPorCraf(crafBusca);
        if (personalEncontrado != null) {
            if (personalEncontrado.getAlunosdele().isEmpty()) {
                System.out.println("Este Personal ainda nao possui alunos vinculados.");
            } else {
                System.out.println("Alunos do Personal " + personalEncontrado.getNome() + ":");
                for (String nomeAluno : personalEncontrado.getAlunosdele()) {
                    System.out.println(" - " + nomeAluno);
                }
            }
        } else {
            System.out.println("Personal nao encontrado!");
        }
    }

    public void cadastrarPersonal() {
        System.out.println("CADASTRO DO PERSONAL!");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.println("Cpf:");
        String cpf = sc.nextLine();

        System.out.println("Data de Nascimento (dd/mm/aaaa): ");
        LocalDate dataNascimento;
        DateTimeFormatter nascimentoFmt = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        while (true) {
            String entrada = sc.nextLine().trim();
            try {
                dataNascimento = LocalDate.parse(entrada, nascimentoFmt);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida. Use o formato dd/mm/aaaa (ex: 12/09/2000). Tente novamente:");
            }
        }

        System.out.println("Email: ");
        String email = sc.nextLine();

        System.out.println("Telefone: ");
        String telefone = sc.nextLine();

        System.out.println("Senha: ");
        String senha = sc.nextLine();

        String craf;
        while (true) {
            System.out.print("CRAF: ");
            craf = sc.nextLine().trim();
            if (craf.isEmpty()) {
                System.out.println("Campos vazios, tente novamente!");
            } else {
                break;
            }
        }

        double salario = 0;
        boolean salarioValido = false;

        while (!salarioValido) {
            try {
                System.out.print("Salario (R$): ");
                salario = sc.nextDouble();
                salarioValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas numeros para o salario!");
                sc.nextLine();
            }
        }

        double horas = 0;
        boolean horasValidas = false;

        while (!horasValidas) {
            try {
                System.out.print("Horas do expediente do Personal: ");
                horas = sc.nextDouble();
                horasValidas = true;
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas numeros para as horas!");
                sc.nextLine();
            }
        }

        sc.nextLine();

        Endereco endereco = lerEndereco();

        Personal novo = new Personal(
                null, nome, cpf, dataNascimento, email, telefone, senha,
                craf, salario, horas, new ArrayList<>(),
                endereco
        );
        personalRepositorio.save(novo);

        System.out.println("Personal cadastrado com sucesso!");
    }

    public void listarPersonais() {
        List<Personal> personais = personalRepositorio.listarTodos();
        if (personais.isEmpty()) {
            System.out.println("Personal não encontrado!");
        } else {
            System.out.println("Personal encontrado com Sucesso!");
            for (Personal p : personais) {
                System.out.println(p);
            }
        }
    }

    public void excluirPersonal() {
        System.out.println("Remover Personal");
        System.out.println("Digite o CRAF do Personal que deseja excluir:");

        String crafBusca = sc.nextLine();

        boolean removido = personalRepositorio.removerPorCraf(crafBusca);

        if (removido) {
            System.out.println("Personal com CRAF: " + crafBusca + " removido");
        } else {
            System.out.println("Nenhum Personal encontrado");
        }
    }

    public void alterarPersonal() {
        System.out.println("Alterar dados do Personal");
        System.out.println("Digite o CRAF do personal: ");
        String crafBusca = sc.nextLine();

        Personal encontrado = personalRepositorio.buscarPorCraf(crafBusca);

        if (encontrado != null) {
            System.out.println("Personal encontrado: " + encontrado.getNome());
            System.out.println("1- Alterar nome");
            System.out.println("2- Alterar salario");
            System.out.println("3- Alterar endereco");
            System.out.println("Escolha uma opcao");
            int op = sc.nextInt();
            sc.nextLine();

            if (op == 1) {
                System.out.println("Novo nome: ");
                encontrado.setNome(sc.nextLine());
                personalRepositorio.atualizar(encontrado);
                System.out.println("Dados do personal atualizados com sucesso");

            } else if (op == 2) {
                System.out.println("Novo salario: ");
                encontrado.setSalario(sc.nextDouble());
                sc.nextLine();
                personalRepositorio.atualizar(encontrado);
                System.out.println("Dados do personal atualizados com sucesso");

            } else if (op == 3) {
                encontrado.setEndereco(lerEndereco());
                personalRepositorio.atualizar(encontrado);
                System.out.println("Dados do personal atualizados com sucesso");

            } else {
                System.out.println("Opcao invalida");
            }
        } else {
            System.out.println("Personal não encontrado");
        }
    }

    public Personal buscarPorIdPer(int id) {
        return personalRepositorio.buscarPorIdper(id);
    }

    private Endereco lerEndereco() {
        System.out.println("CEP: ");
        String cep = sc.nextLine();

        System.out.println("Bairro: ");
        String bairro = sc.nextLine();

        System.out.println("Nome da Rua: ");
        String nomeRua = sc.nextLine();

        System.out.println("Complemento: ");
        String complemento = sc.nextLine();

        int numCasa;
        while (true) {
            System.out.println("Numero da Casa: ");
            String entrada = sc.nextLine().trim();
            try {
                numCasa = Integer.parseInt(entrada);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros para o numero da casa!");
            }
        }

        return new Endereco(cep, bairro, nomeRua, complemento, numCasa);
    }
}
