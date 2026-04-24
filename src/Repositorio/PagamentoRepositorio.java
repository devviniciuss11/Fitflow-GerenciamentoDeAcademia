package Repositorio;

import Entidade.Pagamento;
import java.util.ArrayList;
import java.util.List;

public class PagamentoRepositorio {

    public static List<Pagamento> pagamentos = new ArrayList<>();

    public void salvar(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    public List<Pagamento> listarTodos() {
        return pagamentos;
    }


    public boolean remover(int id) {
        return pagamentos.removeIf(p -> p.getId() == id);
    }

    public boolean atualizar(Pagamento pagamentoAtualizado) {
        for (int i = 0; i < pagamentos.size(); i++) {
            if (pagamentos.get(i).getId() == pagamentoAtualizado.getId()) {
                pagamentos.set(i, pagamentoAtualizado);
                return true;
            }
        }
        return false;
    }
}

