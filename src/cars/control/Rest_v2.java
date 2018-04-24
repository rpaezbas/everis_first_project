package cars.control;

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
import cars.boundary.Controller;
import cars.entity.Car;
import interceptor.Interceptor;
import utils.AuthUtil;

@Interceptors(Interceptor.class)
@Path("/cars_v2")

public class Rest_v2 {

	Response response;
	@EJB
	Controller statelessEJB = new Controller();

	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllCars(@HeaderParam("authorization") final String authorization) {

		return verifyAndCallEJB("admin", authorization, () -> statelessEJB.getAllCars());

	}

	@GET
	@Path("/{carId}")
	@Produces("application/json")
	public Response getCarbyId(@PathParam("carId") final int carId,
			@HeaderParam("authorization") final String authorization) {

		return verifyAndCallEJB("admin", authorization, () -> statelessEJB.getCar(carId));

	}

	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public Response postCar(final Car car, @HeaderParam("authorization") final String authorization) {

		return verifyAndCallEJB("admin", authorization, () -> statelessEJB.postCar(car));

	}

	@PUT
	@Path("/{carId}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateCar(@PathParam("carId") final int carId, final Car updatedCar,
			@HeaderParam("authorization") final String authorization) {

		return verifyAndCallEJB("user", authorization, () -> statelessEJB.updateCar(updatedCar, carId));

	}

	@DELETE
	@Path("/{carId}")
	@Produces("application/json")
	public Response deleteCarbyId(@PathParam("carId") int carId,
			@HeaderParam("authorization") final String authorization) {

		return verifyAndCallEJB("user", authorization, () -> statelessEJB.deleteCar(carId));

	}

	// This function wraps the EJB call with a preavious verification and returns
	// its response
	public Response verifyAndCallEJB(String rol, String authorization, EjbCall ejbCall) {

		if (AuthUtil.verifyRoleInToken(authorization, rol)) {
			response = ejbCall.call();
		} else {
			response = Response.status(401).build();
		}

		return response;
	}

}
