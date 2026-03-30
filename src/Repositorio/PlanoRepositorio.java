package Repositorio;

import Entidade.Plano;
import Interfacess.Adicionando;
import Interfacess.Alterando;
import Interfacess.Removendo;
import java.util.ArrayList;
import java.util.List;

public class PlanoRepositorio implements Adicionando, Alterando, Removendo {

    private List<Plano> planos = new ArrayList<>();

    @Override
    public void criar(Plano plano) {
        if (plano != null) {
            planos.add(plano);
        }
    }

    @Override
    public List<Plano> listar() {
        return new ArrayList<>(planos);
    }

    @Override
    public Plano BuscarPorNome(String nome) {
        for (Plano p : planos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void atualizar(String nome, Plano novoPlano) {
        Plano existente = BuscarPorNome(nome);
        if (existente != null && novoPlano != null) {
            existente.setNome(novoPlano.getNome());
            existente.setValor(novoPlano.getValor());
            existente.setDescricao(novoPlano.getDescricao());
            existente.setDuracao(novoPlano.getDuracao());
        }
    }

    @Override
    public void deletar(String nome) {
        planos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }
}