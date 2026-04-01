package Repositorio;

import Entidade.Plano;
import java.util.List;
import java.util.ArrayList;

public class PlanoRepositorio {

    private List<Plano> planorepositorio;

    public PlanoRepositorio(){
        planorepositorio = new ArrayList<>();
        carregarPlanosPadrao(); // ← carrega automático
    }

    // PLANOS INICIAIS DO SISTEMA
    private void carregarPlanosPadrao(){
        planorepositorio.add(
                new Plano(1, "Frango", 79.90, "Acesso á Musculação", 30));

        planorepositorio.add(
                new Plano(2, "Definição", 129.90, "Musculação + Dieta", 30));

        planorepositorio.add(
                new Plano(3, "Bodybuilder", 199.90, "Acesso total + Personal + Dieta",  30));
    }
    public List<Plano> getPlanos(){
        return planorepositorio;
    }
}