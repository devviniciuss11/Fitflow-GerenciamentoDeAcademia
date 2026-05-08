package Servico;

import Entidade.Aluno;
import Entidade.Endereco;
import Interfacess.Instancia;
import Repositorio.AlunoRepositorio;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AlunoServico {
    Scanner sc = new Scanner(System.in);
    AlunoRepositorio alunoRepositorio = new AlunoRepositorio();
    Instancia instancia = new Instancia();

    public static void marcarPresenca(Aluno aluno) {
        if (aluno == null) {
            System.out.println("Aluno invalido.");
            return;
        }

        LocalDate hoje = LocalDate.now();
        Set<LocalDate> diasTreino = aluno.getDiasTreino();

        if (!diasTreino.add(hoje)) {
            System.out.println("voce ja marcou presenca nesse dia");
        } else {
            System.out.println("presenca marcada com sucesso " + hoje);
        }
    }

    public static void mostrarHistorico(Aluno aluno) {
        if (aluno == null) {
            System.out.println("Aluno invalido.");
            return;
        }

        Set<LocalDate> diasTreino = aluno.getDiasTreino();

        if (diasTreino.isEmpty()) {
            System.out.println("Nenhuma presenca marcada");
        } else {
            System.out.println("Historico de presenca: ");
            for (LocalDate data : diasTreino) {
                System.out.println("Presente - " + data);
            }
        }
    }

    public void presencaAluno() {
        System.out.println("Digite o CPF do aluno para marcar/ver presenca:");
        String cpf = sc.nextLine().trim();

        Aluno alunoEncontrado = alunoRepositorio.buscarPorCpf(cpf);

        if (alunoEncontrado == null) {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        while (true) {
            System.out.println("----- PRESENCA DO ALUNO: " + alunoEncontrado.getNome() + " -----");
            System.out.println("1 - Marcar presenca hoje");
            System.out.println("2 - Ver historico de presenca");
            System.out.println("3 - Voltar");
            System.out.print("Escolha: ");

            int opc;
            try {
                opc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros.");
                continue;
            }
            switch (opc) {
                case 1:
                    marcarPresenca(alunoEncontrado);
                    alunoEncontrado = alunoRepositorio.atualizar(alunoEncontrado);
                    break;
                case 2:
                    mostrarHistorico(alunoEncontrado);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    public void cadastrarAluno() {
        LocalDate dataNascimento = LocalDate.now();
        System.out.println("Cadastrar Aluno");

        System.out.println("Nome: ");
        String nome = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        while (true) {
            System.out.println("Digite a Data de Nascimento do Aluno (formato dd/MM/yyyy): ");
            String dataNasimento = sc.nextLine().trim();

            try {
                dataNascimento = LocalDate.parse(dataNasimento, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida ou formato errado. Exemplo: 24/09/2006");
                continue;
            }

            int idade = Period.between(dataNascimento, LocalDate.now()).getYears();

            if (idade < 0) {
                System.out.println("Idade esta errada (data no futuro). Tente novamente.");
                continue;
            }

            if (idade > 120) {
                System.out.println("Idade esta errada (maior que 120 anos). Verifique a data e tente novamente.");
                continue;
            }

            if (idade >= 0 && idade < 5) {
                System.out.println("A academia nao da suporte a criancas nessa idade");
                continue;
            }

            if (idade <= 10 && idade >= 5) {
                System.out.println("Aluno com " + idade + " anos: e obrigatorio informar os dados do responsavel.");

                System.out.println("Cpf Do Responsavel: ");
                String cpf = sc.nextLine();

                if (alunoRepositorio.existePorCpf(cpf)) {
                    System.out.println("Cpf ja cadastrado. Tente novamente com outro CPF.");
                    return;
                }

                System.out.println("Email Do Responsavel: ");
                String email = sc.nextLine();

                System.out.println("Telefone Do Responsavel: ");
                String telefone = sc.nextLine();

                System.out.println("Senha Do Responsavel: ");
                String senha = sc.nextLine();

                Endereco endereco = lerEndereco("Do Responsavel");

                ArrayList<Entidade.Treino> fichaDeTreino = new ArrayList<>();
                ArrayList<Entidade.Plano> alunoPlano = new ArrayList<>();

                Aluno aluno = new Aluno(
                        null, nome, cpf, dataNascimento, email, telefone, senha,
                        alunoPlano, fichaDeTreino,
                        endereco
                );
                alunoRepositorio.save(aluno);
                instancia.adicionar();
                return;
            }
            break;
        }

        System.out.println("CPF: ");
        String cpf = sc.nextLine();
        if (alunoRepositorio.existePorCpf(cpf)) {
            System.out.println("Cpf ja cadastrado. Tente novamente com outro CPF.");
            return;
        }

        System.out.println("Email: ");
        String email = sc.nextLine();

        System.out.println("Telefone: ");
        String telefone = sc.nextLine();

        System.out.println("Senha: ");
        String senha = sc.nextLine();

        Endereco endereco = lerEndereco("");

        ArrayList<Entidade.Treino> fichaDeTreino = new ArrayList<>();
        ArrayList<Entidade.Plano> alunoPlano = new ArrayList<>();

        Aluno aluno = new Aluno(
                null, nome, cpf, dataNascimento, email, telefone, senha,
                alunoPlano, fichaDeTreino,
                endereco
        );
        alunoRepositorio.save(aluno);
        instancia.adicionar();
    }

    public void MostrarAlunos() {
        List<Aluno> alunos = alunoRepositorio.listarTodos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado ainda !!!");
            return;
        }
        for (Aluno a : alunos) {
            System.out.println("Dados do Aluno(a): " + a.getNome());
            System.out.println(a);
        }
    }

    public void excluirAluno() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Qual Aluno voce deseja excluir do cadastro? Digite o CPF:");
        String cpf = sc1.nextLine();

        if (alunoRepositorio.removerPorCpf(cpf)) {
            System.out.println("Procurando Aluno....");
            System.out.println("O Aluno foi excluido com sucesso.\n");
        } else {
            System.out.println("Aluno nao encontrado. Tente novamente!\n");
        }
    }

    public void alteraAluno() {
        System.out.println("Alterar Dados Do Aluno");
        System.out.println("Digite o CPF do Aluno que deseja alterar: ");
        String cpf = sc.nextLine();

        Aluno aluno = alunoRepositorio.buscarPorCpf(cpf);
        if (aluno == null) {
            System.out.println("Aluno nao foi encontrado. Voltando para o menuInicial...");
            return;
        }

        System.out.println("Aluno " + aluno.getNome() + " encontrado com sucesso.");

        char opcao = 's';
        while (opcao != 'n') {
            System.out.println("1- Alterar Nome");
            System.out.println("2- Alterar CPF");
            System.out.println("3- Alterar Data de Nascimento");
            System.out.println("4- Alterar Email");
            System.out.println("5- Alterar Telefone");
            System.out.println("6- Alterar Senha");
            System.out.println("7- Alterar Endereco");
            System.out.println("8- Sair");

            int opc;
            try {
                opc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros.");
                continue;
            }

            switch (opc) {
                case 1:
                    System.out.println("Digite o novo nome: ");
                    aluno.setNome(sc.nextLine());
                    aluno = alunoRepositorio.atualizar(aluno);
                    instancia.alterar();
                    break;
                case 2:
                    System.out.println("Digite o novo CPF: ");
                    aluno.setCpf(sc.nextLine());
                    aluno = alunoRepositorio.atualizar(aluno);
                    instancia.alterar();
                    break;
                case 3:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                    while (true) {
                        System.out.println("Digite a nova data de nascimento (dd/MM/aaaa): ");
                        String dataNascimento = sc.nextLine().trim();
                        try {
                            LocalDate dataNascimentoFormatada = LocalDate.parse(dataNascimento, formatter);
                            aluno.setDataNascimento(dataNascimentoFormatada);
                            aluno = alunoRepositorio.atualizar(aluno);
                            instancia.alterar();
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Voce nao digitou no formato certo. Tente novamente (exemplo: 24/09/2006).");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Digite o novo email: ");
                    aluno.setEmail(sc.nextLine());
                    aluno = alunoRepositorio.atualizar(aluno);
                    instancia.alterar();
                    break;
                case 5:
                    System.out.println("Digite o novo telefone: ");
                    aluno.setTelefone(sc.nextLine());
                    aluno = alunoRepositorio.atualizar(aluno);
                    instancia.alterar();
                    break;
                case 6:
                    System.out.println("Digite a nova senha: ");
                    aluno.setSenha(sc.nextLine());
                    aluno = alunoRepositorio.atualizar(aluno);
                    instancia.alterar();
                    break;
                case 7:
                    aluno.setEndereco(lerEndereco(""));
                    aluno = alunoRepositorio.atualizar(aluno);
                    instancia.alterar();
                    System.out.println("Endereco atualizado.");
                    break;
                case 8:
                    opcao = 'n';
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    public Aluno buscarPorId(int id) {
        return alunoRepositorio.buscarPorId(id);
    }

    private Endereco lerEndereco(String sufixo) {
        String label = sufixo == null || sufixo.isBlank() ? "" : " " + sufixo;

        System.out.println("CEP" + label + ": ");
        String cep = sc.nextLine();

        System.out.println("Bairro" + label + ": ");
        String bairro = sc.nextLine();

        System.out.println("Nome da Rua" + label + ": ");
        String nomeRua = sc.nextLine();

        System.out.println("Complemento" + label + ": ");
        String complemento = sc.nextLine();

        int numCasa;
        while (true) {
            System.out.println("Numero da Casa" + label + ": ");
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
