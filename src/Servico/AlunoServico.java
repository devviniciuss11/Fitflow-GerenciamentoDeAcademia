package Servico;

import Entidade.Aluno;
import Interfacess.Instancia;
import Repositorio.AlunoRepositorio;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


import static Entidade.Aluno.diasTreino;
import static Repositorio.AlunoRepositorio.alunos;

public class AlunoServico {
    Scanner sc = new Scanner(System.in);
    AlunoRepositorio alunoRepositorio = new AlunoRepositorio();
    Instancia instancia = new Instancia();

    public static void marcarPresenca(){
        LocalDate hoje = LocalDate.now();
        if(diasTreino.contains(hoje)){
            System.out.println("voce ja marcou presenca nesse dia");
        }else{
            diasTreino.add(hoje);
            System.out.println("presenca marcada com sucesso "+ hoje);
        }
    }
    public static void mostrarHistorico(){
        if(diasTreino.isEmpty()){
            System.out.println("Nenhuma presenca marcada");
        }else{
        System.out.println("Historico de presenca: ");
        for(LocalDate data:diasTreino){
            System.out.println("Presente - "+data);
        }
        }
    }
    public int GeradorId() {
        {
            Set<Integer> idsUsados = new HashSet<>();
            Random random = new Random();
            int novoId;
            do {
                novoId = random.nextInt(10000);
            } while (idsUsados.contains(novoId));
            idsUsados.add(novoId);
            return novoId;
        }
    }

    public void cadastrarAluno() {
        LocalDate dataNascimento = LocalDate.now();
        System.out.println("Cadastrar Aluno");
        int id = GeradorId();

        System.out.println("Nome: ");
        String nome = sc.nextLine();

        System.out.println("CPF: ");
        String cpf = sc.nextLine();

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
                System.out.println("Idade está errada (data no futuro). Tente novamente.");
                continue;
            }

            if (idade > 120) {
                System.out.println("Idade está errada (maior que 120 anos). Verifique a data e tente novamente.");
                continue;
            }

            if (idade <= 10) {
                System.out.println("Aluno com " + idade + " anos: é obrigatório informar um responsável.");

                String nomeResponsavel;
                do {
                    System.out.println("Nome do responsável: ");
                    nomeResponsavel = sc.nextLine().trim();
                    if (nomeResponsavel.isEmpty()) {
                        System.out.println("Nome do responsável não pode ser vazio.");
                    }
                } while (nomeResponsavel.isEmpty());

                String cpfResponsavel;
                do {
                    System.out.println("CPF do responsável: ");
                    cpfResponsavel = sc.nextLine().trim();
                    if (cpfResponsavel.isEmpty()) {
                        System.out.println("CPF do responsável não pode ser vazio.");
                    }
                } while (cpfResponsavel.isEmpty());

                String telefoneResponsavel;
                do {
                    System.out.println("Telefone do responsável: ");
                    telefoneResponsavel = sc.nextLine().trim();
                    if (telefoneResponsavel.isEmpty()) {
                        System.out.println("Telefone do responsável não pode ser vazio.");
                    }
                } while (telefoneResponsavel.isEmpty());

                System.out.println("Responsável registrado para validação: " + nomeResponsavel + " (CPF: " + cpfResponsavel + ")");
            }

            break;
        }

        System.out.println("Email: ");
        String email = sc.nextLine();

        System.out.println("Telefone: ");
        String telefone = sc.nextLine();

        System.out.println("Senha: ");
        String senha = sc.nextLine();

        Aluno aluno = new Aluno(id, nome, cpf, dataNascimento, email, telefone, senha);
        alunoRepositorio.save(aluno);
        instancia.adicionar();
    }

    public void MostrarAlunos(){
        if(alunos.isEmpty()){
            System.out.println("Nenhum aluno cadastrado ainda !!!");
        }
        for (Aluno a : alunos) {
            System.out.println("Dados do Aluno(a)" + a.getNome());
            System.out.println(a);
        }
    }

    public void excluirAluno() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Qual Aluno você deseja excluir do cadastro? Digite o CPF:");
        String cpf = sc1.nextLine();
        Aluno exluirAluno = null;
        for (Aluno aluno : alunos) {
            if (aluno.getCpf().equals(cpf)) {
                exluirAluno = aluno;

            }
        }
        if (exluirAluno != null) {
            System.out.println("Procurando Aluno....");
            alunos.remove(exluirAluno);
            System.out.println("O Aluno foi excluído com sucesso.\n");
        } else {
            System.out.println("Aluno não encontrado. Tente novamente!\n");
        }


    }

    public void alteraAluno() {
        System.out.println("Alterar Dados Do Aluno");
        System.out.println("Digite o CPF do Aluno que deseja alterar: ");
        String cpf = sc.nextLine();
        Aluno aluno = null;
        for (Aluno aluno1 : alunos) {
            if (aluno1.getCpf().equals(cpf)) {
                aluno = aluno1;
                System.out.println("Aluno "+aluno.getNome()+" encontrado com sucesso.");
                break;
            }
        }
        if (aluno == null) {
            System.out.println("Aluno nao foi encontrado. Voltando para o menu...");
            return;
        }
        System.out.println("Escolha o que voce quer alterar do aluno(a):" + aluno.getNome());
        char opcao = 's';
        while (opcao != 'n') {
            System.out.println("1- Alterar Nome");
            System.out.println("2- Alterar CPF");
            System.out.println("3- Alterar Data de Nascimento");
            System.out.println("4- Alterar Email");
            System.out.println("5- Alterar Telefone");
            System.out.println("6- Alterar Senha");
            System.out.println("7- Sair");
            Scanner sc1 = new Scanner(System.in);
            int opc= sc1.nextInt();
            switch (opc) {
                case 1:
                    System.out.println("Digite o novo nome: ");
                    String novoNome = sc.nextLine();
                    aluno.setNome(novoNome);
                    instancia.alterar();
                    break;
                case 2:
                    System.out.println("Digite o novo CPF: ");
                    String NovoCpf = sc.nextLine();
                    aluno.setCpf(NovoCpf);
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
                            instancia.alterar();
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Voce nao digitou no formato certo. Tente novamente (exemplo: 24/09/2006).");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Digite o novo email: ");
                    String NovoEmail = sc.nextLine();
                    aluno.setEmail(NovoEmail);
                    instancia.alterar();
                    break;
                case 5:
                    System.out.println("Digite o novo telefone: ");
                    String NovoTelefone = sc.nextLine();
                    aluno.setTelefone(NovoTelefone);
                    instancia.alterar();
                    break;
                case 6:
                    System.out.println("Digite a nova senha: ");
                    String NovaSenha = sc.nextLine();
                    aluno.setSenha(NovaSenha);
                    instancia.alterar();
                    break;
                case 7:
                    opcao = 'n';
                    break;
            }
        }

    }


}
