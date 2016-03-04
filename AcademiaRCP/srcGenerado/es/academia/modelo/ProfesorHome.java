package es.academia.modelo;

// Generated 21-jul-2014 23:03:03 by Hibernate Tools 3.4.0.CR1

import java.util.LinkedList;
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
 * Home object for domain model class Profesor.
 * @see es.academia.modelo.Profesor
 * @author Hibernate Tools
 */
public class ProfesorHome {

	private static final Logger log = ACALog.getLogger(ProfesorHome.class);

//	private final SessionFactory sessionFactory = getSessionFactory();
	
    private List profesores = new LinkedList();

	protected SessionFactory getSessionFactory() {
    	return HibernateUtil.getSessionFactory();
    }

	public void persist(Profesor transientInstance) {
		log.debug("persisting Profesor instance");
		
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

	public void attachDirty(Profesor instance) {
		log.debug("attaching dirty Profesor instance");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			sesion.saveOrUpdate(instance);
			log.debug("attach successful");
			sesion.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			sesion.getTransaction().rollback();
			throw re;
		}
	}

	public void attachClean(Profesor instance) {
		log.debug("attaching clean Profesor instance");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			sesion.lock(instance, LockMode.NONE);
			log.debug("attach successful");
			sesion.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			sesion.getTransaction().rollback();
			throw re;
		}
	}

	public void delete(Profesor persistentInstance) {
		log.debug("deleting Profesor instance");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			sesion.delete(persistentInstance);
			log.debug("delete successful");
			sesion.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			sesion.getTransaction().rollback();
			throw re;
		}
	}

	public Profesor merge(Profesor detachedInstance) {
		log.debug("merging Profesor instance");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			Profesor result = (Profesor) sesion.merge(detachedInstance);
			log.debug("merge successful");
			sesion.getTransaction().commit();
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			sesion.getTransaction().rollback();
			throw re;
		}
	}

	public Profesor findById(java.lang.Integer id) {
        log.debug("getting Profesor instance with id: " + id);
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
       try {
    	   Profesor instance = (Profesor) sesion
                    .get("es.academia.modelo.Profesor", id);
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

	public List findByExample(Profesor instance) {
        log.debug("finding Profesor instance by example");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
        try {
        	Criteria crit =sesion.createCriteria("es.academia.modelo.Profesor");
        	if (instance.getIdProfesor() != null)
        		crit.add(Restrictions.eq( "idAlumno", instance.getIdProfesor() ));
        	if (instance.getNombre() != null)
        		crit.add(Restrictions.like("nombre", "%"+instance.getNombre()+"%"));
        	if (instance.getApellidos()!= null)
        		crit.add(Restrictions.like("apellidos", "%"+instance.getApellidos()+"%"));
        	if (instance.getNif() != null)
        		crit.add(Restrictions.like("nif", instance.getNif()+"%"));
        	
        	List<Profesor> results =crit.list();
        	
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

    public List<Profesor> listarTodos(){

        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();

        List<Profesor> result=(List<Profesor>)sesion.createQuery("from Profesor").list();

        sesion.getTransaction().commit();
        return result;
    }
	
}
