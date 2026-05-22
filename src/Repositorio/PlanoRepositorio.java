package Repositorio;

import Entidade.Plano;
import Infra.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlanoRepositorio {

    public PlanoRepositorio() {
        carregarPlanosPadrao();
    }

    private void carregarPlanosPadrao() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery("select count(p.id) from Plano p", Long.class).uniqueResult();
            if (total != null && total > 0) {
                return;
            }
        }

        save(new Plano(null, "Frango", 79.90, "Acesso a Musculacao", 30));
        save(new Plano(null, "Definicao", 129.90, "Musculacao + Dieta", 30));
        save(new Plano(null, "Bodybuilder", 199.90, "Acesso total + Personal + Dieta", 30));
    }

    public List<Plano> getPlanos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Plano", Plano.class).list();
        }
    }

    public void save(Plano plano) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            plano.setId(null);
            session.persist(plano);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public boolean existePorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Plano p where lower(trim(p.nome)) = :nome",
                            Long.class
                    )
                    .setParameter("nome", nome.trim().toLowerCase())
                    .uniqueResult();

            return total != null && total > 0;
        }
    }

    public Plano buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Plano.class, id);
        }
    }

    public boolean removerPorId(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Plano plano = session.get(Plano.class, id);
            if (plano == null) {
                tx.commit();
                return false;
            }
            session.remove(plano);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public boolean atualizar(Plano planoAtualizado) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Plano plano = session.get(Plano.class, planoAtualizado.getId());
            if (plano == null) {
                tx.commit();
                return false;
            }

            plano.setNome(planoAtualizado.getNome());
            plano.setValor(planoAtualizado.getValor());
            plano.setDuracao(planoAtualizado.getDuracao());
            plano.setDescricao(planoAtualizado.getDescricao());
            session.merge(plano);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }
}
