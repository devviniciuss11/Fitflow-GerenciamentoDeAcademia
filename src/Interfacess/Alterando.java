package Interfacess;

import Entidade.Plano;

public interface Alterando {
    public abstract void alterar();

    void atualizar(String nome, Plano novoPlano);
}
