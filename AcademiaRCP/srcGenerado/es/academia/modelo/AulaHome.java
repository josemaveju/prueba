package es.academia.modelo;

// Generated 21-jul-2014 23:03:03 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import es.academia.utils.ACALog;

/**
 * Home object for domain model class Aula.
 * @see es.academia.modelo.Aula
 * @author Hibernate Tools
 */
public class AulaHome {

	private static final Logger log = ACALog.getLogger(AulaHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
    	return HibernateUtil.getSessionFactory();
    }

	public void persist(Aula transientInstance) {
		log.debug("persisting Alumno instance");
		
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

	public void attachDirty(Aula instance) {
		log.debug("attaching dirty Aula instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Aula instance) {
		log.debug("attaching clean Aula instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Aula persistentInstance) {
        log.debug("Borrando el aula: " + persistentInstance.getIdAula());
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("Registro borrado correctamente");
			sesion.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("Error borrando el registro", re);
			sesion.getTransaction().rollback();
			throw re;
		}
	}

	public Aula merge(Aula detachedInstance) {
		log.debug("merging Aula instance");
		try {
			Aula result = (Aula) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Aula findById(java.lang.Integer id) {
        log.debug("getting Alumno instance with id: " + id);
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
       try {
    	   Aula instance = (Aula) sesion.get("es.academia.modelo.Aula", id);
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

	public List findByExample(Aula instance) {
        log.debug("finding Aula instance by example");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("es.academia.modelo.Aula")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
        finally{
        	sesion.getTransaction().commit();
        }
	}
	
	public List<Aula> listarTodos(){

	        Session sesion = getSessionFactory().getCurrentSession();
	        sesion.beginTransaction();

	        List<Aula> result=(List<Aula>)sesion.createQuery("from Aula").list();

	        sesion.getTransaction().commit();
	        return result;
	   }
	
}
