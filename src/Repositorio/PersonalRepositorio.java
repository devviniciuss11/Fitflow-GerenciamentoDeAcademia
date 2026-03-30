
package Repositorio;

import Entidade.Personal;
import java.util.ArrayList;

public class PersonalRepositorio {

    public static ArrayList<Personal> personais = new ArrayList<>();

    public void save(Personal personal){
        personais.add(personal);

    }
}

