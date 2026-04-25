package Servico;

import Entidade.Aluno;
import Repositorio.AlunoRepositorio;
import Entidade.Personal;
import Repositorio.PersonalRepositorio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class PersonalServico {

    Scanner sc = new Scanner(System.in);
    PersonalRepositorio personalRepositorio = new PersonalRepositorio();

    public void vincularAlunoAoPersonal(){
        System.out.println("Vincular Aluno ao Personal   ");
        System.out.println("Digite o CRAF do Personal   ");

        String crafBusca = sc.nextLine();

        Personal personalEncontrado = null;
        for(Personal p : PersonalRepositorio.personais){
            if (p.getCraf().equals(crafBusca)){
                personalEncontrado = p;
                break;

            }

        }

        if(personalEncontrado == null){
            System.out.println("  Personal não encontrado   ");
            return;

        }

        System.out.println("  Digite o CPF do Aluno que será treinado pelo Personal:  ");
        String cpfAluno = sc.nextLine();

        Aluno alunoEncontrado = null;
        for(Aluno a : AlunoRepositorio.alunos){
            if(a.getCpf().equals(cpfAluno)){
                alunoEncontrado = a;
                break;

            }

        }

        if(alunoEncontrado == null) {
            System.out.println("  Aluno não encontrado na base de Dados! Verifique o CPF.  ");
            return;

        }

        personalEncontrado.adicionarAluno(alunoEncontrado.getNome());
        System.out.println(" Sucesso! O Aluno " + alunoEncontrado.getNome() + " agora treina com o Personal " + personalEncontrado.getNome() +".");


    }

    public void listarAlunosDoPersonal(){
        System.out.println("  Ver alunos de um Personal  ");
        System.out.println("  Digitar o CRAF do Personal:  ");
        String crafBusca = sc.nextLine();

        Personal personalEncontrado = null;
        for (Personal p : PersonalRepositorio.personais) {
            if(p.getCraf().equals(crafBusca)){
                personalEncontrado = p;
                break;

            }

        }
        if(personalEncontrado != null) {
            if(personalEncontrado.getAlunosdele() == null || personalEncontrado.getAlunosdele().isEmpty()) {
                System.out.println(" Este Personal ainda não possui alunos vinculados. ");

            }else {
                System.out.println(" Alunos do Personal " + personalEncontrado.getNome() + ":");
                for (String nomeAluno : personalEncontrado.getAlunosdele()){
                    System.out.println(" - " + nomeAluno);

                }

            }

        } else {
            System.out.println(" Personal não encontrado! ");

        }

    }

    public void cadastrarPersonal(){
        System.out.println("     CADASTRO DO PERSONAL!     ");

        int id = new Random().nextInt(1000);

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.println("Cpf:");
        String cpf = sc.nextLine();

        System.out.println( "  Data de Nascimento (dd/mm/aaaa) : ");
        LocalDate dataNascimento;
        DateTimeFormatter nascimentoFmt = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        while (true) {
            String entrada = sc.nextLine().trim();
            try {
                dataNascimento = LocalDate.parse(entrada, nascimentoFmt);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/mm/aaaa (ex: 12/09/2000). Tente novamente:");
            }
        }

        System.out.println( "Email: " );
        String email = sc.nextLine();

        System.out.println( "Telefone: " );
        String telefone = sc.nextLine();

        System.out.println( "Senha: ");
        String senha = sc.nextLine();

        System.out.print("CRAF: ");
        String craf = sc.nextLine();

        double salario = 0;
        boolean salarioValido = false;

        while(!salarioValido) {
            try {
                System.out.print("Salário (R$) : ");
                salario = sc.nextDouble();
                salarioValido = true;
            } catch (InputMismatchException e) {
                System.out.println(" Digite apenas números para o salário!");
                sc.nextLine();
            }
        }

        double horas = 0;
        boolean horasValidas = false;

        while(!horasValidas) {
            try {
                System.out.print("Horas do expediente do Personal: ");
                horas = sc.nextDouble();
                horasValidas = true;
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas números para as horas! ");
                sc.nextLine();
            }
        }

        sc.nextLine();

        Personal novo = new Personal(
                id, nome, cpf, dataNascimento, email, telefone, senha, craf, salario, horas, new ArrayList<>()
        );
        personalRepositorio.save(novo);

        System.out.println(" Personal Cadastrado com Sucesso! ");
    }

    public void listarPersonais() {
        if(PersonalRepositorio.personais.isEmpty()){
            System.out.println("Nenhum Personal foi cadastrado.");

        }else{
            for (Personal p : PersonalRepositorio.personais) {
                System.out.println(p);



            }

        }

    }

    public void excluirPersonal(){
        System.out.println(" Remover Personal ");
        System.out.println("Digite o Craf do Personal que dejesa Excluir: ");

        String crafBusca = sc.nextLine();

        boolean removido = PersonalRepositorio.personais.removeIf(p -> p.getCraf().equals((crafBusca)));

        if (removido){
            System.out.println("Personal com craft : " + crafBusca + "removido");

        }else{
            System.out.println("Nenhum Personal encontrado");

        }

    }

    public void alterarPersonal(){
        System.out.println("Alterar dados do Personal");
        System.out.println("Digite o CRAF do personal: ");
        String crafBusca = sc.nextLine();

        Personal encontrado = null;
        for(Personal p : PersonalRepositorio.personais){
            if (p.getCraf().equals(crafBusca)){
                encontrado = p;
                break;

            }

        }
        if(encontrado != null){
            System.out.println("Personal encontrado: " + encontrado.getNome());
            System.out.println("1- Alterar nome");
            System.out.println("2- Alterar salário");
            System.out.println("Escolha uma opção");
            int op = sc.nextInt();
            sc.nextLine();

            if (op == 1) {
                System.out.println("  Novo nome: ");
                encontrado.setNome(sc.nextLine());
                System.out.println("  Nome atualizado  ");

            }else if (op ==2){
                System.out.println(" Novo salário: ");
                encontrado.setSalario(sc.nextDouble());
                sc.nextLine();
                System.out.println(" Salario Atualizado ");

            }else{
                System.out.println(" Personal nao encontrado ");

            }

        }

    }


}
