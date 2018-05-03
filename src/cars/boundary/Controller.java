package cars.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Logger.Log;
import cars.entity.Car;
import utils.Error;
import utils.HibernateUtil;
import utils.ValidatorUtil;

@Stateless
public class Controller {

	public Response response;
	public Session session = HibernateUtil.getSessionFactory().openSession();

	public Response getAllCars() {

		Transaction transaction = session.beginTransaction();

		Log.logger.info("Enters Controller.getAllCars");

		try {
			// Since the casting of every single car is not correct, a possible solution is
			// creating an array where every car that gets added has the right casting
			List<Car> listCars = session.createQuery("from Car").list();
			Car[] arrayCar = listCars.toArray(new Car[listCars.size()]);
			session.getTransaction().commit();
			if (arrayCar.length > 0) {
				response = Response.status(200).entity(arrayCar).build();
			} else {
				response = Response.status(404).entity(new Error(Error.nullResource)).build();
			}
		} catch (Exception e) {
			Log.logger.warning(e.getMessage());
			response = Response.status(500).build();
			session.getTransaction().commit();
		}

		Log.logger.info("Exits Controller.getAllCars");

		return response;
	}

	public Response getCar(final int carId) {

		Transaction transaction = session.beginTransaction();

		Log.logger.info("Enters Controller.getCar");

		try {
			Car car = (Car) session.get(Car.class, carId);
			if (car != null) {
				response = Response.status(200).entity(car).build();
			} else {
				response = Response.status(404).build();
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			Log.logger.warning(e.getMessage());
			e.printStackTrace();
			response = Response.status(500).build();
			session.getTransaction().commit();
		}

		Log.logger.info("Exits Controller.getCar");

		return response;
	}

	public Response postCar(final Car car) {

		Transaction transaction = session.beginTransaction();

		Log.logger.info("Enters Controller.postCar");

		ArrayList<String> violantionMessages = ValidatorUtil.validate(car);

		if (violantionMessages.size() > 0) {
			response = Response.status(400).entity(violantionMessages).build();
			session.clear();
			session.getTransaction().commit();
		} else {
			try {
				session.save(car);
				session.getTransaction().commit();
				response = Response.status(201).entity(car).build();
			} catch (Exception e) {
				// Removes the bad query
				session.clear();
				response = Response.status(400).build();
				// Do an empty commit in order to avoid nested transactions
				session.getTransaction().commit();
			}
		}

		Log.logger.info("Exits Controller.postCar");

		return response;
	}

	public Response updateCar(final Car newData, final int carId) {

		Transaction transaction = session.beginTransaction();

		Log.logger.info("Enters Controller.updateCar");

		Car carToUpdate = null;

		try {
			carToUpdate = (Car) session.get(Car.class, carId);
		} catch (Exception e) {
			Log.logger.warning(e.getMessage());
			e.printStackTrace();
			response = Response.status(500).build();
			session.clear();
		}

		if (carToUpdate != null) {
			carToUpdate.copyValuesFrom(newData);

			try {
				session.update(carToUpdate);
				session.getTransaction().commit();
				response = Response.status(200).build();
			} catch (Exception e) {
				e.printStackTrace();
				Log.logger.warning(e.getMessage());
				response = Response.status(400).build();
				session.getTransaction().rollback();
				session.clear();
			}
		} else {
			response = Response.status(404).build();
			session.getTransaction().commit();
		}

		Log.logger.info("Exits Controller.updateCar");

		return response;
	}

	public Response deleteCar(final int carId) {

		Log.logger.info("Enters Controller.deleteCar");

		Transaction transaction = session.beginTransaction();

		Car car = (Car) session.get(Car.class, carId);
		if (car != null) {
			try {
				session.delete(car);
				session.getTransaction().commit();
				response = Response.status(204).build();
			} catch (Exception e) {
				e.printStackTrace();
				Log.logger.warning(e.getMessage());
				response = Response.status(404).entity((new Error(Error.deleteNullResource))).build();
				session.getTransaction().commit();
			}
		} else {
			response = Response.status(404).build();
			session.getTransaction().commit();
		}

		Log.logger.info("Exits Controller.deleteCar");

		return response;
	}

}
