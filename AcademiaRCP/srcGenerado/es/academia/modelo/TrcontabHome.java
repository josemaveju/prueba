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
 * Home object for domain model class Trcontab.
 * @see es.academia.modelo.Trcontab
 * @author Hibernate Tools
 */
public class TrcontabHome {

	private static final Log log = LogFactory.getLog(TrcontabHome.class);

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

	public void persist(Trcontab transientInstance) {
		log.debug("persisting Trcontab instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Trcontab instance) {
		log.debug("attaching dirty Trcontab instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Trcontab instance) {
		log.debug("attaching clean Trcontab instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Trcontab persistentInstance) {
		log.debug("deleting Trcontab instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Trcontab merge(Trcontab detachedInstance) {
		log.debug("merging Trcontab instance");
		try {
			Trcontab result = (Trcontab) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Trcontab findById(java.lang.Integer id) {
		log.debug("getting Trcontab instance with id: " + id);
		try {
			Trcontab instance = (Trcontab) sessionFactory.getCurrentSession()
					.get("es.academia.modelo.Trcontab", id);
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

	public List findByExample(Trcontab instance) {
		log.debug("finding Trcontab instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("es.academia.modelo.Trcontab")
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
