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
 * Home object for domain model class PmaDesignerSettings.
 * @see com.rahma.AvEchelon.Entity.PmaDesignerSettings
 * @author Hibernate Tools
 */
public class PmaDesignerSettingsHome {

	private static final Logger logger = Logger.getLogger(PmaDesignerSettingsHome.class.getName());

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(PmaDesignerSettings transientInstance) {
		logger.log(Level.INFO, "persisting PmaDesignerSettings instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			logger.log(Level.INFO, "persist successful");
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "persist failed", re);
			throw re;
		}
	}

	public void attachDirty(PmaDesignerSettings instance) {
		logger.log(Level.INFO, "attaching dirty PmaDesignerSettings instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			logger.log(Level.INFO, "attach successful");
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "attach failed", re);
			throw re;
		}
	}

	public void attachClean(PmaDesignerSettings instance) {
		logger.log(Level.INFO, "attaching clean PmaDesignerSettings instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			logger.log(Level.INFO, "attach successful");
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "attach failed", re);
			throw re;
		}
	}

	public void delete(PmaDesignerSettings persistentInstance) {
		logger.log(Level.INFO, "deleting PmaDesignerSettings instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.log(Level.INFO, "delete successful");
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "delete failed", re);
			throw re;
		}
	}

	public PmaDesignerSettings merge(PmaDesignerSettings detachedInstance) {
		logger.log(Level.INFO, "merging PmaDesignerSettings instance");
		try {
			PmaDesignerSettings result = (PmaDesignerSettings) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			logger.log(Level.INFO, "merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "merge failed", re);
			throw re;
		}
	}

	public PmaDesignerSettings findById(java.lang.String id) {
		logger.log(Level.INFO, "getting PmaDesignerSettings instance with id: " + id);
		try {
			PmaDesignerSettings instance = (PmaDesignerSettings) sessionFactory.getCurrentSession()
					.get("com.rahma.AvEchelon.Entity.PmaDesignerSettings", id);
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

	public List findByExample(PmaDesignerSettings instance) {
		logger.log(Level.INFO, "finding PmaDesignerSettings instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.rahma.AvEchelon.Entity.PmaDesignerSettings").add(Example.create(instance))
					.list();
			logger.log(Level.INFO, "find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			logger.log(Level.SEVERE, "find by example failed", re);
			throw re;
		}
	}
}
