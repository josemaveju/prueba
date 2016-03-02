package es.academia.modelo;

// Generated 21-jul-2014 23:03:03 by Hibernate Tools 3.4.0.CR1

import static org.hibernate.criterion.Example.create;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Conocimiento.
 * @see es.academia.modelo.Conocimiento
 * @author Hibernate Tools
 */
public class ConocimientoHome {

	private static final Log log = LogFactory.getLog(ConocimientoHome.class);
	
    private List conocimientos = new LinkedList();


	protected SessionFactory getSessionFactory() {
    	return HibernateUtil.getSessionFactory();
    }

	public void persist(Conocimiento transientInstance) {
		log.debug("persisting Conocimiento instance");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			sesion.saveOrUpdate(transientInstance);
			log.debug("persist successful");
			sesion.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			sesion.getTransaction().rollback();
			throw re;
		}
	}

	public void attachDirty(Conocimiento instance) {
		log.debug("attaching dirty Conocimiento instance");
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Conocimiento instance) {
		log.debug("attaching clean Conocimiento instance");
		try {
			getSessionFactory().getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Conocimiento persistentInstance) {
		log.debug("deleting Conocimiento instance");
		try {
			getSessionFactory().getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Conocimiento merge(Conocimiento detachedInstance) {
		log.debug("merging Conocimiento instance");
		try {
			Conocimiento result = (Conocimiento) getSessionFactory().
					getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Conocimiento findById(java.lang.Integer id) {
		log.debug("getting Conocimiento instance with id: " + id);
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
       try {
            Conocimiento instance = (Conocimiento) sesion
                    .get("es.academia.modelo.Conocimiento", id);
            if (instance==null) {
                log.debug("get successful, no instance found");
            }
            else {
                log.debug("get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
       finally{
       	sesion.getTransaction().commit();
       }
	}

	public List findByExample(Conocimiento instance) {
		log.debug("finding Conocimiento instance by example");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
        try {
            List<Conocimiento> results = (List<Conocimiento>) sesion
                    .createCriteria("es.academia.modelo.Conocimiento")
                    .add( create(instance) )
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
        finally{
        	sesion.getTransaction().commit();
        }
	}
	
    public List<Conocimiento> listarTodos(){

        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();

        List<Conocimiento> result=(List<Conocimiento>)sesion.createQuery("from Conocimiento").list();

        sesion.getTransaction().commit();
        return result;
    }
    
    public List<Integer> listarTodosInteger(){
    	List<Conocimiento> lstCono = listarTodos();
    	List<Integer> lstInteger = new ArrayList<Integer>();
    	
    	Iterator it = lstCono.iterator();
    	while (it.hasNext())
    		lstInteger.add(((Conocimiento)it.next()).getIdConocimiento());
    
    	return lstInteger;
    }
	
}
