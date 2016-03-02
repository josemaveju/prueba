package es.academia.modelo;

// Generated 21-jul-2014 23:03:03 by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class DatosAcademia.
 * @see es.academia.modelo.DatosAcademia
 * @author Hibernate Tools
 */
public class DatosAcademiaHome {

	private static final Log log = LogFactory.getLog(DatosAcademiaHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(DatosAcademia transientInstance) {
		log.debug("persisting DatosAcademia instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DatosAcademia instance) {
		log.debug("attaching dirty DatosAcademia instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DatosAcademia instance) {
		log.debug("attaching clean DatosAcademia instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DatosAcademia persistentInstance) {
		log.debug("deleting DatosAcademia instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DatosAcademia merge(DatosAcademia detachedInstance) {
		log.debug("merging DatosAcademia instance");
		try {
			DatosAcademia result = (DatosAcademia) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DatosAcademia findById(java.lang.Integer id) {
		log.debug("getting DatosAcademia instance with id: " + id);
		try {
			DatosAcademia instance = (DatosAcademia) sessionFactory
					.getCurrentSession().get(
							"es.academia.modelo.DatosAcademia", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DatosAcademia instance) {
		log.debug("finding DatosAcademia instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("es.academia.modelo.DatosAcademia")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
