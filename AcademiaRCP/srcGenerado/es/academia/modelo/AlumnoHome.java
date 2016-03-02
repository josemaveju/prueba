package es.academia.modelo;

// Generated 21-jul-2014 23:03:03 by Hibernate Tools 3.4.0.CR1system


import static org.hibernate.criterion.Example.create;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import static org.hibernate.criterion.Example.create;
import es.academia.modelo.HibernateUtil;
import es.academia.modelo.Alumno;
import es.academia.utils.ACALog;

/**
 * Home object for domain model class Alumno.
 * @see es.academia.modelo.Alumno
 * @author Hibernate Tools
 */
public class AlumnoHome {

	private static final Logger log = ACALog.getLogger(AlumnoHome.class);

    private List alumnos = new LinkedList();

    protected SessionFactory getSessionFactory() {
    	return HibernateUtil.getSessionFactory();
}

	public void persist(Alumno transientInstance) {
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

	public void attachDirty(Alumno instance) {
		log.debug("attaching dirty Alumno instance");
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Alumno instance) {
		log.debug("attaching clean Alumno instance");
		try {
			getSessionFactory().getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Alumno persistentInstance) {
		log.debug("deleting Alumno instance");
		try {
			getSessionFactory().getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Alumno merge(Alumno detachedInstance) {
		log.debug("merging Alumno instance");
		try {
			Alumno result = (Alumno) getSessionFactory().getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Alumno findById(java.lang.Integer id) {
        log.debug("getting Alumno instance with id: " + id);
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
       try {
            Alumno instance = (Alumno) sesion
                    .get("es.academia.modelo.Alumno", id);
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

	public List findByExample(Alumno instance) {
        log.debug("finding Alumno instance by example");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
        try {
        	Criteria crit =sesion.createCriteria("es.academia.modelo.Alumno");
        	if (instance.getIdAlumno() != null)
        		crit.add(Restrictions.eq( "idAlumno", instance.getIdAlumno() ));
        	if (instance.getNombre() != null)
        		crit.add(Restrictions.like("nombre", "%"+instance.getNombre()+"%"));
        	if (instance.getApellidos()!= null)
        		crit.add(Restrictions.like("apellidos", "%"+instance.getApellidos()+"%"));
        	if (instance.getNif() != null)
        		crit.add(Restrictions.like("nif", instance.getNif()+"%"));
        	
        	List<Alumno> results =crit.list();
        	log.debug("find by example successful, result size: " + results.size());
        	sesion.getTransaction().commit();
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            sesion.getTransaction().rollback();
            throw re;
        }
	}
    public List<Alumno> listarTodos(){

        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();

        List<Alumno> result=(List<Alumno>)sesion.createQuery("from Alumno").list();

        sesion.getTransaction().commit();
        return result;
    }
    
    public List<Recibo> getRecibos(Alumno alumno){
    	Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
    	Query query = sesion.createQuery("select r from Recibo as r, Matricula as m where r.idMatricula = m.idMatricula and  m.alumno=:alumno");
//    	Query query = sesion.createQuery("select m.recibos from Matricula as m where m.alumno=:alumno");    	
    	query.setParameter("alumno", alumno);
    	
    	List<Recibo> resultado = (List<Recibo>)query.list();
        sesion.getTransaction().commit();
        
        return resultado;
    }
 	
}
