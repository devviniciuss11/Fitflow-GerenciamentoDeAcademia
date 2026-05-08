package Repositorio;

import Entidade.Aluno;
import Infra.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepositorio {

    public void save(Aluno aluno) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            aluno.setId(null);
            session.persist(aluno);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public Aluno buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Aluno.class, id);
        }
    }

    public Aluno buscarPorCpf(String cpf) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Aluno where cpf = :cpf", Aluno.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();
        }
    }

    public boolean existePorCpf(String cpf) {
        Long total;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            total = session.createQuery("select count(a.id) from Aluno a where a.cpf = :cpf", Long.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();
        }
        return total != null && total > 0;
    }

    public List<Aluno> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Aluno", Aluno.class).list();
        }
    }

    public boolean removerPorCpf(String cpf) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Aluno aluno = session.createQuery("from Aluno where cpf = :cpf", Aluno.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();

            if (aluno == null) {
                tx.commit();
                return false;
            }

            session.remove(aluno);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public Aluno atualizar(Aluno aluno) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Aluno atualizado = (Aluno) session.merge(aluno);
            tx.commit();
            return atualizado;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public Aluno autenticar(String cpf, String senha) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Aluno where cpf = :cpf and senha = :senha", Aluno.class)
                    .setParameter("cpf", cpf)
                    .setParameter("senha", senha)
                    .uniqueResult();
        }
    }
}
