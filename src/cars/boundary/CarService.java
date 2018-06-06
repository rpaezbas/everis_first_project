package cars.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import com.mongodb.MongoClient;
import Logger.Log;
import brands.entity.Brand;
import cars.entity.Car;

@Stateless
public class CarService {

	String dbName = new String("test");
	MongoClient mongo = new MongoClient();
	Morphia morphia = new Morphia();
	Datastore datastore = morphia.createDatastore(mongo, dbName);

	public Response response;

	public Car[] getAllCars() {

		Log.logger.info("Enters Controller.getAllCars");

		List<Car> cars = datastore.find(Car.class).asList();
		Car[] arrayCar = cars.toArray(new Car[cars.size()]);

		Log.logger.info("Exits Controller.getAllCars");
		return arrayCar;
	}

	public Car[] getCarsByCountry(final String country) {

		Log.logger.info("Enters Controller.getCarsByCountry");

		Query<Car> query = datastore.createQuery(Car.class).field("country").equal(country);
		List<Car> cars = query.asList();
		Car[] arrayCar = cars.toArray(new Car[cars.size()]);

		Log.logger.info("Exits Controller.getCarsByCountry");

		return arrayCar;

	}

	// TODO
	public Car[] getCarsByBrand(final String brandName) {

		Log.logger.info("Enters Controller.getCarsByBrand");

		Car[] arrayCar = null;

		// Get the brand by its name
		Query<Brand> queryBrand = datastore.createQuery(Brand.class).field("name").equal(brandName);
		List<Brand> brands = queryBrand.asList();

		// There should be only a single brand in the list
		if (brands.size() != 0) {
			Brand brand = brands.get(0);
			// Now we have the brand id, get every car with that id as brand
			Query<Car> queryCars = datastore.createQuery(Car.class).field("brand").equal(brand);
			List<Car> cars = queryCars.asList();
			arrayCar = cars.toArray(new Car[cars.size()]);
		}

		Log.logger.info("Exits Controller.getCarsByBrand");

		return arrayCar;
	}

	public Car getCar(final String carId) {

		Log.logger.info("Enters Controller.getCar");

		ObjectId objectId = new ObjectId(carId);
		Car car = datastore.get(Car.class, objectId);

		Log.logger.info("Exits Controller.getCar");

		return car;
	}

	public Car postCar(final Car car) throws Exception {

		Log.logger.info("Enters Controller.postCar");

		// If brand doesn't exist, create it
		Query<Brand> query = datastore.createQuery(Brand.class);
		query.filter("name =", car.getBrand().getName());
		List<Brand> brands = query.asList();

		if (brands.size() == 0) {
			datastore.save(car.getBrand());
		} else {
			// There should be only a single brand in the list
			car.setBrand(brands.get(0)); 
		}

		datastore.save(car);

		Log.logger.info("Exits Controller.postCar");

		return null;

	}

	public Car updateCar(String carId, final Car updatedCar) throws Exception {

		Log.logger.info("Enters Controller.updateCar");

		ObjectId objectId = new ObjectId(carId);
		Car car = datastore.get(Car.class, objectId);

		UpdateOperations<Car> ops = datastore.createUpdateOperations(Car.class).set("country", updatedCar.getCountry())
				.set("createdAt", car.getCreatedAt()).set("registration", car.getRegistration())
				.set("lastUpdated", car.getLastUpdated()).set("brand", car.getBrand());

		datastore.update(car, ops);

		Log.logger.info("Exits Controller.updateCar");

		return null;
	}

	public Car deleteCar(final Car carToDelete) {

		Log.logger.info("Enters Controller.deleteCar");

		datastore.delete(carToDelete);

		Log.logger.info("Exits Controller.deleteCar");

		return null;
	}

}
