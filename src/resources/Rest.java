package resources;

import javax.ejb.EJB;
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
import entities.Car;
import Auth.AuthUtil;
import controller.Controller;

@Path("/cars")
public class Rest {
	
	Response response;

	@EJB
	Controller statelessEJB;

	// Get every car in table Car
	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllCars(@HeaderParam("authorization") final String authorization) {

		if (AuthUtil.verifyTokenInHeader(authorization)) {
			//recieves response from stateless EJB
			response = statelessEJB.getAllCars();
		} else {
			response = Response.status(401).build();
		}
		
		return response;

	}

	// Get single car by id
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getCarbyId(@PathParam("id") final int id,
			@HeaderParam("authorization") final String authorization) {

		if (AuthUtil.verifyTokenInHeader(authorization)) {
			response = statelessEJB.getCar(id);
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

		if (AuthUtil.verifyTokenInHeader(authorization)) {
			response = statelessEJB.postCar(car);
		} else {
			response = Response.status(401).build();
		}
		
		return response;
	}

	// Update car by id
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateCar(@PathParam("id") final int id, final Car updatedCar,
			@HeaderParam("authorization") final String authorization) {
		
		if (AuthUtil.verifyTokenInHeader(authorization)) {
			response = statelessEJB.updateCar(updatedCar, id);
		} else {
			response = Response.status(401).build();
		}
		
		return response;
	}

	// Delete single car by id
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	public Response deleteCarbyId(@PathParam("id") int id, @HeaderParam("authorization") final String authorization) {
	
		if (AuthUtil.verifyTokenInHeader(authorization)) {
			response = statelessEJB.deleteCar(id);
		} else {
			response = Response.status(401).build();
		}

		return response;
	}
}
