package Servico;

import Entidade.Aluno;
import Entidade.Desempenho;
import Repositorio.AlunoRepositorio;
import Repositorio.DesempenhoRepositorio;

import java.util.List;
import java.util.Scanner;

public class DesempenhoServico {
    Scanner sc = new Scanner(System.in);
    AlunoRepositorio alunoRepositorio = new AlunoRepositorio();
    DesempenhoRepositorio repositorio = new DesempenhoRepositorio();

    public void cadastrarDesempenho() {
        System.out.println("\n--- CADASTRO DE DESEMPENHO ---");

        System.out.print("Digite o CPF do Aluno: ");
        String cpf = sc.nextLine().trim();

        if (cpf.isEmpty()) {
            System.out.println("CPF invalido. Tente novamente.");
            return;
        }

        Aluno alunoEncontrado = alunoRepositorio.buscarPorCpf(cpf);

        if (alunoEncontrado == null) {
            System.out.println("Aluno Não Encontrado! Verifique o cpf");
            return;
        }

        Double peso = lerDoublePositivo(
                "Digite o Peso (kg) - ex: 70,5: ",
                "Digite apenas numeros para o peso.",
                "Peso invalido. Informe um valor maior que zero."
        );
        if (peso == null) {
            return;
        }

        Double altura = lerDoublePositivo(
                "Digite a Altura (m) - ex: 1,75: ",
                "Digite apenas numeros para a altura.",
                "Altura invalida. Informe um valor maior que zero."
        );
        if (altura == null) {
            return;
        }

        Desempenho desempenho = new Desempenho(null, cpf, peso, altura, alunoEncontrado.getNome());
        repositorio.save(desempenho);

        System.out.println("Valor do imc Calculado: " + String.format("%.2f", desempenho.getImc()));
        System.out.println("Cadastro realizado com Sucesso.");
    }

    public void listarDesempenhos() {
        List<Desempenho> desempenhos = repositorio.listarTodos();
        if (desempenhos.isEmpty()) {
            System.out.println("Nenhum desempenho Registrado no momento.");
            return;
        }

        Integer idBusca = lerId(
                "Digite o ID para busca (0 para listar todos): ",
                "Digite Apenas Numeros para o id."
        );
        if (idBusca == null) {
            return;
        }

        System.out.println("\n--- LISTA DE DESEMPENHOS ---");

        if (idBusca == 0) {
            for (Desempenho d : desempenhos) {
                System.out.println(d);
            }
            System.out.println("Exibir Lista de Desempenhos cadastrados!.");
            return;
        }

        Desempenho encontrado = repositorio.buscarPorId(idBusca);
        if (encontrado == null) {
            System.out.println("Registro não Encontrado.");
            return;
        }

        System.out.println(encontrado);
        System.out.println("Exibir Lista de Desempenhos cadastrados!.");
    }

    public void excluirDesempenho() {
        System.out.println("\n--- REMOVER DESEMPENHO ---");
        Integer id = lerId(
                "Digite o ID do desempenho que deseja remover: ",
                "Registro não Encontrado."
        );
        if (id == null) {
            return;
        }

        boolean removido = repositorio.remover(id);

        if (removido) {
            System.out.println("Desempenho Excluido com Sucesso!.");
        } else {
            System.out.println("Registro não Encontrado.");
        }
    }

    public void alterarDesempenho() {
        System.out.println("\n--- ALTERAR DADOS DE DESEMPENHO ---");
        Integer id = lerId("Digite o ID do desempenho: ", "Digite Apenas Numeros para o id.");
        if (id == null) {
            return;
        }

        Desempenho encontrado = repositorio.buscarPorId(id);

        if (encontrado == null) {
            System.out.println("Registro não Encontrado.");
            return;
        }

        System.out.println("Desempenho encontrado para o CPF: " + encontrado.getCpf());
        System.out.println("1- Alterar Peso");
        System.out.println("2- Alterar Altura");

        Integer op = lerId("Escolha uma opcao: ", "Digite apenas numeros para a opcao.");
        if (op == null) {
            return;
        }

        if (op == 1) {
            Double novoPeso = lerDoublePositivo(
                    "Novo peso (kg): ",
                    "Digite apenas numeros para o peso.",
                    "Peso invalido. Informe um valor maior que zero."
            );
            if (novoPeso == null) {
                return;
            }
            encontrado.setPeso(novoPeso);
        } else if (op == 2) {
            Double novaAltura = lerDoublePositivo(
                    "Nova altura (m): ",
                    "Digite apenas numeros para a altura.",
                    "Altura invalida. Informe um valor maior que zero."
            );
            if (novaAltura == null) {
                return;
            }
            encontrado.setAltura(novaAltura);
        } else {
            System.out.println("Opcao invalida.");
            return;
        }

        repositorio.atualizar(encontrado);
        System.out.println("Desempenho atualizado com sucesso!.");
    }

    private Integer lerId(String prompt, String mensagemErro) {
        System.out.print(prompt);
        String entrada = sc.nextLine().trim();
        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println(mensagemErro);
            return null;
        }
    }

    private Double lerDoublePositivo(String prompt, String mensagemErroFormato, String mensagemErroNegativo) {
        System.out.print(prompt);
        String entrada = sc.nextLine().trim().replace(',', '.');
        double valor;
        try {
            valor = Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            System.out.println(mensagemErroFormato);
            return null;
        }

        if (valor <= 0) {
            System.out.println(mensagemErroNegativo);
            return null;
        }
        return valor;
    }
}