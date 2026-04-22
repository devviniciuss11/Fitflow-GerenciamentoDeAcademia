package Repositorio;

import Entidade.Pagamento;
import java.util.ArrayList;
import java.util.List;

public class PagamentoRepositorio {

    public static List<Pagamento> pagamentos = new ArrayList<>();

    public void salvar(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    // Veja que aqui o T tem que ser maiúsculo também!
    public List<Pagamento> listarTodos() {
        return pagamentos;
    }
}
