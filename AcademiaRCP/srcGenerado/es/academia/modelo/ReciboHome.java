package es.academia.modelo;

// Generated 21-jul-2014 23:03:03 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
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
 * Home object for domain model class Recibo.
 * @see es.academia.modelo.Recibo
 * @author Hibernate Tools
 */
public class ReciboHome {

	private static final Logger log = ACALog.getLogger(ReciboHome.class);

	protected SessionFactory getSessionFactory() {
    	return HibernateUtil.getSessionFactory();
    }
	
	
	public void abrirTransaccion(Session sesion){
		sesion.beginTransaction();
	}
	
	public void hacerCommit(Session sesion){
		sesion.getTransaction().commit();
	}
	
	public void hacerRollback(Session sesion){
		sesion.getTransaction().rollback();
	}
	
	public Session obtenerSesion(){
		Session sesion = getSessionFactory().getCurrentSession();
		return sesion;
	}
	
	public void actSinCommit(Recibo rec, Session sesion) throws RuntimeException{

			sesion.saveOrUpdate(rec);

	}
	public void persist(Recibo recibo) {
		persist (recibo,null);
	}
	
	public void persist(Recibo recibo, Session sesion) {
		log.debug("persisting Recibo instance");
		boolean hacerCommit =false;
		 
        if (sesion == null){
        	sesion = getSessionFactory().getCurrentSession();
            sesion.beginTransaction();
            hacerCommit=true;
        }
        
		try {
			sesion.saveOrUpdate(recibo);
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

	public void delete(Recibo persistentInstance) {
		log.debug("deleting Recibo instance");
		try {
			getSessionFactory().getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Recibo merge(Recibo detachedInstance) {
		log.debug("merging Recibo instance");
		try {
			Recibo result = (Recibo) getSessionFactory().getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Recibo findById(java.lang.Integer id) {
	       log.debug("getting Recibo instance with id: " + id);
	        Session sesion = getSessionFactory().getCurrentSession();
//	        sesion.beginTransaction();
	       try {
	    	   Recibo instance = (Recibo) sesion
	                    .get("es.academia.modelo.Recibo", id);
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
//	       	sesion.getTransaction().commit();
	       }
	}

	public List findByExample(Recibo instance) {
		log.debug("finding Recibo instance by example");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
		try {
			List results = getSessionFactory().getCurrentSession()
					.createCriteria("es.academia.modelo.Recibo")
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

	public List findListaRecibos(Date mes, boolean todos, boolean curso, Integer idCurso) {
        log.debug("Obteniendo la lista de recibos.");
        Session sesion = getSessionFactory().getCurrentSession();
        sesion.beginTransaction();
        try {
        	Criteria crit =sesion.createCriteria("es.academia.modelo.Recibo");
        	
        	crit.add(Restrictions.eq("estadoRecibo",Recibo.ESTADOPENDIENTE));
        	crit.add(Restrictions.and(Restrictions.le("FDesde", mes),Restrictions.ge("FHasta",mes)));

        	if (curso){
        		CursoHome curH = new CursoHome();
        		Curso cur = curH.findById(idCurso);

        		crit.add(Restrictions.in("matricula", cur.getMatriculas()));
        	}
/*        		crit.add(Restrictions.sqlRestriction(
        				"idMatricula in (select idMatricula from matricula where idCurso =?"
        				 ,idCurso,org.hibernate.type.StandardBasicTypes.INTEGER));
*/        	
        	List<Recibo> results =crit.list();
        	log.debug("findListaRecibos correcto, Tamaño devuelto: " + results.size());
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
	
	
	
}
