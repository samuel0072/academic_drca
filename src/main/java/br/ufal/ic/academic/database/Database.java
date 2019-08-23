package br.ufal.ic.academic.database;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Database<T> extends AbstractDAO<T>{


    public Database(SessionFactory factory) {
        super(factory);
    }

    public T findById(Class<T> clazz, Long id) {
        CriteriaBuilder cr = super.currentSession().getCriteriaBuilder();
        CriteriaQuery crit = criteriaQuery();
        Root<T> root = crit.from(clazz);
        crit.select(root).where(cr.equal(root.get("id"), id));

        return (T) uniqueResult(crit);
    }

    public T create(T object) {
        return persist(object);
    }

    public List<T> findAll(Class<T> clazz) {
        CriteriaQuery crit = criteriaQuery();
        Root<T> root = crit.from(clazz);
        crit.select(root);

        return super.list(crit);
    }

    public void deleteById(Class<T> clazz, Long id) {
        currentSession().delete(findById(clazz, id));
    }

    public void updateById(T s) {
        currentSession().saveOrUpdate(s);
    }

    public<G> G excuteQuery(String query, boolean multiple, Class<G> clazz) {
        Session session = currentSession().getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();
        G result;
        try {

            if(multiple) {
                result = (G) session.createQuery(query).getResultList();
            }
            else {
                result = (G) session.createQuery(query).getSingleResult();
            }
            trans.commit();
        }
        catch(Exception e) {
            result = null;
        }
        return result;

    }

}
