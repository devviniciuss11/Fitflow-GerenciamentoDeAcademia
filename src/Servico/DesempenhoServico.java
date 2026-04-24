package Servico;

import Entidade.Desempenho;
import Repositorio.DesempenhoRepositorio;
import Interfacess.Instancia;

import java.util.Random;
import java.util.Scanner;

public class DesempenhoServico {
    Scanner sc = new Scanner(System.in);
    DesempenhoRepositorio repositorio = new DesempenhoRepositorio();
    Instancia instancia = new Instancia();

    public void cadastrarDesempenho() {
        System.out.println("\n--- CADASTRO DE DESEMPENHO ---");
        int id = new Random().nextInt(10000);

        System.out.print("Digite o CPF do Aluno: ");
        String cpfAluno = sc.nextLine();

        System.out.print("Digite o Peso (kg) - ex: 70,5: ");
        double peso = sc.nextDouble();

        System.out.print("Digite a Altura (m) - ex: 1,75: ");
        double altura = sc.nextDouble();
        sc.nextLine(); // limpar o buffer

        Desempenho desempenho = new Desempenho(id, cpfAluno, peso, altura);
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
        int id = sc.nextInt();
        sc.nextLine();

        boolean removido = DesempenhoRepositorio.desempenhos.removeIf(d -> d.getId() == id);

        if (removido) {
            instancia.remover();
        } else {
            System.out.println("Registro não encontrado!");
        }
    }

    public void alterarDesempenho() {
        System.out.println("\n--- ALTERAR DADOS DE DESEMPENHO ---");
        System.out.print("Digite o ID do desempenho: ");
        int id = sc.nextInt();
        sc.nextLine();

        Desempenho encontrado = null;
        for (Desempenho d : DesempenhoRepositorio.desempenhos) {
            if (d.getId() == id) {
                encontrado = d;
                break;
            }
        }

        if (encontrado != null) {
            System.out.println("Desempenho encontrado para o CPF: " + encontrado.getCpfAluno());
            System.out.println("1- Alterar Peso");
            System.out.println("2- Alterar Altura");
            System.out.print("Escolha uma opção: ");
            int op = sc.nextInt();

            if (op == 1) {
                System.out.print("Novo peso (kg): ");
                encontrado.setPeso(sc.nextDouble());
                System.out.println("Peso atualizado! Novo IMC: " + String.format("%.2f", encontrado.getImc()));
                instancia.alterar();
            } else if (op == 2) {
                System.out.print("Nova altura (m): ");
                encontrado.setAltura(sc.nextDouble());
                System.out.println("Altura atualizada! Novo IMC: " + String.format("%.2f", encontrado.getImc()));
                instancia.alterar();
            } else {
                System.out.println("Opção inválida.");
            }
        } else {
            System.out.println("ID não encontrado. Tente novamente.");
        }
    }
}