package Servico;
import Entidade.Endereco;
import Entidade.Funcionario;
import Interfacess.Instancia;
import Repositorio.FuncionarioRepositorio;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.*;

import static Repositorio.FuncionarioRepositorio.funcionarios;


public class FuncionarioServico {
    Scanner sc = new Scanner(System.in);
    FuncionarioRepositorio funcionariosRepositorio = new FuncionarioRepositorio();
    Instancia instancia = new Instancia();

    public static void mostrarDiasTrabalhados(Funcionario funcionario) {
        if (funcionario == null){
            System.out.println("Funcionario não encontrado!");
            return;
        }
        Set<LocalDate> diasTrabalhados = funcionario.getDiasTrabalhados();

        if (diasTrabalhados.isEmpty()) {
            System.out.println("O funcionario não possui nenhuma presença marcada!");
        }else {
            System.out.println("Historico de presenças do Funcionario: ");
            for (LocalDate data : diasTrabalhados) {
                System.out.println("Dias Presentes --- " + data);
            }

        }

    }

    public static void marcarPresenca(Funcionario funcionario) {
        if (funcionario == null) {
            System.out.println("Funcionario Não encontrado!!");
            return;
        }
        LocalDate hoje = LocalDate.now();
        Set<LocalDate> diasTrabalhados = funcionario.getDiasTrabalhados();
        if (!diasTrabalhados.add(hoje)) {
            System.out.println("O funcionario já consta como presente nesse dia!");
        }else {
            System.out.println("Presenca marcada com sucesso! " + hoje);
        }

    }

    public void presencaFuncionario() {
        System.out.println("Digite o Cpf do Funcionario que deseja Marcar Presença: ");
        String Cpf = sc.nextLine().trim();

        Funcionario funcionarioPresente = null;
        for (Funcionario f: funcionarios) {
            if (f.getCpf() != null && f.getCpf().equals(Cpf))
                funcionarioPresente = f;
            break;
        }
        if (funcionarioPresente == null) {
            System.out.println("Funcionario não encontrado!!");
            return;
        }

        while (true) {
            System.out.println("Presença do Funcionario: "+ funcionarioPresente.getNome());
            System.out.println("==========================================================");
            System.out.println("Qual opção deseja executar: ");
            System.out.println("1 - Ver historico de presença do Funcionario.");
            System.out.println("2 - Marcar Presença do Funcionario.");
            System.out.println("3 - Voltar.");

            int opc;
            try {
                opc = Integer.parseInt(sc.nextLine().trim());
            }catch (NumberFormatException e) {
                System.out.println("Digite apenas Numeros: ");
                continue;
            }
            switch (opc) {
                case 1:
                    mostrarDiasTrabalhados(funcionarioPresente);
                    break;
                case 2:
                    marcarPresenca(funcionarioPresente);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção invalida!");
            }
        }
    }


    public int GeradorID() {
        {

            HashSet<Integer> IdsUsados = new HashSet<>();
            Random random = new Random();
            int novoID;
            do {

                novoID = random.nextInt(10000);
            } while (IdsUsados.contains(novoID));
            IdsUsados.add(novoID);
            return novoID;

        }
    }

    public void cadastrarFuncionarios() {
        LocalDate datadeNascimento = LocalDate.now();
        System.out.println("Area de Cadastro de Funcionario!!!");
        int id = GeradorID();

        System.out.println("Digite o nome do Funcionario: ");
        String nome = sc.nextLine();

        System.out.println("Digite o Cpf do Funcionario: ");
        String Cpf = sc.nextLine();

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
                System.out.println("A Idade esta errada!! Tente novamente. ");
                continue;
            }
            if (idade > 120) {
                System.out.println("A idade esta errada (Maior que 120 anos). Tente novamente");
                continue;
            }
            if (idade <= 17) {
                System.out.println("Funcionarios precisam ter mais de 18 anos. Tente novamente ");
                continue;
            }
            if (idade > 17) {
                System.out.println("Idade Cadastrada com sucesso!!");
            }
            break;

        }

        System.out.println("Digite seu Email: ");
        String Email = sc.nextLine();

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

        System.out.println("Digite o CEP: ");
        String cep = sc.nextLine();

        System.out.println("Digite o Bairro: ");
        String bairro = sc.nextLine();

        System.out.println("Digite o Nome da Rua: ");
        String nomeRua = sc.nextLine();

        System.out.println("Digite o Complemento: ");
        String Complemento = sc.nextLine();

        System.out.println("Digite o Numero da Casa: ");
        Integer numCasa = sc.nextInt();

        Funcionario funcionario = new Funcionario(id, nome, Cpf, datadeNascimento, Email, telefone, senha, cargo, salario, horario,new Endereco(cep,bairro,nomeRua,Complemento,numCasa));
        funcionariosRepositorio.Save(funcionario);
        instancia.adicionar();
    }


    public void ListarFuncionarios() {
        if (funcionarios.isEmpty()) {
            System.out.println("Não há Funcionarios Cadastrados!!");
        }else {
            for (Funcionario f : funcionarios) {
                System.out.println(" ID : " + f.getId() + " / Nome: " + f.getNome() + " Cargo: " + f.getCargo());
            }
        }
    }
    public void RemoverFuncionario(){
        System.out.println("Aba de Remoção de Funcionarios!!");
        System.out.println("Digite o CPF do Funcionario que deseja Remover");
        String buscaCpf = sc.nextLine();
        boolean removido = funcionarios.removeIf(Funcionario -> Funcionario.getCpf().equals(buscaCpf));

        if (removido){
            System.out.println("Funcionario com CPF: " + buscaCpf + " Removido");
        }else {
            System.out.println("Nenhum Funcionario com o Cpf: " + buscaCpf + " Encontrado!");
        }
    }

    public void AlterarFuncionario() {
        System.out.println("Pagina de Alteração de dados do Funcionario!!");
        System.out.println("Digite o Cpf do funcionario");
        String Cpf = sc.nextLine();
        Funcionario funcionario = null;
        for (Funcionario funcionario1 : funcionarios) {
            if (funcionario1.getCpf().equals(Cpf)){
                funcionario = funcionario1;
                System.out.println("Funcionario " + funcionario.getNome() + " Encontrada com sucesso.");
                break;
            }
        }
        if (funcionario == null) {
            System.out.println("Funcionario não encontrado. Voltando ao menuInicial.");
            return;
        }

        System.out.println("Escolha qual alteração gostaria de fazer nos dados do Funcionario(a): " + funcionario.getNome());
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
            System.out.println("10- Sair");
            int opc = sc.nextInt();
            sc.nextLine();
            switch (opc){
                case 1:
                    System.out.println("Digite o novo nome: ");
                    String novoNome = sc.nextLine();
                    funcionario.setNome(novoNome);
                    instancia.alterar();
                    break;
                case 2:
                    System.out.println("Digite o novo CPF: ");
                    String novoCpf = sc.nextLine();
                    funcionario.setCpf(novoCpf);
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
                            instancia.alterar();
                            break;
                        }catch (DateTimeParseException e) {
                            System.out.println("Voce nao digitou a data no formato certo. Tente novamente (exemplo: 02/08/2007).");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Digite o novo email: ");
                    String novoEmail = sc.nextLine();
                    funcionario.setEmail(novoEmail);
                    instancia.alterar();
                    break;
                case 5:
                    System.out.println("Digite o novo número: ");
                    String novoTelefone = sc.nextLine();
                    funcionario.setTelefone(novoTelefone);
                    instancia.alterar();
                    break;
                case 6:
                    System.out.println("Digite a nova senha: ");
                    String novaSenha = sc.nextLine();
                    funcionario.setSenha(novaSenha);
                    instancia.alterar();
                    break;
                case 7:
                    System.out.println("Digite o novo cargo ocupado pelo Funcionario: ");
                    String novoCargo = sc.nextLine();
                    funcionario.setCargo(novoCargo);
                    instancia.alterar();
                    break;
                case 8:
                    System.out.println("Digite o novo salario do Funcionario: ");
                    Double novoSalario = sc.nextDouble();
                    funcionario.setSalario(novoSalario);
                    instancia.alterar();
                    break;
                case 9:
                    System.out.println("Digite o novo Horario de trabalho do Funcionario: ");
                    Double novoHorario = sc.nextDouble();
                    funcionario.setHorarioTrabalho(novoHorario);
                    instancia.alterar();
                    break;
                case 10:
                    opcao = '1';
                    break;
            }
        }

    }


}