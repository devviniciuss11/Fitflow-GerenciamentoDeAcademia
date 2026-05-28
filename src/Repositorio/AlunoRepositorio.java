package Repositorio;

import Entidade.Aluno;
import Entidade.Plano;
import Entidade.Treino;
import Infra.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        Long total;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            total = session.createQuery("select count(a.id) from Aluno a where a.cpf = :cpf", Long.class)
                    .setParameter("cpf", cpf.trim())
                    .uniqueResult();
        }
        return total != null && total > 0;
    }

    public boolean existePorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        Long total;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            total = session.createQuery(
                            "select count(a.id) from Aluno a where lower(trim(a.email)) = :email",
                            Long.class
                    )
                    .setParameter("email", email.trim().toLowerCase())
                    .uniqueResult();
        }
        return total != null && total > 0;
    }

    public boolean existePorCpfExcetoId(String cpf, Integer idIgnorado) {
        if (cpf == null || cpf.trim().isEmpty() || idIgnorado == null) {
            return false;
        }

        Long total;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            total = session.createQuery(
                            "select count(a.id) from Aluno a where a.cpf = :cpf and a.id <> :idIgnorado",
                            Long.class
                    )
                    .setParameter("cpf", cpf.trim())
                    .setParameter("idIgnorado", idIgnorado)
                    .uniqueResult();
        }
        return total != null && total > 0;
    }

    public boolean existePorEmailExcetoId(String email, Integer idIgnorado) {
        if (email == null || email.trim().isEmpty() || idIgnorado == null) {
            return false;
        }

        Long total;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            total = session.createQuery(
                            "select count(a.id) from Aluno a where lower(trim(a.email)) = :email and a.id <> :idIgnorado",
                            Long.class
                    )
                    .setParameter("email", email.trim().toLowerCase())
                    .setParameter("idIgnorado", idIgnorado)
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

    public void vincularPlanoAoAlunoSeAindaNaoExiste(int alunoId, int planoId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Aluno aluno = session.get(Aluno.class, alunoId);
            Plano plano = session.get(Plano.class, planoId);
            if (aluno == null || plano == null) {
                tx.commit();
                return;
            }

            Number total = (Number) session.createNativeQuery(
                            "select count(1) from aluno_plano where aluno_id = :alunoId and plano_id = :planoId")
                    .setParameter("alunoId", alunoId)
                    .setParameter("planoId", planoId)
                    .getSingleResult();

            if (total != null && total.longValue() == 0L) {
                session.createNativeQuery(
                                "insert into aluno_plano (aluno_id, plano_id) values (:alunoId, :planoId)")
                        .setParameter("alunoId", alunoId)
                        .setParameter("planoId", planoId)
                        .executeUpdate();
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao vincular plano ao aluno.", e);
        }
    }

    public void desvincularPlanoAoAlunoSeExistir(int alunoId, int planoId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Aluno aluno = session.get(Aluno.class, alunoId);
            Plano plano = session.get(Plano.class, planoId);
            if (aluno == null || plano == null) {
                tx.commit();
                return;
            }

            session.createNativeQuery(
                            "delete from aluno_plano where aluno_id = :alunoId and plano_id = :planoId")
                    .setParameter("alunoId", alunoId)
                    .setParameter("planoId", planoId)
                    .executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao desvincular plano do aluno.", e);
        }
    }

    public void vincularTreinoAoAlunoSeAindaNaoExiste(int alunoId, int treinoId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Aluno aluno = session.get(Aluno.class, alunoId);
            Treino treino = session.get(Treino.class, treinoId);
            if (aluno == null || treino == null) {
                tx.commit();
                return;
            }

            Number total = (Number) session.createNativeQuery(
                            "select count(1) from aluno_treino where aluno_id = :alunoId and treino_id = :treinoId")
                    .setParameter("alunoId", alunoId)
                    .setParameter("treinoId", treinoId)
                    .getSingleResult();

            if (total != null && total.longValue() == 0L) {
                session.createNativeQuery(
                                "insert into aluno_treino (aluno_id, treino_id) values (:alunoId, :treinoId)")
                        .setParameter("alunoId", alunoId)
                        .setParameter("treinoId", treinoId)
                        .executeUpdate();
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao vincular treino ao aluno.", e);
        }
    }

    public void desvincularTreinoDoAlunoSeExistir(int alunoId, int treinoId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Aluno aluno = session.get(Aluno.class, alunoId);
            Treino treino = session.get(Treino.class, treinoId);
            if (aluno == null || treino == null) {
                tx.commit();
                return;
            }

            session.createNativeQuery(
                            "delete from aluno_treino where aluno_id = :alunoId and treino_id = :treinoId")
                    .setParameter("alunoId", alunoId)
                    .setParameter("treinoId", treinoId)
                    .executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao desvincular treino do aluno.", e);
        }
    }
}
