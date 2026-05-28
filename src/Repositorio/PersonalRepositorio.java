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

    public boolean existePorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Personal p where p.cpf = :cpf", Long.class
                    )
                    .setParameter("cpf", cpf.trim())
                    .uniqueResult();
            return total != null && total > 0;
        }
    }

    public boolean existePorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Personal p where lower(trim(p.email)) = :email", Long.class
                    )
                    .setParameter("email", email.trim().toLowerCase())
                    .uniqueResult();
            return total != null && total > 0;
        }
    }

    public boolean existePorCraf(String craf) {
        if (craf == null || craf.trim().isEmpty()) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Personal p where lower(trim(p.craf)) = :craf", Long.class
                    )
                    .setParameter("craf", craf.trim().toLowerCase())
                    .uniqueResult();
            return total != null && total > 0;
        }
    }

    public boolean existePorCpfExcetoId(String cpf, Integer idIgnorado) {
        if (cpf == null || cpf.trim().isEmpty() || idIgnorado == null) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Personal p where p.cpf = :cpf and p.id <> :idIgnorado", Long.class
                    )
                    .setParameter("cpf", cpf.trim())
                    .setParameter("idIgnorado", idIgnorado)
                    .uniqueResult();
            return total != null && total > 0;
        }
    }

    public boolean existePorEmailExcetoId(String email, Integer idIgnorado) {
        if (email == null || email.trim().isEmpty() || idIgnorado == null) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Personal p where lower(trim(p.email)) = :email and p.id <> :idIgnorado",
                            Long.class
                    )
                    .setParameter("email", email.trim().toLowerCase())
                    .setParameter("idIgnorado", idIgnorado)
                    .uniqueResult();
            return total != null && total > 0;
        }
    }

    public boolean existePorCrafExcetoId(String craf, Integer idIgnorado) {
        if (craf == null || craf.trim().isEmpty() || idIgnorado == null) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Personal p where lower(trim(p.craf)) = :craf and p.id <> :idIgnorado",
                            Long.class
                    )
                    .setParameter("craf", craf.trim().toLowerCase())
                    .setParameter("idIgnorado", idIgnorado)
                    .uniqueResult();
            return total != null && total > 0;
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
