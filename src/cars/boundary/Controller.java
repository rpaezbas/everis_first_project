package cars.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Logger.Log;
import cars.entity.Car;
import utils.Error;
import utils.HibernateUtil;

@Stateless
public class Controller {

	public Response response;
	public Session session = HibernateUtil.getSessionFactory().openSession();

	public Response getAllCars() {

		try {
			// When the get query is finished, it closes the session automatically
			List<Car> cars = session.createQuery("from Car").list();
			if (cars != null) {
				response = Response.status(200).entity(cars).build();
			} else {
				response = Response.status(404).entity(new Error(Error.nullResource)).build();
			}
		} catch (HibernateException hibernateEx) {
			Log.logger.warning(hibernateEx.getMessage());
			response = Response.status(500).build();
			session.close();
		}

		return response;
	}

	public Response getCar(final int carId) {

		try {
			Car car = (Car) session.get(Car.class, carId);
			if (car != null) {
				response = Response.status(200).entity(car).build();
			} else {
				response = Response.status(404).entity(new Error(Error.nullResource)).build();
			}
		} catch (HibernateException hibernateEx) {
			Log.logger.warning(hibernateEx.getMessage());
			response = Response.status(500).build();
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
				Log.logger.warning(hibernateEx.getMessage());
				response = Response.status(500).entity(new Error(Error.invalidInput)).build();
				transaction.rollback();
				session.close();
			} catch (HibernateException rollbackEx) {
				Log.logger.warning(rollbackEx.getMessage());
				response = Response.status(500).build();
				session.close();
			}
		}

		return response;
	}

	public Response updateCar(final Car updatedCar, final int carId) {

		Transaction transaction = session.beginTransaction();

		try {
			Car deprecatedCar = (Car) session.get(Car.class, carId);
			if (deprecatedCar != null) {
				deprecatedCar.copyValuesFrom(updatedCar);
				session.update(deprecatedCar);
				session.getTransaction().commit();
				response = Response.status(200).entity(updatedCar).build();
			} else {
				response = Response.status(404).entity(new Error(Error.nullResource)).build();
			}
		} catch (HibernateException hibernateEx) {
			try {
				Log.logger.warning(hibernateEx.getMessage());
				response = Response.status(500).build();
				session.getTransaction().rollback();
				session.close();
			} catch (HibernateException rollbackEx) {
				Log.logger.warning(rollbackEx.getMessage());
				response = Response.status(500).build();
				session.close();
			}
		}

		return response;
	}

	public Response deleteCar(final int carId) {

		Transaction transaction = session.beginTransaction();

		try {
			Car car = (Car) session.get(Car.class, carId);
			if (car != null) {
				session.delete(car);
				session.getTransaction().commit();
				response = Response.status(204).build();
			}else {
				session.getTransaction().commit();
				response = Response.status(404).entity(new Error(Error.deleteNullResource)).build(); 
			}
		} catch (HibernateException hibernateEx) {
			try {
				Log.logger.warning(hibernateEx.getMessage());
				response = Response.status(500).build();
				transaction.rollback();
				session.close();
			} catch (HibernateException rollbackEx) {
				Log.logger.warning(rollbackEx.getMessage());
				response = Response.status(500).build();
				session.close();
			}
		}

		return response;
	}

}
