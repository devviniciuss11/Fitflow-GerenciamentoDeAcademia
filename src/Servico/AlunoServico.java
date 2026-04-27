package Servico;

import Entidade.Aluno;
import Entidade.Endereco;
import Interfacess.Instancia;
import Repositorio.AlunoRepositorio;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static Repositorio.AlunoRepositorio.alunos;

public class AlunoServico {
    Scanner sc = new Scanner(System.in);
    AlunoRepositorio alunoRepositorio = new AlunoRepositorio();
    Instancia instancia = new Instancia();


    public static void marcarPresenca(Aluno aluno){
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

    public static void mostrarHistorico(Aluno aluno){
        if (aluno == null) {
            System.out.println("Aluno invalido.");
            return;
        }

        Set<LocalDate> diasTreino = aluno.getDiasTreino();

        if(diasTreino.isEmpty()){
            System.out.println("Nenhuma presenca marcada");
        }else{
            System.out.println("Historico de presenca: ");
            for(LocalDate data : diasTreino){
                System.out.println("Presente - " + data);
            }
        }
    }

    public void presencaAluno() {
        System.out.println("Digite o CPF do aluno para marcar/ver presenca:");
        String cpf = sc.nextLine().trim();

        Aluno alunoEncontrado = null;
        for (Aluno a : alunos) {
            if (a.getCpf() != null && a.getCpf().equals(cpf)) {
                alunoEncontrado = a;
                break;
            }
        }

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
            menuPresenca:
            switch (opc) {
                case 1:
                    marcarPresenca(alunoEncontrado);
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

            if (idade >=0 && idade < 5 ){
                System.out.println("A academia nao da Suporte a Crianças nessa idade");
                continue;
            }

            if (idade <= 10&& idade >= 5) {
                System.out.println("Aluno com " + idade + " anos: é obrigatório informar os Dados Do Responsavel.");

                System.out.println("Cpf Do Responsavel: ");
                String cpf=sc.nextLine();

                System.out.println("Email Do Responsavel: ");
                String email=sc.nextLine();

                System.out.println("Telefone Do Responsavel: ");
                String telefone=sc.nextLine();

                System.out.println("Senha Do Responsavel: ");
                String senha=sc.nextLine();

                System.out.println("CEP Do Responsavel: ");
                String cep = sc.nextLine();

                System.out.println("Bairro Do Responsavel: ");
                String bairro = sc.nextLine();

                System.out.println("Nome da Rua Do Responsavel: ");
                String nomeRua = sc.nextLine();

                System.out.println("Complemento Do Responsavel: ");
                String complemento = sc.nextLine();

                System.out.println("Numero da Casa Do Responsavel: ");
                Integer numCasa = sc.nextInt();

                ArrayList FichaDeTreino = new ArrayList();
                ArrayList AlunoPlano= new ArrayList();

                Aluno aluno = new Aluno(id, nome, cpf, dataNascimento, email, telefone, senha,FichaDeTreino, AlunoPlano,new Endereco(cep,bairro,nomeRua,complemento,numCasa));
                alunoRepositorio.save(aluno);
                instancia.adicionar();
                return;

                }
                break;
        }

        System.out.println("CPF: ");
        String cpf = sc.nextLine();
        if (AlunoRepositorio.alunos.stream().anyMatch(a -> a.getCpf().equals(cpf))) {
            System.out.println("Cpf ja cadastrado. Tente novamente com outro CPF.");
            return;
        }

        System.out.println("Email: ");
        String email = sc.nextLine();

        System.out.println("Telefone: ");
        String telefone = sc.nextLine();

        System.out.println("Senha: ");
        String senha = sc.nextLine();

        System.out.println("CEP: ");
        String cep = sc.nextLine();

        System.out.println("Bairro: ");
        String bairro = sc.nextLine();

        System.out.println("Nome da Rua: ");
        String nomeRua = sc.nextLine();

        System.out.println("Complemento: ");
        String complemento = sc.nextLine();

        System.out.println("Numero da Casa: ");
        Integer numCasa = sc.nextInt();

        ArrayList FichaDeTreino = new ArrayList();
        ArrayList AlunoPlano= new ArrayList();

        Aluno aluno = new Aluno(id, nome, cpf, dataNascimento, email, telefone, senha,FichaDeTreino, AlunoPlano,new Endereco(cep,bairro,nomeRua,complemento,numCasa));
        alunoRepositorio.save(aluno);
        instancia.adicionar();
    }

    public void MostrarAlunos(){
        if(alunos.isEmpty()){
            System.out.println("Nenhum aluno cadastrado ainda !!!");
        }
        for (Aluno a : alunos) {
            System.out.println("Dados do Aluno(a): " + a.getNome());
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
            System.out.println("Aluno nao foi encontrado. Voltando para o menuInicial...");
            return;
        }

        System.out.println("Escolha o que voce quer fazer no aluno(a):" + aluno.getNome());
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
    public Aluno buscarPorId(int id) {
        return alunoRepositorio.buscarPorId(id);
    }


}
