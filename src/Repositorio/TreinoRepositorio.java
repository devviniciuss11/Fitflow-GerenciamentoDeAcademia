package Repositorio;

import Entidade.Treino;
import java.util.ArrayList;
import java.util.List;

public class TreinoRepositorio {


        private List<Treino> treinos = new ArrayList<>();

        public void salvar(Treino treino) {
            treinos.add(treino);
        }

        public List<Treino> listarTodos() {
            return treinos;
        }

        public boolean remover(int id) {
            return treinos.removeIf(t -> t.getId() == id);
        }

        public boolean atualizar(Treino treinoAtualizado) {
            for (int i = 0; i < treinos.size(); i++) {
                if (treinos.get(i).getId() == treinoAtualizado.getId()) {
                    treinos.set(i, treinoAtualizado);
                    return true;
                }
            }
            return false;
        }
}

