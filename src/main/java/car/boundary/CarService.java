package car.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import car.entity.Car;
import logger.Log;
import utils.Error;

@Stateless
public class CarService {

	public Response response;

	@PersistenceContext
	private EntityManager em;

	public Response getAllCars() {

		Log.logger.info("Enters Controller.getAllCars");

		try {
			List<Car> cars =  em.createQuery("SELECT c from Car c").getResultList();
			if (cars.size() > 0) {
				response = Response.status(200).entity(cars).build();
			} else {
				response = Response.status(404).entity(new Error(Error.nullResource)).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.logger.warning(e.getMessage());
			response = Response.status(500).build();
		}

		Log.logger.info("Exits Controller.getAllCars");

		return response;
	}

	public Response getCar(final int carId) {
		Log.logger.info("Enters Controller.getCar");
		try {
			Car car = em.find(Car.class, carId);
			if (car != null) {
				response = Response.status(200).entity(car).build();
			} else {
				response = Response.status(404).build();
			}
		} catch (Exception e) {
			Log.logger.warning(e.getMessage());
			e.printStackTrace();
			response = Response.status(500).build();
		}
		Log.logger.info("Exits Controller.getCar");
		return response;
	}

	public Response postCar(final Car car) {

		Log.logger.info("Enters Controller.postCar");

		try {
			this.em.persist(car);
			this.em.flush();
			this.em.refresh(car);
			response = Response.status(201).entity(car).build();
		} catch (Exception e) {
			response = Response.status(400).build();
		}

		Log.logger.info("Exits Controller.postCar");

		return response;
	}

	public Response updateCar(final Car newData, final int carId) {

		Log.logger.info("Enters Controller.updateCar");

		Car carToUpdate = null;
		carToUpdate = em.find(Car.class, carId);

		if (carToUpdate != null) {
			carToUpdate.copyValuesFrom(newData);
		} else {
			response = Response.status(404).build();
		}

		Log.logger.info("Exits Controller.updateCar");
		return response;
	}

	public Response deleteCar(final int carId) {

		Log.logger.info("Enters Controller.deleteCar");
		Car car = em.find(Car.class, carId);
		if (car != null) {
			try {
				em.remove(car);
				response = Response.status(204).build();
			} catch (Exception e) {
				e.printStackTrace();
				Log.logger.warning(e.getMessage());
				response = Response.status(404).entity((new Error(Error.deleteNullResource))).build();
			}
		} else {
			response = Response.status(404).build();
		}
		Log.logger.info("Exits Controller.deleteCar");
		return response;
	}

}
