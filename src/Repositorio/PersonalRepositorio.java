
package Repositorio;

import Entidade.Aluno;
import Entidade.Personal;
import java.util.ArrayList;
import java.util.Scanner;

import static Repositorio.AlunoRepositorio.alunos;

public class PersonalRepositorio {

    public static ArrayList<Personal> personais = new ArrayList<>();

    public void save(Personal personal){
        personais.add(personal);

    }
    public static ArrayList<Aluno>ListaDeAlunosDoPersonal=new ArrayList<>();
    public void adicionarAluno() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Diga o id do aluno que voce quer adicionar na sua lista de alunos:");
        int id = sc.nextInt();

        Aluno encontrado = null;
        for (Aluno a : alunos) {
            if (a.getId() == id) {
                encontrado = a;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Aluno com id " + id + " nao encontrado.");
            return;
        }

        boolean jaExiste = false;
        for (Aluno a : ListaDeAlunosDoPersonal) {
            if (a.getId() == id) {
                jaExiste = true;
                break;
            }
        }

        if (jaExiste) {
            System.out.println("Esse aluno ja esta na sua lista.");
            return;
        }

        ListaDeAlunosDoPersonal.add(encontrado);
        System.out.println("Aluno adicionado: " + encontrado.getNome());
    }


    public void removerAluno(Aluno aluno){
        ListaDeAlunosDoPersonal.remove(aluno);
    }
    public void atualizarAluno(Aluno aluno, Aluno novoAluno){
        ListaDeAlunosDoPersonal.set(ListaDeAlunosDoPersonal.indexOf(aluno), novoAluno);
    }
    public ArrayList<Aluno> getListaDeAlunosDoPersonal() {
        return ListaDeAlunosDoPersonal;
    }
    public void listaDeAlunosDoPersonal(ArrayList<Aluno> listaDeAlunosDoPersonal) {
        for (int i = 0; i < listaDeAlunosDoPersonal.size(); i++) {
            System.out.println(listaDeAlunosDoPersonal.get(i).getNome());
            System.out.println(listaDeAlunosDoPersonal.get(i).getCpf());
            System.out.println(listaDeAlunosDoPersonal.get(i).getId());
            System.out.println(listaDeAlunosDoPersonal.get(i).getTelefone());
        }
    }
}
