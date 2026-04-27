
package Repositorio;

import Entidade.Personal;
import java.util.ArrayList;

public class PersonalRepositorio {

    public static ArrayList<Personal> personais = new ArrayList<>();

    public Personal buscarPorIdper(int id) {
        for (Personal a : personais) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }
    public void save(Personal personal){
        personais.add(personal);

    }
}


