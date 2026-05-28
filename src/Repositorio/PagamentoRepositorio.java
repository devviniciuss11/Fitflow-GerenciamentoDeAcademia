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

            Aluno aluno = session.get(Aluno.class, pagamento.getAluno().getId());
            Plano plano = session.get(Plano.class, pagamento.getPlano().getId());
            pagamento.setAluno(aluno);
            pagamento.setPlano(plano);

            session.persist(pagamento);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao executar operacao de persistencia.", e);
        }
    }

    public List<Pagamento> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select p from Pagamento p " +
                            "left join fetch p.aluno " +
                            "left join fetch p.plano", Pagamento.class
            ).list();
        }
    }

    public Pagamento buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select p from Pagamento p " +
                                    "left join fetch p.aluno " +
                                    "left join fetch p.plano " +
                                    "where p.id = :id", Pagamento.class
                    )
                    .setParameter("id", id)
                    .uniqueResult();
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

            Aluno aluno = session.get(Aluno.class, pagamentoAtualizado.getAluno().getId());
            Plano plano = session.get(Plano.class, pagamentoAtualizado.getPlano().getId());
            pagamento.setAluno(aluno);
            pagamento.setPlano(plano);
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

    public boolean existePagamentoPagoParaAlunoEPlanoExcetoId(int alunoId, int planoId, int idIgnorado) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Pagamento p " +
                                    "where p.aluno.id = :alunoId " +
                                    "and p.plano.id = :planoId " +
                                    "and p.status = :status " +
                                    "and p.id <> :idIgnorado", Long.class
                    )
                    .setParameter("alunoId", alunoId)
                    .setParameter("planoId", planoId)
                    .setParameter("status", Pagamento.StatusPagamento.PAGO)
                    .setParameter("idIgnorado", idIgnorado)
                    .uniqueResult();

            return total != null && total > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar pagamentos pagos do aluno/plano.", e);
        }
    }

    public boolean existePorAlunoId(int alunoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Pagamento p where p.aluno.id = :alunoId",
                            Long.class
                    )
                    .setParameter("alunoId", alunoId)
                    .uniqueResult();
            return total != null && total > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar pagamentos do aluno.", e);
        }
    }

    public boolean existePorPlanoId(int planoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery(
                            "select count(p.id) from Pagamento p where p.plano.id = :planoId",
                            Long.class
                    )
                    .setParameter("planoId", planoId)
                    .uniqueResult();
            return total != null && total > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar pagamentos do plano.", e);
        }
    }
}
