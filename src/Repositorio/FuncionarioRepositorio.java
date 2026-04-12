package Repositorio;

import java.util.ArrayList;
import Entidade.Funcionario;
import Entidade.Pessoa;

public class FuncionarioRepositorio {
    public static ArrayList<Funcionario> funcionarios = new ArrayList<>();

    public void Save (Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

}
