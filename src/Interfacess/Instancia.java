package Interfacess;

public class Instancia implements Adicionando,Removendo,Alterando{
    @Override
    public void adicionar() {
        System.out.println("Cadastro Realizado com Sucesso!!!!");

    }
    @Override
    public void remover() {
        System.out.println("Cadatro Removido com Sucesso!!!!");
    }
    @Override
    public void alterar() {
        System.out.println("Dados Alterado com Sucesso!!!!");
    }
}
