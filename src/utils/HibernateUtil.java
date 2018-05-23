package utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import Logger.Log;
import brands.entity.Brand;
import cars.entity.Car;

public class HibernateUtil {

	// Annotation based configuration
	private static SessionFactory sessionFactory;

	private static SessionFactory buildSessionFactory() {
		
		Log.logger.info("Enters HibernateUtil.buildSessionFactory");
		
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration();
			configuration.configure("persistence.xml");
			configuration.addAnnotatedClass(Car.class);
			configuration.addAnnotatedClass(Brand.class);
			System.out.println("Hibernate Annotation Configuration loaded");

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			System.out.println("Hibernate Annotation serviceRegistry created");

			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
			Log.logger.info("Exits HibernateUtil.buildSessionFactory");

			return sessionFactory;
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			Log.logger.warning("hibernate exception" + ex.getMessage());
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		
		Log.logger.info("Enters HibernateUtil.getSessionFactory");
		
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		
		Log.logger.info("Exits HibernateUtil.getSessionFactory");
		
		return sessionFactory;
	}
}

