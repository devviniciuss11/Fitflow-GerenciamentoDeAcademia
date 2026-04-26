package Servico;

import Entidade.Aluno;
import Entidade.Desempenho;
import Repositorio.DesempenhoRepositorio;
import Interfacess.Instancia;
import Repositorio.AlunoRepositorio;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class DesempenhoServico {
    Scanner sc = new Scanner(System.in);
    AlunoRepositorio alunoRepositorio = new AlunoRepositorio();
    DesempenhoRepositorio repositorio = new DesempenhoRepositorio();
    Instancia instancia = new Instancia();

    public void cadastrarDesempenho() {
        System.out.println("\n--- CADASTRO DE DESEMPENHO ---");
        int id = new Random().nextInt(10000);

        System.out.print("Digite o CPF do Aluno: ");
        String cpf = sc.nextLine().trim();

        if (cpf.isEmpty()) {
            System.out.println("CPF invalido. Tente novamente.");
            return;
        }

        Aluno alunoEncontrado = null;
        for (Aluno a : AlunoRepositorio.alunos) {
            if (a.getCpf() != null && a.getCpf().equals(cpf)) {
                alunoEncontrado = a;
                break;
            }
        }

        if (alunoEncontrado == null) {
            System.out.println("Aluno nao encontrado! Verifique o CPF.");
            return;
        }

        double peso;
        try {
            System.out.print("Digite o Peso (kg) - ex: 70,5: ");
            peso = sc.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Digite apenas numeros para o peso.");
            sc.nextLine();
            return;
        }

        if (peso <= 0) {
            System.out.println("Peso invalido. Informe um valor maior que zero.");
            sc.nextLine();
            return;
        }

        double altura;
        try {
            System.out.print("Digite a Altura (m) - ex: 1,75: ");
            altura = sc.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Digite apenas numeros para a altura.");
            sc.nextLine();
            return;
        }

        if (altura <= 0) {
            System.out.println("Altura invalida. Informe um valor maior que zero.");
            sc.nextLine();
            return;
        }
        sc.nextLine();

        Desempenho desempenho = new Desempenho(id, cpf, peso, altura, alunoEncontrado.getNome());
        repositorio.save(desempenho);

        System.out.println("Desempenho salvo! O IMC calculado foi: " + String.format("%.2f", desempenho.getImc()));
        instancia.adicionar();
    }

    public void listarDesempenhos() {
        if (DesempenhoRepositorio.desempenhos.isEmpty()) {
            System.out.println("Nenhum desempenho registrado no momento!");
            return;
        }
        System.out.println("\n--- LISTA DE DESEMPENHOS ---");
        for (Desempenho d : DesempenhoRepositorio.desempenhos) {
            System.out.println(d.toString());
        }
    }

    public void excluirDesempenho() {
        System.out.println("\n--- REMOVER DESEMPENHO ---");
        System.out.print("Digite o ID do desempenho que deseja remover: ");

        int id;
        try {
            id = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Digite apenas numeros para o ID.");
            sc.nextLine();
            return;
        }

        boolean removido = DesempenhoRepositorio.desempenhos.removeIf(d -> d.getId() == id);

        if (removido) {
            instancia.remover();
        } else {
            System.out.println("Registro nao encontrado!");
        }
    }

    public void alterarDesempenho() {
        System.out.println("\n--- ALTERAR DADOS DE DESEMPENHO ---");
        System.out.print("Digite o ID do desempenho: ");

        int id;
        try {
            id = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Digite apenas numeros para o ID.");
            sc.nextLine();
            return;
        }

        Desempenho encontrado = null;
        for (Desempenho d : DesempenhoRepositorio.desempenhos) {
            if (d.getId() == id) {
                encontrado = d;
                break;
            }
        }

        if (encontrado != null) {
            System.out.println("Desempenho encontrado para o CPF: " + encontrado.getCpf());
            System.out.println("1- Alterar Peso");
            System.out.println("2- Alterar Altura");
            System.out.print("Escolha uma opcao: ");

            int op;
            try {
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas numeros para a opcao.");
                sc.nextLine();
                return;
            }

            if (op == 1) {
                double novoPeso;
                try {
                    System.out.print("Novo peso (kg): ");
                    novoPeso = sc.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Digite apenas numeros para o peso.");
                    sc.nextLine();
                    return;
                }

                if (novoPeso <= 0) {
                    System.out.println("Peso invalido. Informe um valor maior que zero.");
                    sc.nextLine();
                    return;
                }

                encontrado.setPeso(novoPeso);
                System.out.println("Peso atualizado! Novo IMC: " + String.format("%.2f", encontrado.getImc()));
                instancia.alterar();
            } else if (op == 2) {
                double novaAltura;
                try {
                    System.out.print("Nova altura (m): ");
                    novaAltura = sc.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Digite apenas numeros para a altura.");
                    sc.nextLine();
                    return;
                }

                if (novaAltura <= 0) {
                    System.out.println("Altura invalida. Informe um valor maior que zero.");
                    sc.nextLine();
                    return;
                }

                encontrado.setAltura(novaAltura);
                System.out.println("Altura atualizada! Novo IMC: " + String.format("%.2f", encontrado.getImc()));
                instancia.alterar();
            } else {
                System.out.println("Opcao invalida.");
            }

            sc.nextLine();
        } else {
            System.out.println("ID nao encontrado. Tente novamente.");
        }
    }
}
