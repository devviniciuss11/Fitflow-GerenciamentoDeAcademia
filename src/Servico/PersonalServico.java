package Servico;

import Entidade.Aluno;
import Entidade.Personal;
import Repositorio.PersonalRepositorio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;


public class PersonalServico {

    Scanner sc = new Scanner(System.in);
    PersonalRepositorio personalRepositorio = new PersonalRepositorio();

    public void cadastrarPersonal(){
        System.out.println("     CADASTRO DO PERSONAL!     ");

        int id = new Random().nextInt(1000);

        System.out.print(" Nome : ");
        String nome = sc.nextLine();

        System.out.print(" CRAF : ");
        String craf = sc.nextLine();

        double salario = 0;
        boolean salarioValido = false;

        while(!salarioValido) {
            try {
                System.out.print(" Salário (R$) : ");
                salario = sc.nextDouble();
                salarioValido = true;
            } catch (InputMismatchException e) {
                System.out.println("  Digite apenas números para o salário!   ");
                sc.nextLine();
            }
        }

        double horas = 0;
        boolean horasValidas = false;

        while(!horasValidas) {
            try {
                System.out.print(" Horas do expediente do Personal: ");
                horas = sc.nextDouble();
                horasValidas = true;
            } catch (InputMismatchException e) {
                System.out.println("  Digite apenas números para as horas!  ");
                sc.nextLine();
            }
        }

        sc.nextLine();

        Personal novo = new Personal(id, nome, "000.000.000-00", LocalDate.now(), "email@teste.com", "9999-9999", "123", craf, salario, horas, new ArrayList<>());
        personalRepositorio.save(novo);

        System.out.println(" Personal Cadastrado com Sucesso! ");
    }

    public void listarPersonais(){
        if(PersonalRepositorio.personais.isEmpty()){
            System.out.println("Nenhum Personal foi cadastrado.");

        }else{
            for (Personal p : PersonalRepositorio.personais) {
                System.out.println(" ID : " + p.getId() + " / Nome: " + p.getNome() + "/ Craf: " + p.getCraf());



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
public void GerenciarSeusAlunos(){
    System.out.println("Gerenciar seus alunos");
    System.out.println("O que deseja fazer? ");
    int sair=2;
    while(sair!=0){
        System.out.println(" [1] - Adicionar um Aluno novo pra sua lista de alunos");
        System.out.println(" [2] - Listar seus Alunos");
        System.out.println(" [3] - Remover Aluno da sua lista");
        System.out.println(" [4] - Alterar Dados de seu Aluno");
        System.out.println(" [5] - Voltar ao Menu Principal");
        Scanner sc1 = new Scanner(System.in);
        int op = sc1.nextInt();
        switch (op){
            case 1:
                PersonalRepositorio personalRepositorio = new PersonalRepositorio();
                personalRepositorio.adicionarAluno();
                System.out.println("Adicionando um novo Aluno");


            case 2:
                System.out.println("Listando seus alunos");
                break;
            case 3:
                System.out.println("Removendo Aluno da sua lista");
                break;
            case 4:
                System.out.println("Alterando Dados de seu Aluno");
                break;

                case 5:
                System.out.println("Voltando ao Menu Principal");
                sair=0;
        }
    }

}

}

