package com.rahma.AvEchelon.Entity;
// Generated Jun 13, 2024, 2:26:23 AM by Hibernate Tools 5.6.7.Final

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class PmaUsergroups.
 * @see com.rahma.AvEchelon.Entity.PmaUsergroups
 * @author Hibernate Tools
 */
public class PmaUsergroupsHome {

	private static final Logger logger = Logger.getLogger(PmaUsergroupsHome.class.getName());

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(PmaUsergroups transientInstance) {
		logger.log(Level.INFO, "persisting PmaUsergroups instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			logger.log(Level.INFO, "persist successful");
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "persist failed", re);
			throw re;
		}
	}

	public void attachDirty(PmaUsergroups instance) {
		logger.log(Level.INFO, "attaching dirty PmaUsergroups instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			logger.log(Level.INFO, "attach successful");
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "attach failed", re);
			throw re;
		}
	}

	public void attachClean(PmaUsergroups instance) {
		logger.log(Level.INFO, "attaching clean PmaUsergroups instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			logger.log(Level.INFO, "attach successful");
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "attach failed", re);
			throw re;
		}
	}

	public void delete(PmaUsergroups persistentInstance) {
		logger.log(Level.INFO, "deleting PmaUsergroups instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.log(Level.INFO, "delete successful");
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "delete failed", re);
			throw re;
		}
	}

	public PmaUsergroups merge(PmaUsergroups detachedInstance) {
		logger.log(Level.INFO, "merging PmaUsergroups instance");
		try {
			PmaUsergroups result = (PmaUsergroups) sessionFactory.getCurrentSession().merge(detachedInstance);
			logger.log(Level.INFO, "merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "merge failed", re);
			throw re;
		}
	}

	public PmaUsergroups findById(com.rahma.AvEchelon.Entity.PmaUsergroupsId id) {
		logger.log(Level.INFO, "getting PmaUsergroups instance with id: " + id);
		try {
			PmaUsergroups instance = (PmaUsergroups) sessionFactory.getCurrentSession()
					.get("com.rahma.AvEchelon.Entity.PmaUsergroups", id);
			if (instance == null) {
				logger.log(Level.INFO, "get successful, no instance found");
			} else {
				logger.log(Level.INFO, "get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "get failed", re);
			throw re;
		}
	}

	public List findByExample(PmaUsergroups instance) {
		logger.log(Level.INFO, "finding PmaUsergroups instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.rahma.AvEchelon.Entity.PmaUsergroups")
					.add(Example.create(instance)).list();
			logger.log(Level.INFO, "find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "find by example failed", re);
			throw re;
		}
	}
}
