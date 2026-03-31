package Servico;

import Repositorio.PlanoRepositorio;
import Entidade.Plano;
import java.util.List;

public class PlanoServico {
    private PlanoRepositorio repositorio;

    public PlanoServico(){
        repositorio = new PlanoRepositorio();
    }

    public void cadastrarPlano(Plano plano){
    repositorio.getPlanos().add(plano);
    }
    public List<Plano> listarPlano(){
        return repositorio.getPlanos();
    }

    public Plano buscarPorId(int id){
        for (Plano p : repositorio.getPlanos()){
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public boolean removerPlano(int id){
    Plano plano = buscarPorId(id);

        if (plano != null){
            repositorio.getPlanos().remove(plano);
            return true;
        }
        return false;
    }

    public boolean atualizarPlano(Plano planoAtualizado){
        List<Plano> lista = repositorio.getPlanos();

        for (int i = 0; i < lista.size(); i++){
            if(lista.get(i).getId() == planoAtualizado.getId()){
                return true;
            }
        }
        return false;
    }
}