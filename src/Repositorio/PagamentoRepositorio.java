package Repositorio;

import Entidade.Aluno;
import Entidade.Pagamento;
import Entidade.Plano;
import Infra.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PagamentoRepositorio {

    public void salvar(Pagamento pagamento) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            pagamento.setId(null);

            Aluno alunoRef = session.getReference(Aluno.class, pagamento.getAluno().getId());
            Plano planoRef = session.getReference(Plano.class, pagamento.getPlano().getId());
            pagamento.setAluno(alunoRef);
            pagamento.setPlano(planoRef);

            session.persist(pagamento);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public List<Pagamento> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Pagamento", Pagamento.class).list();
        }
    }

    public Pagamento buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Pagamento.class, id);
        }
    }

    public boolean remover(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Pagamento pagamento = session.get(Pagamento.class, id);
            if (pagamento == null) {
                tx.commit();
                return false;
            }
            session.remove(pagamento);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public boolean atualizar(Pagamento pagamentoAtualizado) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Pagamento pagamento = session.get(Pagamento.class, pagamentoAtualizado.getId());
            if (pagamento == null) {
                tx.commit();
                return false;
            }

            Aluno alunoRef = session.getReference(Aluno.class, pagamentoAtualizado.getAluno().getId());
            Plano planoRef = session.getReference(Plano.class, pagamentoAtualizado.getPlano().getId());
            pagamento.setAluno(alunoRef);
            pagamento.setPlano(planoRef);
            pagamento.setStatus(pagamentoAtualizado.getStatus());
            pagamento.setMetodoPagamento(pagamentoAtualizado.getMetodoPagamento());
            pagamento.setValor(pagamentoAtualizado.getValor());
            pagamento.setDataPagamento(pagamentoAtualizado.getDataPagamento());
            pagamento.setDataVencimento(pagamentoAtualizado.getDataVencimento());
            session.merge(pagamento);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }
}
