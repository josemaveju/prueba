package es.academia.modelo;

// Generated 21-jul-2014 23:03:03 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Iterator;
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
 * Home object for domain model class Materia.
 * @see es.academia.modelo.Materia
 * @author Hibernate Tools
 */
public class MateriaHome {

	private static final Logger log = ACALog.getLogger(MateriaHome.class);

//	private final SessionFactory sessionFactory = getSessionFactory();
	
    private List profesores = new LinkedList();

	protected SessionFactory getSessionFactory() {
    	return HibernateUtil.getSessionFactory();
    }
	

/*	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
*/
	public void persist(Materia transientInstance) {
		
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

	public void attachDirty(Materia instance) {
		log.debug("attaching dirty Materia instance");
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

	public void attachClean(Materia instance) {
		log.debug("attaching clean Materia instance");
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

	public void delete(Materia persistentInstance) {
		log.debug("deleting Materia instance");
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

	public Materia merge(Materia detachedInstance) {
		log.debug("merging Materia instance");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			Materia result = (Materia) sesion.merge(detachedInstance);
			log.debug("merge successful");
			sesion.getTransaction().commit();
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			sesion.getTransaction().rollback();
			throw re;
		}
	}

	public Materia findById(java.lang.Integer id) {
        log.debug("getting Materia instance with id: " + id);
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
       try {
    	   Materia instance = (Materia) sesion
                    .get("es.academia.modelo.Materia", id);
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

	public List findByExample(Materia instance) {
	       log.debug("finding Materia instance by example");
	        Session sesion = getSessionFactory().getCurrentSession();
	        sesion.beginTransaction();
	        try {
	        	Criteria crit =sesion.createCriteria("es.academia.modelo.Materia");
	        	if (instance.getIdMateria() != null)
	        		crit.add(Restrictions.eq( "idMateria", instance.getIdMateria() ));
	        	if (instance.getDescMateria() != null)
	        		crit.add(Restrictions.like("nombre", "%"+instance.getDescMateria()+"%"));
	        	
	        	List<Materia> results =crit.list();
	        	
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

    public List<Materia> listarTodos(){

        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();

        List<Materia> result=(List<Materia>)sesion.createQuery("from Materia").list();

        sesion.getTransaction().commit();
        return result;
    }


}
