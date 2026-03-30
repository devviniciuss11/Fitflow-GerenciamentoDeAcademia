package Servico;

import Entidade.Plano;
import Repositorio.PlanoRepositorio;

import java.util.List;

public class PlanoServico {

    private PlanoRepositorio repositorio;

    public PlanoServico(PlanoRepositorio repository) {
        this.repositorio = repository;
    }

    public void criarPlano(Plano plano) {
        repositorio.criar(plano);
    }

    public List<Plano> listarPlanos() {
        return repositorio.listar();
    }

    public void atualizarPlano(String nome, Plano plano) {
        repositorio.atualizar(nome, plano);
    }

    public void deletarPlano(String nome) {
        repositorio.deletar(nome);
    }
}