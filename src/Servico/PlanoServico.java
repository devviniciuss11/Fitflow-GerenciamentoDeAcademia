package Servico;

import Entidade.Plano;
import Repositorio.PagamentoRepositorio;
import Repositorio.PlanoRepositorio;

import java.util.List;

public class PlanoServico {
    private static final String MSG_PLANO_DUPLICADO = "Nome Do plano Ja Cadastrado.Tente Novamente!";
    private static final String MSG_CADASTRO_REALIZADO = "CADASTRO REALIZADO!";
    private static final String MSG_CAMPOS_VAZIOS = "Campos vazios. Tente novamente!";
    private static final String MSG_PLANO_NAO_ENCONTRADO = "NAO ENCONTRADO!";
    private static final String MSG_PLANO_REMOVIDO = "PLANO REMOVIDO COM SUCESSO!";
    private static final String MSG_PLANO_COM_PAGAMENTO = "Nao e possivel remover o plano pois existem pagamentos vinculados a ele.";

    private PlanoRepositorio repositorio;
    private PagamentoRepositorio pagamentoRepositorio;

    public PlanoServico() {
        repositorio = new PlanoRepositorio();
        pagamentoRepositorio = new PagamentoRepositorio();
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

    public String removerPlanoComValidacao(int id) {
        Plano plano = repositorio.buscarPorId(id);
        if (plano == null) {
            return MSG_PLANO_NAO_ENCONTRADO;
        }

        if (pagamentoRepositorio.existePorPlanoId(id)) {
            return MSG_PLANO_COM_PAGAMENTO;
        }

        if (repositorio.removerPorId(id)) {
            return MSG_PLANO_REMOVIDO;
        }

        return MSG_PLANO_NAO_ENCONTRADO;
    }

    public boolean atualizarPlano(Plano planoAtualizado) {
        return repositorio.atualizar(planoAtualizado);
    }
}
