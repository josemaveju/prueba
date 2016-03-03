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
 * Home object for domain model class Curso.
 * @see es.academia.modelo.Curso
 * @author Hibernate Tools
 */
public class CursoHome {

	private static final Logger log = ACALog.getLogger(CursoHome.class);


	protected SessionFactory getSessionFactory() {
    	return HibernateUtil.getSessionFactory();
    }

	public void persist(Curso transientInstance) {
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

	public void attachDirty(Curso instance) {
		log.debug("attaching dirty Curso instance");
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Curso instance) {
		log.debug("attaching clean Curso instance");
		try {
			getSessionFactory().getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Curso persistentInstance) {
		log.debug("deleting Curso instance");
		try {
			getSessionFactory().getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Curso merge(Curso detachedInstance) {
		log.debug("merging Curso instance");
		try {
			Curso result = (Curso) getSessionFactory().getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Curso findById(java.lang.Integer id) {
        log.debug("getting Curso instance with id: " + id);
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
       try {
    	   Curso instance = (Curso) sesion
                    .get("es.academia.modelo.Curso", id);
            if (instance==null) {
                log.debug("get successful, no instance found");
            }
            else {
                log.debug("get successful, instance found");
            }
            sesion.getTransaction().commit();
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            sesion.getTransaction().rollback();
            throw re;
        }
	}

	public List findByExample(Curso instance) {
        log.debug("finding Curso instance by example");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
        try {
        	Criteria crit =sesion.createCriteria("es.academia.modelo.Curso");
        	if (instance.getIdCurso() != null)
        		crit.add(Restrictions.eq( "idAlumno", instance.getIdCurso() ));
        	if (instance.getDescCurso() != null)
        		crit.add(Restrictions.like("nombre", "%"+instance.getDescCurso()+"%"));
        	if (instance.getProfesor().getIdProfesor() != 0)
        		crit.add(Restrictions.like("apellidos", "%"+instance.getProfesor().getIdProfesor()+"%"));
        	
        	List<Curso> results =crit.list();
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
	
    public List<Curso> listarTodos(){

        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();

        List<Curso> result=(List<Curso>)sesion.createQuery("from Curso").list();

        sesion.getTransaction().commit();
        return result;
    }
    
	
}
