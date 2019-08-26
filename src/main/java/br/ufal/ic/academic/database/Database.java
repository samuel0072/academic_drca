package br.ufal.ic.academic.database;

import br.ufal.ic.academic.model.Model;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Database extends AbstractDAO<Model>{


    public Database(SessionFactory factory) {
        super(factory);
    }

    public Model findById(Long id) {
        return get(id);
    }

    public Model create(Model object) {
        if(object.isOkay()) {
            return persist(object);
        }
        return null;
    }

    public<T> List<T> findAll(Class<T> clazz) {
        CriteriaQuery crit = criteriaQuery();
        Root<T> root = crit.from(clazz);
        crit.select(root);

        return super.list(crit);
    }

    public void deleteById(Long id) {
        currentSession().delete(findById(id));
    }

    public void update(Model s) {
        if(s.isOkay()) {
            currentSession().saveOrUpdate(s);
        }
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
