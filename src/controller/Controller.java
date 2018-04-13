package controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Car;
import resources.HibernateUtil;

@Stateless
public class Controller {

	Response response;
	Session session = HibernateUtil.getSessionFactory().openSession();

	public Response getAllCars() {

		try {
			List<Car> cars = session.createQuery("from Car").list();
			session.getTransaction().commit();
			if (cars != null) {
				response = Response.status(200).entity(cars).build();
			} else {
				response = Response.status(404).build(); //Cambiar a repuesta 200 con json vacio
			}
		} catch (HibernateException hibernateEx) {
			response = Response.status(500).build();
			hibernateEx.printStackTrace();
			session.close();
		}
		return response;
	}

	public Response getCar(final int carId) {

		try {
			Car car = (Car) session.get(Car.class, carId);
			session.getTransaction().commit();
			if (car != null) {
				response = Response.status(200).entity(car).build();
			} else {
				response = Response.status(404).build();  //Cambiar error -Bad Request- 
			}
		} catch (HibernateException hibernateEx) {
			response = Response.status(500).build();
			hibernateEx.printStackTrace();
			session.close();
		}

		return response;
	}

	public Response postCar(final Car car) {

		Transaction transaction = session.beginTransaction();

		try {
			session.save(car);
			session.getTransaction().commit();
			response = Response.status(201).entity(car).build();
		} catch (HibernateException hibernateEx) {
			try {
				transaction.rollback();
				response = Response.status(500).build();
				session.close();
				hibernateEx.printStackTrace();
			} catch (HibernateException rollbackEx) {
				response = Response.status(500).build();
				session.close();
				rollbackEx.printStackTrace();
			}
		}

		return response;
	}

	public Response updateCar(final Car updatedCar, final int carId) {

		Transaction transaction = session.beginTransaction();

		try {
			Car deprecatedCar = (Car) session.get(Car.class, carId); // Controlar si el coche existe - Usar como boolean- error 400
			updatedCar.setId(carId);
			session.update(updatedCar);
			session.getTransaction().commit();
			response = Response.status(200).entity(updatedCar).build();
		} catch (HibernateException hibernateEx) {
			try {
				transaction.rollback();
				response = Response.status(500).build();
				hibernateEx.printStackTrace();
			} catch (HibernateException rollbackEx) {
				response = Response.status(500).build();
				rollbackEx.printStackTrace();
			}
		}

		return response;
	}

	public Response deleteCar(final int carId) {

		Transaction transaction = session.beginTransaction();

		try {
			Car car = (Car) session.get(Car.class, carId); //Bad request ¿Existe el coche? -- Utilizar funcion getCar
			session.delete(car);
			transaction.commit();
			response = Response.status(204).entity(car).build();
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
