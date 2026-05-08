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
    Scanner sc = new Scanner(System.in);
    FuncionarioRepositorio funcionariosRepositorio = new FuncionarioRepositorio();
    Instancia instancia = new Instancia();

    public static void mostrarDiasTrabalhados(Funcionario funcionario) {
        if (funcionario == null) {
            System.out.println("Funcionario nao encontrado!");
            return;
        }
        Set<LocalDate> diasTrabalhados = funcionario.getDiasTrabalhados();

        if (diasTrabalhados.isEmpty()) {
            System.out.println("O funcionario nao possui nenhuma presenca marcada!");
        } else {
            System.out.println("Historico de presencas do Funcionario: ");
            for (LocalDate data : diasTrabalhados) {
                System.out.println("Dias Presentes --- " + data);
            }
        }
    }

    public static void marcarPresenca(Funcionario funcionario) {
        if (funcionario == null) {
            System.out.println("Funcionario nao encontrado!!");
            return;
        }
        LocalDate hoje = LocalDate.now();
        Set<LocalDate> diasTrabalhados = funcionario.getDiasTrabalhados();
        if (!diasTrabalhados.add(hoje)) {
            System.out.println("O funcionario ja consta como presente nesse dia!");
        } else {
            System.out.println("Presenca marcada com sucesso! " + hoje);
        }
    }

    public void presencaFuncionario() {
        System.out.println("Digite o Cpf do Funcionario que deseja Marcar Presenca: ");
        String cpf = sc.nextLine().trim();

        Funcionario funcionarioPresente = funcionariosRepositorio.buscarPorCpf(cpf);

        if (funcionarioPresente == null) {
            System.out.println("Funcionario nao encontrado!!");
            return;
        }

        while (true) {
            System.out.println("Presenca do Funcionario: " + funcionarioPresente.getNome());
            System.out.println("==========================================================");
            System.out.println("Qual opcao deseja executar: ");
            System.out.println("1 - Ver historico de presenca do Funcionario.");
            System.out.println("2 - Marcar Presenca do Funcionario.");
            System.out.println("3 - Voltar.");

            int opc;
            try {
                opc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas Numeros: ");
                continue;
            }
            switch (opc) {
                case 1:
                    mostrarDiasTrabalhados(funcionarioPresente);
                    break;
                case 2:
                    marcarPresenca(funcionarioPresente);
                    funcionarioPresente = funcionariosRepositorio.atualizar(funcionarioPresente);
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

        System.out.println("Digite o nome do Funcionario: ");
        String nome = sc.nextLine();

        System.out.println("Digite o Cpf do Funcionario: ");
        String cpf = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        while (true) {
            System.out.println("Digite a Data de Nascimento do Funcionario: (Formato dd/MM/yyyy): ");
            String dataNacimento = sc.nextLine().trim();
            try {
                datadeNascimento = LocalDate.parse(dataNacimento, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Data ou Formato invalido. Exemplo: 02/08/2007");
                continue;
            }

            int idade = Period.between(datadeNascimento, LocalDate.now()).getYears();

            if (idade < 0) {
                System.out.println("A Idade esta errada!! Tente novamente.");
                continue;
            }
            if (idade > 120) {
                System.out.println("A idade esta errada (Maior que 120 anos). Tente novamente");
                continue;
            }
            if (idade <= 17) {
                System.out.println("Funcionarios precisam ter mais de 18 anos. Tente novamente");
                continue;
            }
            System.out.println("Idade Cadastrada com sucesso!!");
            break;
        }

        System.out.println("Digite seu Email: ");
        String email = sc.nextLine();

        System.out.println("Digite seu telefone: ");
        String telefone = sc.nextLine();

        System.out.println("Crie uma senha: ");
        String senha = sc.nextLine();

        System.out.println("Qual a Faixa Salario do Funcionario: ");
        Double salario = sc.nextDouble();
        sc.nextLine();

        System.out.println("Qual o Cargo do Funcionario: ");
        String cargo = sc.nextLine();

        System.out.println("Qual o Horario de trabalho: ");
        Double horario = sc.nextDouble();
        sc.nextLine();

        Endereco endereco = lerEndereco();

        Funcionario funcionario = new Funcionario(
                null, nome, cpf, datadeNascimento, email, telefone, senha,
                cargo, salario, horario,
                endereco
        );
        funcionariosRepositorio.Save(funcionario);
        instancia.adicionar();
    }

    public void ListarFuncionarios() {
        List<Funcionario> funcionarios = funcionariosRepositorio.listarTodos();
        if (funcionarios.isEmpty()) {
            System.out.println("Nao ha Funcionarios Cadastrados!!");
        } else {
            for (Funcionario f : funcionarios) {
                System.out.println(f);
            }
        }
    }

    public void RemoverFuncionario() {
        System.out.println("Aba de Remocao de Funcionarios!!");
        System.out.println("Digite o CPF do Funcionario que deseja Remover");
        String buscaCpf = sc.nextLine();
        boolean removido = funcionariosRepositorio.removerPorCpf(buscaCpf);

        if (removido) {
            System.out.println("Funcionario com CPF: " + buscaCpf + " Removido");
        } else {
            System.out.println("Nenhum Funcionario com o Cpf: " + buscaCpf + " Encontrado!");
        }
    }

    public void AlterarFuncionario() {
        System.out.println("Pagina de Alteracao de dados do Funcionario!!");
        System.out.println("Digite o Cpf do funcionario");
        String cpf = sc.nextLine();

        Funcionario funcionario = funcionariosRepositorio.buscarPorCpf(cpf);
        if (funcionario == null) {
            System.out.println("Funcionario nao encontrado. Voltando ao menuInicial.");
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

            int opc = sc.nextInt();
            sc.nextLine();
            switch (opc) {
                case 1:
                    System.out.println("Digite o novo nome: ");
                    funcionario.setNome(sc.nextLine());
                    funcionario = funcionariosRepositorio.atualizar(funcionario);
                    instancia.alterar();
                    break;
                case 2:
                    System.out.println("Digite o novo CPF: ");
                    funcionario.setCpf(sc.nextLine());
                    funcionario = funcionariosRepositorio.atualizar(funcionario);
                    instancia.alterar();
                    break;
                case 3:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                    while (true) {
                        System.out.println("Digite a nova data de nascimento (dd/MM/aaaa): ");
                        String dataNascimento = sc.nextLine().trim();
                        try {
                            LocalDate dataNascimentoAlterada = LocalDate.parse(dataNascimento, formatter);
                            funcionario.setDataNascimento(dataNascimentoAlterada);
                            funcionario = funcionariosRepositorio.atualizar(funcionario);
                            instancia.alterar();
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Voce nao digitou a data no formato certo. Tente novamente (exemplo: 02/08/2007).");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Digite o novo email: ");
                    funcionario.setEmail(sc.nextLine());
                    funcionario = funcionariosRepositorio.atualizar(funcionario);
                    instancia.alterar();
                    break;
                case 5:
                    System.out.println("Digite o novo numero: ");
                    funcionario.setTelefone(sc.nextLine());
                    funcionario = funcionariosRepositorio.atualizar(funcionario);
                    instancia.alterar();
                    break;
                case 6:
                    System.out.println("Digite a nova senha: ");
                    funcionario.setSenha(sc.nextLine());
                    funcionario = funcionariosRepositorio.atualizar(funcionario);
                    instancia.alterar();
                    break;
                case 7:
                    System.out.println("Digite o novo cargo ocupado pelo Funcionario: ");
                    funcionario.setCargo(sc.nextLine());
                    funcionario = funcionariosRepositorio.atualizar(funcionario);
                    instancia.alterar();
                    break;
                case 8:
                    System.out.println("Digite o novo salario do Funcionario: ");
                    Double novoSalario = sc.nextDouble();
                    sc.nextLine();
                    funcionario.setSalario(novoSalario);
                    funcionario = funcionariosRepositorio.atualizar(funcionario);
                    instancia.alterar();
                    break;
                case 9:
                    System.out.println("Digite o novo Horario de trabalho do Funcionario: ");
                    Double novoHorario = sc.nextDouble();
                    sc.nextLine();
                    funcionario.setHorarioTrabalho(novoHorario);
                    funcionario = funcionariosRepositorio.atualizar(funcionario);
                    instancia.alterar();
                    break;
                case 10:
                    funcionario.setEndereco(lerEndereco());
                    funcionario = funcionariosRepositorio.atualizar(funcionario);
                    instancia.alterar();
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

    private Endereco lerEndereco() {
        System.out.println("Digite o CEP: ");
        String cep = sc.nextLine();

        System.out.println("Digite o Bairro: ");
        String bairro = sc.nextLine();

        System.out.println("Digite o Nome da Rua: ");
        String nomeRua = sc.nextLine();

        System.out.println("Digite o Complemento: ");
        String complemento = sc.nextLine();

        int numCasa;
        while (true) {
            System.out.println("Digite o Numero da Casa: ");
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
