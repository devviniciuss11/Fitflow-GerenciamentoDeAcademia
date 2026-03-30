package Gui;

import Servico.PersonalServico;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Scanner;


public class GuiPersonal {
    Scanner sc = new Scanner(System.in);
    PersonalServico servico = new PersonalServico();

    public void menu(){
        int op = -1;
        while (op != 5){
            System.out.println("           MENU PERSONAL           ");
            System.out.println(" [1] - Cadastrar Novo Personal");
            System.out.println(" [2] - Listar Personais Cadastrados");
            System.out.println(" [3] - Remover Personal");
            System.out.println(" [4] - Alterar Dados de um Personal");
            System.out.println(" [5] - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            try{
                op = sc.nextInt();
                sc.nextLine();

            }catch (java.util.InputMismatchException e) {
                System.out.println(" ERRO: Digite apenas números!");
                sc.nextLine();
                continue;

            }

            switch(op){
                case 1 -> servico.cadastrarPersonal();
                case 2 -> servico.listarPersonais();
                case 3 -> servico.excluirPersonal();
                case 4 -> servico.alterarPersonal();
                case 5 -> System.out.println(" Voltando ao Menu Principal... ");
                default -> System.out.println(" Opção Inválida! Tente Novamente. ");

            }




        }

    }

}
