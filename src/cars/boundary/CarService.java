package cars.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Logger.Log;
import cars.entity.Car;
import utils.Error;
import utils.HibernateUtil;
import utils.ValidatorUtil;

@Stateless
public class CarService {

	public Response response;
	public Session session = HibernateUtil.getSessionFactory().openSession();

	public Car[] getAllCars() {

		Log.logger.info("Enters Controller.getAllCars");

		session.beginTransaction();
		List<Car> listCars = session.createQuery("from Car").list();
		Car[] arrayCar = listCars.toArray(new Car[listCars.size()]);
		session.getTransaction().commit();

		Log.logger.info("Exits Controller.getAllCars");

		return arrayCar;
	}

	public Car[] getCarsByCountry(final String country) {

		Log.logger.info("Enters Controller.getCarsByCountry");

		session.beginTransaction();
		Query query = session.createQuery("from Car where country LIKE :country");
		query.setParameter("country", country + "%");
		List<Car> cars = query.list();
		Car[] arrayCar = cars.toArray(new Car[cars.size()]);
		session.getTransaction().commit();

		Log.logger.info("Exits Controller.getCarsByCountry");

		return arrayCar;

	}

	public Car[] getCarsByBrand(final String brandName) {

		session.beginTransaction();

		Log.logger.info("Enters Controller.getCarsByBrand");
		Query query = session.createQuery("from Car where brand IN (SELECT id from Brand WHERE name LIKE :brandName))");
		query.setParameter("brandName", brandName + "%");
		List<Car> cars = query.list();
		Car[] arrayCar = cars.toArray(new Car[cars.size()]);
		session.getTransaction().commit();

		Log.logger.info("Exits Controller.getCarsByBrand");

		return arrayCar;
	}

	public Car getCar(final int carId) {

		Log.logger.info("Enters Controller.getCar");

		session.beginTransaction();
		Car car = (Car) session.get(Car.class, carId);
		session.getTransaction().commit();

		Log.logger.info("Exits Controller.getCar");

		return car;
	}

	public Car postCar(final Car car) throws HibernateException {

		Log.logger.info("Enters Controller.postCar");

		session.beginTransaction();
		session.save(car);
		session.getTransaction().commit();
		response = Response.status(201).entity(car).build();

		Log.logger.info("Exits Controller.postCar");

		return car;

	}

	public Car updateCar(final Car carToUpdate , final Car updatedCar) throws HibernateException {

		Log.logger.info("Enters Controller.updateCar");

		session.beginTransaction();
		carToUpdate.copyValuesFrom(updatedCar);
		session.update(carToUpdate);
		session.getTransaction().commit();

		Log.logger.info("Exits Controller.updateCar");

		return carToUpdate;
	}

	public Car deleteCar(final Car carToDelete) {

		Log.logger.info("Enters Controller.deleteCar");
		session.beginTransaction();

		session.delete(carToDelete);
		
		session.getTransaction().commit();

		Log.logger.info("Exits Controller.deleteCar");

		return carToDelete;
	}

}
