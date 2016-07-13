package es.academia.modelo;

// Generated 21-jul-2014 23:03:03 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

/**
 * Home object for domain model class Matricula.
 * @see es.academia.modelo.Matricula
 * @author Hibernate Tools
 */
public class MatriculaHome {

	private static final Log log = LogFactory.getLog(MatriculaHome.class);

//	private final SessionFactory sessionFactory = getSessionFactory();

    protected SessionFactory getSessionFactory() {
    	return HibernateUtil.getSessionFactory();
}

	public void persist(Matricula matricula) {
		persist (matricula,null);
	}

    public void persist(Matricula matricula, Session sesion) {
		log.debug("persisting Matricula instance");
		boolean hacerCommit =false;
        if (sesion == null){
        	sesion = getSessionFactory().getCurrentSession();
            sesion.beginTransaction();
            hacerCommit=true;
        }
		try {
			sesion.saveOrUpdate(matricula);
			log.debug("persist successful");
			if (hacerCommit)
				sesion.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			if (hacerCommit)
				sesion.getTransaction().rollback();
			throw re;
		}
	}

	public void attachDirty(Matricula instance) {
		log.debug("attaching dirty Matricula instance");
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Matricula instance) {
		log.debug("attaching clean Matricula instance");
		try {
			getSessionFactory().getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Matricula persistentInstance) {
		log.debug("deleting Matricula instance");
		try {
			getSessionFactory().getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Matricula merge(Matricula detachedInstance) {
		log.debug("merging Matricula instance");
		try {
			Matricula result = (Matricula) getSessionFactory().getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Matricula findById(java.lang.Integer id) {
		log.debug("getting Matricula instance with id: " + id);
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			Matricula instance = (Matricula) sesion.get("es.academia.modelo.Matricula", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			sesion.getTransaction().commit();
			return instance;
		} catch (RuntimeException re) {
	            log.error("get failed", re);
	        	sesion.getTransaction().rollback();
	            throw re;
	        }
	}

	public List findByExample(Matricula instance) {
        log.debug("finding Alumno instance by example");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
        try {
        	Criteria crit =sesion.createCriteria("es.academia.modelo.Alumno");
        	if (instance.getCurso() != null)
        		crit.add(Restrictions.eq( "idCurso", instance.getCurso() ));
        	if (instance.getAlumno() != null)
        		crit.add(Restrictions.like("idAlumno", instance.getAlumno()));
        	List<Matricula> results =crit.list();
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
	
    public List<Matricula> listarTodos(){

        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();

        List<Matricula> result=(List<Matricula>)sesion.createQuery("from Matricula").list();

        sesion.getTransaction().commit();
        return result;
    }
    
    public List<Recibo> getRecibos(Matricula matricula){
    	Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
    	Query query = sesion.createQuery("select r from Recibo as r where r.idMatricula = :matricula");
    	query.setParameter("matricula", matricula);
    	
    	List<Recibo> resultado = (List<Recibo>)query.list();
        sesion.getTransaction().commit();
        
        return resultado;
    } 
    
	
}
