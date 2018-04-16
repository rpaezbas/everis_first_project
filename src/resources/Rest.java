package resources;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import entities.Car;
import Auth.AuthUtil;
import Logger.Log;
import controller.Controller;

@Path("/cars")
public class Rest {

	Response response;

	@EJB
	Controller statelessEJB;

	@Context
	HttpServletRequest request;

	// Get every car in table Car
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllCars(@HeaderParam("authorization") final String authorization) {
		
		Log.logger.info("GET: All cars");

		if (AuthUtil.verifyTokenInHeader(authorization)) {
			// recieves response from stateless EJB
			response = statelessEJB.getAllCars();
		} else {
			response = Response.status(401).build();
		}

		return response;

	}

	// Get single car by carId
	@GET
	@Path("/{carId}")
	@Produces("application/json")
	public Response getCarbyId(@PathParam("carId") final int carId,
			@HeaderParam("authorization") final String authorization) {
		
		Log.logger.info("GET: carId " + carId);

		if (AuthUtil.verifyTokenInHeader(authorization)) {
			response = statelessEJB.getCar(carId);
		} else {
			response = Response.status(401).build();
		}

		return response;
	}

	// Create car
	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public Response postCar(final Car car, @HeaderParam("authorization") final String authorization) {

		Log.logger.info("POST:" + car.toString());

		if (AuthUtil.verifyTokenInHeader(authorization)) {
			response = statelessEJB.postCar(car);
		} else {
			response = Response.status(401).build();
		}

		return response;
	}

	// Update car by carId
	@PUT
	@Path("/{carId}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateCar(@PathParam("carId") final int carId, final Car updatedCar,
			@HeaderParam("authorization") final String authorization) {

		Log.logger.info("PUT:" + updatedCar.toString());

		if (AuthUtil.verifyTokenInHeader(authorization)) {
			response = statelessEJB.updateCar(updatedCar, carId);
		} else {
			response = Response.status(401).build();
		}

		return response;
	}

	// Delete single car by carId
	@DELETE
	@Path("/{carId}")
	@Produces("application/json")
	public Response deleteCarbyId(@PathParam("carId") int carId, @HeaderParam("authorization") final String authorization) {

		Log.logger.info("DELETE: carId" + carId);

		if (AuthUtil.verifyTokenInHeader(authorization)) {
			response = statelessEJB.deleteCar(carId);
		} else {
			response = Response.status(401).build();
		}

		return response;
	}
}
