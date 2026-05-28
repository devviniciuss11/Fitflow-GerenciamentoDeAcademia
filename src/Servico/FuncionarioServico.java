package Servico;

import Entidade.Endereco;
import Entidade.Funcionario;
import Interfacess.Instancia;
import Repositorio.FuncionarioRepositorio;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FuncionarioServico {
    private static final String MSG_CAMPOS_VAZIOS = "Campos vazios. Tente novamente!";
    private static final String MSG_CPF_DUPLICADO = "Cpf ja cadastrado. Tente novamente com outro CPF.";
    private static final String MSG_EMAIL_DUPLICADO = "Email ja cadastrado. Tente novamente com outro email.";
    private static final String MSG_FUNCIONARIO_NAO_ENCONTRADO = "Nenhum funcionario com o CPF informado foi encontrado.";

    private final Scanner sc = new Scanner(System.in);
    private final FuncionarioRepositorio funcionariosRepositorio = new FuncionarioRepositorio();
    private final Instancia instancia = new Instancia();

    public static void mostrarDiasTrabalhados(Funcionario funcionario) {
        if (funcionario == null) {
            System.out.println("Funcionario nao encontrado");
            return;
        }
        Set<LocalDate> diasTrabalhados = funcionario.getDiasTrabalhados();

        if (diasTrabalhados.isEmpty()) {
            System.out.println("O funcionario nao possui nenhuma presenca marcada!");
        } else {
            System.out.println("Historico de presencas do Funcionario:");
            for (LocalDate data : diasTrabalhados) {
                System.out.println("Dias Presentes --- " + data);
            }
        }
    }

    public static void marcarPresenca(Funcionario funcionario) {
        if (funcionario == null) {
            System.out.println("Funcionario nao encontrado");
            return;
        }
        LocalDate hoje = LocalDate.now();
        Set<LocalDate> diasTrabalhados = funcionario.getDiasTrabalhados();
        if (!diasTrabalhados.add(hoje)) {
            System.out.println("Presenca ja marcada hoje");
        } else {
            System.out.println("Check-in do funcionario realizado com sucesso!");
        }
    }

    public void presencaFuncionario() {
        System.out.println("Digite o CPF do Funcionario que deseja marcar presenca:");
        String cpf = sc.nextLine().trim();

        Funcionario funcionarioPresente = funcionariosRepositorio.buscarPorCpf(cpf);

        if (funcionarioPresente == null) {
            System.out.println("Funcionario nao encontrado");
            return;
        }

        while (true) {
            System.out.println("Presenca do Funcionario: " + funcionarioPresente.getNome());
            System.out.println("==========================================================");
            System.out.println("Qual opcao deseja executar:");
            System.out.println("1 - Ver historico de presenca do Funcionario.");
            System.out.println("2 - Marcar presenca do Funcionario.");
            System.out.println("3 - Voltar.");

            int opc = lerInteiro("Opcao: ");
            switch (opc) {
                case 1:
                    mostrarDiasTrabalhados(funcionarioPresente);
                    break;
                case 2:
                    marcarPresenca(funcionarioPresente);
                    funcionarioPresente = atualizarFuncionarioComTratamento(funcionarioPresente);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    public void cadastrarFuncionarios() {
        LocalDate datadeNascimento = LocalDate.now();
        System.out.println("Area de Cadastro de Funcionario!!!");

        System.out.println("Digite o nome do Funcionario:");
        String nome = sc.nextLine();

        System.out.println("Digite o CPF do Funcionario:");
        String cpf = sc.nextLine().trim();
        if (funcionariosRepositorio.existePorCpf(cpf)) {
            System.out.println(MSG_CPF_DUPLICADO);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        while (true) {
            System.out.println("Digite a Data de Nascimento do Funcionario (Formato dd/MM/yyyy):");
            String dataNacimento = sc.nextLine().trim();
            try {
                datadeNascimento = LocalDate.parse(dataNacimento, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Data ou formato invalido. Exemplo: 02/08/2007");
                continue;
            }

            int idade = Period.between(datadeNascimento, LocalDate.now()).getYears();

            if (idade < 0) {
                System.out.println("A idade esta errada! Tente novamente.");
                continue;
            }
            if (idade > 120) {
                System.out.println("A idade esta errada (maior que 120 anos). Tente novamente");
                continue;
            }
            if (idade <= 17) {
                System.out.println("Funcionarios precisam ter mais de 18 anos. Tente novamente");
                continue;
            }
            break;
        }

        System.out.println("Digite seu Email:");
        String email = sc.nextLine().trim();
        if (funcionariosRepositorio.existePorEmail(email)) {
            System.out.println(MSG_EMAIL_DUPLICADO);
            return;
        }

        System.out.println("Digite seu telefone:");
        String telefone = sc.nextLine();

        System.out.println("Crie uma senha:");
        String senha = sc.nextLine();

        Double salario = lerDouble("Qual a faixa salarial do Funcionario: ");

        System.out.println("Qual o cargo do Funcionario:");
        String cargo = sc.nextLine();

        Double horario = lerDouble("Qual o horario de trabalho: ");

        Endereco endereco = lerEndereco();

        Funcionario funcionario = new Funcionario(
                null, nome, cpf, datadeNascimento, email, telefone, senha,
                cargo, salario, horario,
                endereco
        );

        try {
            funcionariosRepositorio.Save(funcionario);
            instancia.adicionar();
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel cadastrar o funcionario. Verifique CPF/email e tente novamente.");
        }
    }

    public void ListarFuncionarios() {
        try {
            List<Funcionario> funcionarios = funcionariosRepositorio.listarTodos();
            if (funcionarios.isEmpty()) {
                System.out.println("Nao ha funcionarios cadastrados!");
            } else {
                for (Funcionario f : funcionarios) {
                    System.out.println(f);
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel listar os funcionarios agora.");
        }
    }

    public void RemoverFuncionario() {
        System.out.println("Aba de remocao de Funcionarios!!");
        System.out.println("Digite o CPF do Funcionario que deseja remover");
        String buscaCpf = sc.nextLine().trim();

        int escolha = lerInteiro("Deseja realmente remover este funcionario? (1 para Sim, qualquer outro numero para cancelar): ");

        if (escolha == 1) {
            try {
                Funcionario funcionario = funcionariosRepositorio.buscarPorCpf(buscaCpf);
                if (funcionario == null) {
                    System.out.println(MSG_FUNCIONARIO_NAO_ENCONTRADO);
                    return;
                }

                boolean removido = funcionariosRepositorio.removerPorCpf(buscaCpf);
                if (removido) {
                    System.out.println("Funcionario com o CPF: " + buscaCpf + " removido com sucesso!");
                } else {
                    System.out.println(MSG_FUNCIONARIO_NAO_ENCONTRADO);
                }
            } catch (RuntimeException e) {
                System.out.println("Nao foi possivel remover o funcionario agora.");
            }
        } else {
            System.out.println("Remocao cancelada");
        }
    }

    public void AlterarFuncionario() {
        System.out.println("Pagina de alteracao de dados do Funcionario!!");
        System.out.println("Digite o CPF do funcionario");
        String cpf = sc.nextLine();

        Funcionario funcionario = funcionariosRepositorio.buscarPorCpf(cpf);
        if (funcionario == null) {
            System.out.println("Funcionario nao encontrado");
            return;
        }

        System.out.println("Funcionario " + funcionario.getNome() + " encontrado com sucesso.");

        char opcao = '2';
        while (opcao != '1') {
            System.out.println("1- Alterar Nome");
            System.out.println("2- Alterar CPF");
            System.out.println("3- Alterar Data de Nascimento");
            System.out.println("4- Alterar Email");
            System.out.println("5- Alterar Telefone");
            System.out.println("6- Alterar Senha");
            System.out.println("7- Alterar Cargo");
            System.out.println("8- Alterar Salario");
            System.out.println("9- Alterar Horario de Trabalho");
            System.out.println("10- Alterar Endereco");
            System.out.println("11- Sair");

            int opc = lerInteiro("Opcao: ");
            switch (opc) {
                case 1:
                    System.out.println("Digite o novo nome:");
                    funcionario.setNome(sc.nextLine());
                    funcionario = atualizarFuncionarioComTratamento(funcionario);
                    break;
                case 2:
                    System.out.println("Digite o novo CPF:");
                    String novoCpf = sc.nextLine().trim();
                    if (novoCpf.isEmpty()) {
                        System.out.println(MSG_CAMPOS_VAZIOS);
                        break;
                    }
                    if (funcionariosRepositorio.existePorCpfExcetoId(novoCpf, funcionario.getId())) {
                        System.out.println(MSG_CPF_DUPLICADO);
                        break;
                    }
                    funcionario.setCpf(novoCpf);
                    funcionario = atualizarFuncionarioComTratamento(funcionario);
                    break;
                case 3:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                    while (true) {
                        System.out.println("Digite a nova data de nascimento (dd/MM/aaaa):");
                        String dataNascimento = sc.nextLine().trim();
                        try {
                            LocalDate dataNascimentoAlterada = LocalDate.parse(dataNascimento, formatter);
                            funcionario.setDataNascimento(dataNascimentoAlterada);
                            funcionario = atualizarFuncionarioComTratamento(funcionario);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato invalido. Tente novamente (exemplo: 02/08/2007).");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Digite o novo email:");
                    String novoEmail = sc.nextLine().trim();
                    if (novoEmail.isEmpty()) {
                        System.out.println(MSG_CAMPOS_VAZIOS);
                        break;
                    }
                    if (funcionariosRepositorio.existePorEmailExcetoId(novoEmail, funcionario.getId())) {
                        System.out.println(MSG_EMAIL_DUPLICADO);
                        break;
                    }
                    funcionario.setEmail(novoEmail);
                    funcionario = atualizarFuncionarioComTratamento(funcionario);
                    break;
                case 5:
                    System.out.println("Digite o novo numero:");
                    funcionario.setTelefone(sc.nextLine());
                    funcionario = atualizarFuncionarioComTratamento(funcionario);
                    break;
                case 6:
                    System.out.println("Digite a nova senha:");
                    funcionario.setSenha(sc.nextLine());
                    funcionario = atualizarFuncionarioComTratamento(funcionario);
                    break;
                case 7:
                    System.out.println("Digite o novo cargo ocupado pelo Funcionario:");
                    funcionario.setCargo(sc.nextLine());
                    funcionario = atualizarFuncionarioComTratamento(funcionario);
                    break;
                case 8:
                    Double novoSalario = lerDouble("Digite o novo salario do Funcionario: ");
                    funcionario.setSalario(novoSalario);
                    funcionario = atualizarFuncionarioComTratamento(funcionario);
                    break;
                case 9:
                    Double novoHorario = lerDouble("Digite o novo horario de trabalho do Funcionario: ");
                    funcionario.setHorarioTrabalho(novoHorario);
                    funcionario = atualizarFuncionarioComTratamento(funcionario);
                    break;
                case 10:
                    funcionario.setEndereco(lerEndereco());
                    funcionario = atualizarFuncionarioComTratamento(funcionario);
                    System.out.println("Endereco atualizado.");
                    break;
                case 11:
                    opcao = '1';
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private Funcionario atualizarFuncionarioComTratamento(Funcionario funcionario) {
        try {
            Funcionario atualizado = funcionariosRepositorio.atualizar(funcionario);
            instancia.alterar();
            return atualizado;
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel salvar a alteracao. Verifique os dados e tente novamente.");
            return funcionario;
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

    private Double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = sc.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros.");
            }
        }
    }

    private Endereco lerEndereco() {
        System.out.println("Digite o CEP:");
        String cep = sc.nextLine();

        System.out.println("Digite o Bairro:");
        String bairro = sc.nextLine();

        System.out.println("Digite o Nome da Rua:");
        String nomeRua = sc.nextLine();

        System.out.println("Digite o Complemento:");
        String complemento = sc.nextLine();

        int numCasa;
        while (true) {
            System.out.println("Digite o Numero da Casa:");
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
