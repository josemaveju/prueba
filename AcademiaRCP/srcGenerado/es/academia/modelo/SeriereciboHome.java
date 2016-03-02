package es.academia.modelo;

// Generated 21-jul-2014 23:03:03 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import es.academia.utils.ACALog;

/**
 * Home object for domain model class Serierecibo.
 * @see es.academia.modelo.Serierecibo
 * @author Hibernate Tools
 */
public class SeriereciboHome {

	private static final Logger log = ACALog.getLogger(SeriereciboHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
    	return HibernateUtil.getSessionFactory();
    }

	public void persist(Serierecibo transientInstance) {
		log.debug("persistiendo instancia de SerieRecibo");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			sesion.saveOrUpdate(transientInstance);
			log.debug("persist OK");
			sesion.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("persist falló", re);
			sesion.getTransaction().rollback();
			throw re;
		}
	}

	public void attachDirty(Serierecibo instance) {
		log.debug("attaching dirty SerieRecibo instance");
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Serierecibo instance) {
		log.debug("attaching clean Serierecibo instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Serierecibo persistentInstance) {
        log.debug("Borrando la serierecibo: " + persistentInstance.getIdSerie());
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

	public Serierecibo merge(Serierecibo detachedInstance) {
		log.debug("merging Serierecibo instance");
		try {
			Serierecibo result = (Serierecibo) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Serierecibo findById(java.lang.Integer id) {
        log.debug("getting Serierecibo instance with id: " + id);
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
       try {
    	   Serierecibo instance = (Serierecibo) sesion.get("es.academia.modelo.Serierecibo", id);
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

	public List findByExample(Serierecibo instance) {
        log.debug("finding Alumno instance by example");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("es.academia.modelo.Serierecibo")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
        finally{
        	sesion.getTransaction().commit();
        }
	}
	
    public List<Serierecibo> listarTodos(){

        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();

        List<Serierecibo> result=(List<Serierecibo>)sesion.createQuery("from Serierecibo").list();

        sesion.getTransaction().commit();
        return result;
    
    }
    
    public Serierecibo obtenerSerieDefecto(){

    	Serierecibo result;
    	
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();

        List<Serierecibo> lista=(List<Serierecibo>)sesion.createQuery("from Serierecibo where defecto='S'").list();
 
        sesion.getTransaction().commit();

        if (!lista.isEmpty())
        	result = (Serierecibo) lista.get(0);
        else
        	result = new Serierecibo();

        return result;
    }

}
