package Repositorio;

import Entidade.Plano;
import java.util.List;
import java.util.ArrayList;

public class PlanoRepositorio {
    private List<Plano> planorepositorio;

    public PlanoRepositorio(){
        planorepositorio = new ArrayList<>();
    }
    public List<Plano> getPlanos(){
        return planorepositorio;
    }
}