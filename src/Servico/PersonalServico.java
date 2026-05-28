package Servico;

import Entidade.Aluno;
import Entidade.Endereco;
import Entidade.Personal;
import Repositorio.AlunoRepositorio;
import Repositorio.PersonalRepositorio;
import Repositorio.TreinoRepositorio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonalServico {

    private static final String MSG_CAMPOS_VAZIOS = "Campos vazios. Tente novamente!";
    private static final String MSG_CPF_DUPLICADO = "Cpf ja cadastrado. Tente novamente com outro CPF.";
    private static final String MSG_EMAIL_DUPLICADO = "Email ja cadastrado. Tente novamente com outro email.";
    private static final String MSG_CRAF_DUPLICADO = "CRAF ja cadastrado. Tente novamente com outro CRAF.";
    private static final String MSG_PERSONAL_COM_TREINO = "Nao e possivel remover o personal pois existem treinos vinculados a ele.";
    private static final String MSG_PERSONAL_NAO_ENCONTRADO = "Personal nao encontrado.";

    private final Scanner sc = new Scanner(System.in);
    private final PersonalRepositorio personalRepositorio = new PersonalRepositorio();
    private final AlunoRepositorio alunoRepositorio = new AlunoRepositorio();
    private final TreinoRepositorio treinoRepositorio = new TreinoRepositorio();

    public void vincularAlunoAoPersonal() {
        System.out.println("Vincular Aluno ao Personal");
        System.out.println("Digite o CRAF do Personal");

        String crafBusca = sc.nextLine();

        Personal personalEncontrado = personalRepositorio.buscarPorCraf(crafBusca);

        if (personalEncontrado == null) {
            System.out.println("Personal nao encontrado.");
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
            System.out.println("Sucesso! O aluno " + alunoEncontrado.getNome() + " agora treina com o personal " + personalEncontrado.getNome());
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel concluir o vinculo. Verifique os dados e tente novamente.");
        }
    }

    public void listarAlunosDoPersonal() {
        System.out.println("Ver alunos de um Personal");
        System.out.println("Digite o CRAF do Personal:");
        String crafBusca = sc.nextLine();

        Personal personalEncontrado = personalRepositorio.buscarPorCraf(crafBusca);
        if (personalEncontrado != null) {
            if (personalEncontrado.getAlunosdele().isEmpty()) {
                System.out.println("Este personal ainda nao possui alunos vinculados.");
            } else {
                System.out.println("Alunos do Personal \"" + personalEncontrado.getNome() + "\":");
                for (String nomeAluno : personalEncontrado.getAlunosdele()) {
                    System.out.println(" - " + nomeAluno);
                }
            }
        } else {
            System.out.println("Personal nao encontrado.");
        }
    }

    public void cadastrarPersonal() {
        System.out.println("CADASTRO DO PERSONAL!");

        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();

        System.out.println("CPF:");
        String cpf = sc.nextLine().trim();

        System.out.println("Data de Nascimento (dd/MM/aaaa):");
        LocalDate dataNascimento;
        DateTimeFormatter nascimentoFmt = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        while (true) {
            String entrada = sc.nextLine().trim();
            try {
                dataNascimento = LocalDate.parse(entrada, nascimentoFmt);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida. Use o formato dd/MM/aaaa (ex: 12/09/2000). Tente novamente:");
            }
        }

        System.out.println("Email:");
        String email = sc.nextLine().trim();

        System.out.println("Telefone:");
        String telefone = sc.nextLine().trim();

        System.out.println("Senha:");
        String senha = sc.nextLine().trim();

        String craf;
        while (true) {
            System.out.print("CRAF: ");
            craf = sc.nextLine().trim();
            if (craf.isEmpty()) {
                System.out.println(MSG_CAMPOS_VAZIOS);
            } else {
                break;
            }
        }

        if (personalRepositorio.existePorCpf(cpf)) {
            System.out.println(MSG_CPF_DUPLICADO);
            return;
        }
        if (personalRepositorio.existePorEmail(email)) {
            System.out.println(MSG_EMAIL_DUPLICADO);
            return;
        }
        if (personalRepositorio.existePorCraf(craf)) {
            System.out.println(MSG_CRAF_DUPLICADO);
            return;
        }

        double salario = lerDouble("Salario (R$): ");
        double horas = lerDouble("Horas do expediente do Personal: ");

        Endereco endereco = lerEndereco();

        Personal novo = new Personal(
                null, nome, cpf, dataNascimento, email, telefone, senha,
                craf, salario, horas, new ArrayList<>(),
                endereco
        );

        try {
            personalRepositorio.save(novo);
            System.out.println("Personal cadastrado com sucesso!!");
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel cadastrar o personal. Verifique CPF, email e CRAF e tente novamente.");
        }
    }

    public void listarPersonais() {
        try {
            List<Personal> personais = personalRepositorio.listarTodos();
            if (personais.isEmpty()) {
                System.out.println("Personal nao encontrado!");
            } else {
                System.out.println("Personal encontrado com sucesso!");
                for (Personal p : personais) {
                    System.out.println(p);
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel listar os personais agora.");
        }
    }

    public void excluirPersonal() {
        System.out.println("Remover Personal");
        System.out.println("Digite o CRAF do Personal que deseja excluir:");

        String crafBusca = sc.nextLine().trim();

        try {
            Personal personal = personalRepositorio.buscarPorCraf(crafBusca);
            if (personal == null) {
                System.out.println(MSG_PERSONAL_NAO_ENCONTRADO);
                return;
            }

            if (treinoRepositorio.existePorPersonalId(personal.getId())) {
                System.out.println(MSG_PERSONAL_COM_TREINO);
                return;
            }

            boolean removido = personalRepositorio.removerPorCraf(crafBusca);
            if (removido) {
                System.out.println("Personal com CRAF: " + crafBusca + " removido");
            } else {
                System.out.println(MSG_PERSONAL_NAO_ENCONTRADO);
            }
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel remover o personal agora.");
        }
    }

    public void alterarPersonal() {
        System.out.println("Alterar dados do Personal");
        System.out.println("Digite o CRAF do personal:");
        String crafBusca = sc.nextLine();

        Personal encontrado = personalRepositorio.buscarPorCraf(crafBusca);

        if (encontrado != null) {
            System.out.println("Personal encontrado: " + encontrado.getNome());
            System.out.println("1- Alterar nome");
            System.out.println("2- Alterar salario");
            System.out.println("3- Alterar endereco");
            System.out.println("4- Alterar CPF");
            System.out.println("5- Alterar email");
            System.out.println("6- Alterar CRAF");
            int op = lerInteiro("Escolha uma opcao: ");

            if (op == 1) {
                System.out.println("Novo nome:");
                encontrado.setNome(sc.nextLine());
                if (atualizarPersonalComTratamento(encontrado)) {
                    System.out.println("Dados do Personal atualizados com sucesso!");
                }
            } else if (op == 2) {
                encontrado.setSalario(lerDouble("Novo salario: "));
                if (atualizarPersonalComTratamento(encontrado)) {
                    System.out.println("Dados do Personal atualizados com sucesso!");
                }
            } else if (op == 3) {
                encontrado.setEndereco(lerEndereco());
                if (atualizarPersonalComTratamento(encontrado)) {
                    System.out.println("Dados do Personal atualizados com sucesso!");
                }
            } else if (op == 4) {
                String novoCpf = lerLinhaObrigatoria("Novo CPF: ");
                if (personalRepositorio.existePorCpfExcetoId(novoCpf, encontrado.getId())) {
                    System.out.println(MSG_CPF_DUPLICADO);
                    return;
                }
                encontrado.setCpf(novoCpf);
                if (atualizarPersonalComTratamento(encontrado)) {
                    System.out.println("Dados do Personal atualizados com sucesso!");
                }
            } else if (op == 5) {
                String novoEmail = lerLinhaObrigatoria("Novo email: ");
                if (personalRepositorio.existePorEmailExcetoId(novoEmail, encontrado.getId())) {
                    System.out.println(MSG_EMAIL_DUPLICADO);
                    return;
                }
                encontrado.setEmail(novoEmail);
                if (atualizarPersonalComTratamento(encontrado)) {
                    System.out.println("Dados do Personal atualizados com sucesso!");
                }
            } else if (op == 6) {
                String novoCraf = lerLinhaObrigatoria("Novo CRAF: ");
                if (personalRepositorio.existePorCrafExcetoId(novoCraf, encontrado.getId())) {
                    System.out.println(MSG_CRAF_DUPLICADO);
                    return;
                }
                encontrado.setCraf(novoCraf);
                if (atualizarPersonalComTratamento(encontrado)) {
                    System.out.println("Dados do Personal atualizados com sucesso!");
                }
            } else {
                System.out.println("Opcao invalida");
            }
        } else {
            System.out.println("Personal nao encontrado");
        }
    }

    public Personal buscarPorIdPer(int id) {
        return personalRepositorio.buscarPorIdper(id);
    }

    private boolean atualizarPersonalComTratamento(Personal personal) {
        try {
            personalRepositorio.atualizar(personal);
            return true;
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel salvar as alteracoes. Verifique os dados e tente novamente.");
            return false;
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

    private double lerDouble(String prompt) {
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

    private String lerLinhaObrigatoria(String prompt) {
        while (true) {
            System.out.print(prompt);
            String valor = sc.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println(MSG_CAMPOS_VAZIOS);
        }
    }

    private Endereco lerEndereco() {
        System.out.println("CEP:");
        String cep = sc.nextLine();

        System.out.println("Bairro:");
        String bairro = sc.nextLine();

        System.out.println("Nome da Rua:");
        String nomeRua = sc.nextLine();

        System.out.println("Complemento:");
        String complemento = sc.nextLine();

        int numCasa;
        while (true) {
            System.out.println("Numero da Casa:");
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
