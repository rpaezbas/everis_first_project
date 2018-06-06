package cars.boundary;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Logger.Log;
import cars.entity.Car;
import utils.AuthUtil;

@Stateless
@Path("/cars")
public class CarResource {

	Response response;
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

	@EJB
	public CarService carService;

	// Get every car in table Car
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllCars(@QueryParam("country") final String country, @QueryParam("brand") final String brandName,
			@HeaderParam("authorization") final String authorization) {

		Log.logger.info("Enters Rest.getAllCars");

		Car[] cars;

		if (AuthUtil.verifyRoleInToken(authorization, "user")) {

			// First checks wich of the parameters has been filled, filter by country if its
			// not null, filter by brand if its not null

			if (country == null && brandName == null) {
				cars = carService.getAllCars();
				response = Response.status(200).entity(cars).build();

			} else if (country != null && brandName == null) {
				cars = carService.getCarsByCountry(country);
				response = Response.status(200).entity(cars).build();

			} else if (country == null && brandName != null) {
				cars = carService.getCarsByBrand(brandName);
				response = Response.status(200).entity(cars).build();
			}

		} else {
			Response.status(401).build();
		}

		Log.logger.info("Exits Rest.getAllCars");

		return response;

	}

	// Get single car by carId
	@GET
	@Path("/{carId}")
	@Produces("application/json")
	public Response getCarbyId(@PathParam("carId") final String carId,
			@HeaderParam("authorization") final String authorization) {

		Log.logger.info("Enters Rest.getCarbyId");

		if (AuthUtil.verifyRoleInToken(authorization, "user")) {

			Car car = carService.getCar(carId);
			if (car != null) {
				response = Response.status(200).entity(car).build();
			} else {
				response = Response.status(204).entity(car).build();
			}

		} else {
			response = Response.status(401).build();
		}

		Log.logger.info("Exits Rest.getCarbyId");

		return response;
	}

	// Create car
	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public Response postCar(final String car, @HeaderParam("authorization") final String authorization) {

		Log.logger.info("Enters Rest.postCar");
		
		Car carToSave = gson.fromJson(car, Car.class);

		if (AuthUtil.verifyRoleInToken(authorization, "admin")) {

			try {
				Car newCar = carService.postCar(carToSave);
				response = Response.status(201).entity(newCar).build();
			} catch (Exception e) {
				response = Response.status(400).build();
			}

		} else {
			response = Response.status(401).build();
		}

		Log.logger.info("Exits Rest.postCar");

		return response;
	}

	// Update car by carId
	@PUT
	@Path("/{carId}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateCar(@PathParam("carId") final String carId, final String updatedCarJson,
			@HeaderParam("authorization") final String authorization) {

		Log.logger.info("Enters Rest.updateCar");
		
		Car updatedCar = gson.fromJson(updatedCarJson, Car.class);

		if (AuthUtil.verifyRoleInToken(authorization, "user")) {

			Car deprecatedCar = carService.getCar(carId);
			if (deprecatedCar != null) {
				try {
					Car newCar = carService.updateCar(carId, updatedCar);
					response = Response.status(204).entity(newCar).build();
				} catch (Exception e) {
					response = Response.status(400).build();
				}
			} else {
				response = Response.status(204).build();
			}

		} else {
			response = Response.status(401).build();
		}

		Log.logger.info("Exits Rest.updateCar");

		return response;
	}

	// Delete single car by carId
	@DELETE
	@Path("/{carId}")
	@Produces("application/json")
	public Response deleteCarbyId(@PathParam("carId") String carId,
			@HeaderParam("authorization") final String authorization) {

		Log.logger.info("Enters Rest.deleteCar");

		if (AuthUtil.verifyRoleInToken(authorization, "user")) {
			Car carToDelete = carService.getCar(carId);
			if(carToDelete != null) {
				carService.deleteCar(carToDelete);
				response = Response.status(200).build();
			}else {
				response = Response.status(204).build();
			}
		} else {
			response = Response.status(401).build();
		}

		Log.logger.info("Exits Rest.deleteCar");

		return response;
	}
}
