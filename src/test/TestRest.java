package test;

import javax.ws.rs.core.Response;
import org.mockito.Mockito;

import brands.entity.Brand;
import cars.boundary.Controller;
import cars.control.Rest;
import cars.entity.Car;
import junit.framework.TestCase;

public class TestRest extends TestCase {

	Rest rest = new Rest();
	String validToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL3JwYWV6YmFzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw1YWNiOTM0NTQzYzg3ZjExNDllZmRiMWMiLCJhdWQiOiJ4Nm1FcTF4cVdrcjczMEVLTUQ0M043Z1kyMjdDWm1wZSIsImlhdCI6MTUyNDA0MDY1NywiZXhwIjoxNTI0MDc2NjU3fQ.cZZVpqk3sNTdhBHsJ1pAAshtFyIbkXP-HKrz4nI_aBg";
	String invalidToken = "Bearer invalid.token.token";
	Car car = new Car();

	public void setUp() {
		
		car.setBrand(new Brand());
	
		rest.statelessEJB = Mockito.mock(Controller.class);
		Mockito.when(rest.statelessEJB.getAllCars()).thenReturn(Response.status(200).build());
		Mockito.when(rest.statelessEJB.getCar(1)).thenReturn(Response.status(200).build());
		Mockito.when(rest.statelessEJB.postCar(car)).thenReturn(Response.status(201).build());
		Mockito.when(rest.statelessEJB.updateCar(car, 1)).thenReturn(Response.status(200).build());
		Mockito.when(rest.statelessEJB.deleteCar(1)).thenReturn(Response.status(204).build());
	
	}

	public void testGetAllCars() {
		assertEquals(rest.getAllCars(invalidToken).getStatus(), 401);
		assertEquals(rest.getAllCars(null).getStatus(), 401);
		assertEquals(rest.getAllCars("").getStatus(), 401);
		assertEquals(rest.getAllCars(validToken).getStatus(), 200);
	}

	public void testGetCarById() {
		assertEquals(rest.getCarbyId(1, invalidToken).getStatus(), 401);
		assertEquals(rest.getCarbyId(44, null).getStatus(), 401);
		assertEquals(rest.getCarbyId(44, "").getStatus(), 401);
		assertEquals(rest.getCarbyId(1, validToken).getStatus(), 200);
	}

	public void testPostCar() {
		assertEquals(rest.postCar(car, invalidToken).getStatus(), 401);
		assertEquals(rest.postCar(car, null).getStatus(), 401);
		assertEquals(rest.postCar(car, "").getStatus(), 401);
		assertEquals(rest.postCar(car, validToken).getStatus(), 201);
	}

	public void testDeleteCarById() {
		assertEquals(rest.deleteCarbyId(1, validToken).getStatus(), 204);
		assertEquals(rest.deleteCarbyId(1, invalidToken).getStatus(), 401);
		assertEquals(rest.deleteCarbyId(1, null).getStatus(), 401);
		assertEquals(rest.deleteCarbyId(1, "").getStatus(), 401);
	}

	public void updateCar() {
		assertEquals(rest.updateCar(1, car, validToken).getStatus(), 204);
		assertEquals(rest.updateCar(1, car, invalidToken).getStatus(), 404);
		assertEquals(rest.updateCar(1, car, null).getStatus(), 404);
		assertEquals(rest.updateCar(1, car, "").getStatus(), 404);
	}

}
