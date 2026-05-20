package Repositorio;

import Entidade.Personal;
import Infra.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonalRepositorio {

    public Personal buscarPorIdper(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Personal.class, id);
        }
    }

    public Personal buscarPorCraf(String craf) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Personal where craf = :craf", Personal.class)
                    .setParameter("craf", craf)
                    .uniqueResult();
        }
    }

    public void save(Personal personal) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            personal.setId(null);
            session.persist(personal);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public List<Personal> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Personal", Personal.class).list();
        }
    }

    public boolean removerPorCraf(String craf) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Personal personal = session.createQuery("from Personal where craf = :craf", Personal.class)
                    .setParameter("craf", craf)
                    .uniqueResult();

            if (personal == null) {
                tx.commit();
                return false;
            }

            session.remove(personal);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public Personal atualizar(Personal personal) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Personal atualizado = (Personal) session.merge(personal);
            tx.commit();
            return atualizado;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public Personal autenticarPorCpfOuCraf(String cpfOuCraf, String senha) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Personal where (cpf = :id or craf = :id) and senha = :senha", Personal.class)
                    .setParameter("id", cpfOuCraf)
                    .setParameter("senha", senha)
                    .uniqueResult();
        }
    }
}