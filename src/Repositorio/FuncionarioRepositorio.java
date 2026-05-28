package Repositorio;

import Entidade.Funcionario;
import Infra.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FuncionarioRepositorio {

    public void Save(Funcionario funcionario) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            funcionario.setId(null);
            session.persist(funcionario);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public List<Funcionario> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Funcionario", Funcionario.class).list();
        }
    }

    public Funcionario buscarPorCpf(String cpf) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Funcionario where cpf = :cpf", Funcionario.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();
        }
    }

    public boolean existePorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(f.id) from Funcionario f where f.cpf = :cpf", Long.class
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
                            "select count(f.id) from Funcionario f where lower(trim(f.email)) = :email", Long.class
                    )
                    .setParameter("email", email.trim().toLowerCase())
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
                            "select count(f.id) from Funcionario f where f.cpf = :cpf and f.id <> :idIgnorado",
                            Long.class
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
                            "select count(f.id) from Funcionario f where lower(trim(f.email)) = :email and f.id <> :idIgnorado",
                            Long.class
                    )
                    .setParameter("email", email.trim().toLowerCase())
                    .setParameter("idIgnorado", idIgnorado)
                    .uniqueResult();
            return total != null && total > 0;
        }
    }

    public boolean removerPorCpf(String cpf) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Funcionario funcionario = session.createQuery("from Funcionario where cpf = :cpf", Funcionario.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();
            if (funcionario == null) {
                tx.commit();
                return false;
            }
            session.remove(funcionario);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public Funcionario atualizar(Funcionario funcionario) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Funcionario atualizado = (Funcionario) session.merge(funcionario);
            tx.commit();
            return atualizado;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public Funcionario autenticar(String cpf, String senha) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Funcionario where cpf = :cpf and senha = :senha", Funcionario.class)
                    .setParameter("cpf", cpf)
                    .setParameter("senha", senha)
                    .uniqueResult();
        }
    }
}
