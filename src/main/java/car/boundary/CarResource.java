package car.boundary;

import javax.ejb.EJB;
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
import javax.ws.rs.core.Response;
import car.boundary.CarService;
import car.entity.Car;
import interceptor.Interceptor;
import logger.Log;
import utils.AuthUtil;

@Interceptors(Interceptor.class)
@Path("/cars")
public class CarResource {

	Response response;

	@EJB
	public CarService carService;

	// Get every car in table Car
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllCars(@HeaderParam("authorization") final String authorization) {

		Log.logger.info("Enters Rest.getAllCars");

		if (AuthUtil.verifyRoleInToken(authorization, "user")) {
			response = carService.getAllCars();
		} else {
			response = Response.status(401).build();
		}
		
		Log.logger.info("Exits Rest.getAllCars");

		return response;

	}


	// Get single car by carId
	@GET
	@Path("/{carId}")
	@Produces("application/json")
	public Response getCarbyId(@PathParam("carId") final int carId,
			@HeaderParam("authorization") final String authorization) {

		Log.logger.info("Enters Rest.getCarbyId");

		if (AuthUtil.verifyRoleInToken(authorization, "user")) {
			response = carService.getCar(carId);
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
	public Response postCar(final Car car, @HeaderParam("authorization") final String authorization) {		
		
		Log.logger.info("Enters Rest.postCar");
		
		if (AuthUtil.verifyRoleInToken(authorization, "admin")) {
			response = carService.postCar(car);
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
	public Response updateCar(@PathParam("carId") final int carId, final Car updatedCar,
			@HeaderParam("authorization") final String authorization) {
		
		Log.logger.info("Enters Rest.updateCar");

		if (AuthUtil.verifyRoleInToken(authorization, "user")) {
			response = carService.updateCar(updatedCar, carId);
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
	public Response deleteCarbyId(@PathParam("carId") int carId,
			@HeaderParam("authorization") final String authorization) {

		Log.logger.info("Enters Rest.deleteCar");

		if (AuthUtil.verifyRoleInToken(authorization, "user")) {
			response = carService.deleteCar(carId);
		} else {
			response = Response.status(401).build();
		}
		
		Log.logger.info("Exits Rest.deleteCar");

		return response;
	}
}
