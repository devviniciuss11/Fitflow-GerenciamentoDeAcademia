package Servico;

import Entidade.Plano;
import Repositorio.PlanoRepositorio;

import java.util.List;

public class PlanoServico {
    private PlanoRepositorio repositorio;

    public PlanoServico() {
        repositorio = new PlanoRepositorio();
    }

    public void cadastrarPlano(Plano plano) {
        repositorio.save(plano);
    }

    public List<Plano> listarPlano() {
        return repositorio.getPlanos();
    }

    public Plano buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    public boolean removerPlano(int id) {
        return repositorio.removerPorId(id);
    }

    public boolean atualizarPlano(Plano planoAtualizado) {
        return repositorio.atualizar(planoAtualizado);
    }
}
