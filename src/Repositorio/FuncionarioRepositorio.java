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
