package Repositorio;

import Entidade.Treino;
import Infra.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TreinoRepositorio {

    public void salvar(Treino treino) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            treino.setId(null);
            session.persist(treino);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public List<Treino> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Treino", Treino.class).list();
        }
    }

    public Treino buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Treino.class, id);
        }
    }

    public boolean existePorAlunoId(int alunoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(t.id) from Treino t where t.idAluno = :alunoId",
                            Long.class
                    )
                    .setParameter("alunoId", alunoId)
                    .uniqueResult();
            return total != null && total > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar treinos do aluno.", e);
        }
    }

    public boolean existePorPersonalId(int personalId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(t.id) from Treino t where t.idPersonal = :personalId",
                            Long.class
                    )
                    .setParameter("personalId", personalId)
                    .uniqueResult();
            return total != null && total > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar treinos do personal.", e);
        }
    }

    public boolean remover(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Treino treino = session.get(Treino.class, id);
            if (treino == null) {
                tx.commit();
                return false;
            }
            session.remove(treino);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public boolean atualizar(Treino treinoAtualizado) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Treino treino = session.get(Treino.class, treinoAtualizado.getId());
            if (treino == null) {
                tx.commit();
                return false;
            }

            treino.setIdAluno(treinoAtualizado.getIdAluno());
            treino.setIdPersonal(treinoAtualizado.getIdPersonal());
            treino.setData(treinoAtualizado.getData());
            treino.setHorario(treinoAtualizado.getHorario());
            treino.setDescricao(treinoAtualizado.getDescricao());
            session.merge(treino);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }
}
