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
 * Home object for domain model class FacturaRecibida.
 * @see es.academia.modelo.FacturaRecibida
 * @author Hibernate Tools
 */
public class FacturaRecibidaHome {

	private static final Log log = LogFactory.getLog(FacturaRecibidaHome.class);

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

	public void persist(FacturaRecibida transientInstance) {
		log.debug("persisting FacturaRecibida instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(FacturaRecibida instance) {
		log.debug("attaching dirty FacturaRecibida instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FacturaRecibida instance) {
		log.debug("attaching clean FacturaRecibida instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(FacturaRecibida persistentInstance) {
		log.debug("deleting FacturaRecibida instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FacturaRecibida merge(FacturaRecibida detachedInstance) {
		log.debug("merging FacturaRecibida instance");
		try {
			FacturaRecibida result = (FacturaRecibida) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public FacturaRecibida findById(java.lang.Integer id) {
		log.debug("getting FacturaRecibida instance with id: " + id);
		try {
			FacturaRecibida instance = (FacturaRecibida) sessionFactory
					.getCurrentSession().get(
							"es.academia.modelo.FacturaRecibida", id);
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

	public List findByExample(FacturaRecibida instance) {
		log.debug("finding FacturaRecibida instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("es.academia.modelo.FacturaRecibida")
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
