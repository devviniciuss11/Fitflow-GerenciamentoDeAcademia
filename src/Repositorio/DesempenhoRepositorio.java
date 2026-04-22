package Repositorio;

import Entidade.Desempenho;
import java.util.ArrayList;

public class DesempenhoRepositorio {

    public static ArrayList<Desempenho> desempenhos = new ArrayList<>();

    public void save(Desempenho desempenho) {
        desempenhos.add(desempenho);
    }
}