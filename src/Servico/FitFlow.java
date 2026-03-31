package Servico;

import Gui.GuiAluno;
import Gui.GuiPersonal;
import Gui.GuiPlano;

import java.util.Scanner;

public class FitFlow {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opc2=1;
        while(opc2==1){
            System.out.println("1- Gerenciar Alunos");
            System.out.println("2- Gerenciar Personals");
            System.out.println("3- Gerenciar Treinos");
            System.out.println("4- Gerenciar Planos");
            System.out.println("5- Sair");
            int opc = sc.nextInt();
            switch (opc){
                case 1:
                    GuiAluno guiAluno = new GuiAluno();
                    guiAluno.menu();
                    break;
                case 2:
                    GuiPersonal guiPersonal = new GuiPersonal();
                    guiPersonal.menu();
                    break;
                case 4:
                    GuiPlano guiPlano = new GuiPlano();
                    guiPlano.menuPlano();
                case 5:
                    System.out.println("Saindo...");
                    opc2=0;
                    break;
            }
        }
    }
}
