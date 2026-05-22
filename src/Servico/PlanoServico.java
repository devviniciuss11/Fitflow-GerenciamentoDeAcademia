package Servico;

import Entidade.Plano;
import Repositorio.PlanoRepositorio;

import java.util.List;

public class PlanoServico {
    private static final String MSG_PLANO_DUPLICADO = "Nome Do plano Ja Cadastrado.Tente Novamente!";
    private static final String MSG_CADASTRO_REALIZADO = "CADASTRO REALIZADO!";
    private static final String MSG_CAMPOS_VAZIOS = "Campos vazios. Tente novamente!";

    private PlanoRepositorio repositorio;

    public PlanoServico() {
        repositorio = new PlanoRepositorio();
    }

    public String cadastrarPlano(Plano plano) {
        if (plano == null || plano.getNome() == null || plano.getNome().trim().isEmpty()) {
            return MSG_CAMPOS_VAZIOS;
        }

        if (repositorio.existePorNome(plano.getNome())) {
            return MSG_PLANO_DUPLICADO;
        }

        repositorio.save(plano);
        return MSG_CADASTRO_REALIZADO;
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
