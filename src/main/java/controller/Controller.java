package controller;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Car;
import resources.HibernateUtil;

public class Controller {
	
	public Response getCar(int id) {
		Response response;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Car car = (Car) session.get(Car.class, id);
			session.getTransaction().commit();
			response = Response.status(201).entity(car).build();
		} catch (HibernateException hibernateEx) {
			try {
				transaction.rollback();
				response = Response.status(500).build();
				hibernateEx.printStackTrace();
				session.close();
			} catch (HibernateException rollbackEx) {
				response = Response.status(500).build();
				rollbackEx.printStackTrace();
				session.close();
			}
		}
		return response;		
	}
}
