package test;

import javax.ws.rs.core.Response;
import org.mockito.Mockito;

import brands.entity.Brand;
import cars.boundary.CarResource;
import cars.boundary.CarService;
import cars.entity.Car;
import junit.framework.TestCase;

public class TestRest extends TestCase {

	CarResource rest = new CarResource();
	String userValidToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJodHRwczovL3JwLmNvbS91c2VyX21ldGFkYXRhIjp7InJvbCI6InVzZXIifSwiZ2l2ZW5fbmFtZSI6InJhZmFlbCIsImZhbWlseV9uYW1lIjoiUMOhZXoiLCJuaWNrbmFtZSI6InJhZmFlbHBhZXpiYXN0aWRhZXZlcmlzIiwibmFtZSI6InJhZmFlbCBQw6FleiIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vLVhkVUlxZE1rQ1dBL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBLzQyNTJyc2NidjVNL3Bob3RvLmpwZyIsImxvY2FsZSI6ImVzIiwidXBkYXRlZF9hdCI6IjIwMTgtMDQtMjVUMDc6MTA6MzQuMzI5WiIsImlzcyI6Imh0dHBzOi8vcnBhZXpiYXMuYXV0aDAuY29tLyIsInN1YiI6Imdvb2dsZS1vYXV0aDJ8MTE3MDE4NDU4MTEzNDM4MDI5NTY1IiwiYXVkIjoieDZtRXExeHFXa3I3MzBFS01ENDNON2dZMjI3Q1ptcGUiLCJpYXQiOjE1MjQ2NDAyMzQsImV4cCI6MTUyNTY0MDIzNH0.b2kd4xPZJQD6-vi7g5ehXekqIcDLsKh9fgA5MgWSRuQ";
	String invalidToken = "Bearer invalid.token.token";
	String adminValidToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJodHRwczovL3JwLmNvbS91c2VyX21ldGFkYXRhIjp7InJvbCI6ImFkbWluIn0sIm5pY2tuYW1lIjoicnBhZXpiYXMiLCJuYW1lIjoicnBhZXpiYXNAZXZlcmlzLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci8wMTZhNzA2NWIxZjZmMjcxNGVkNTVmNzBjZDg0NDFiYz9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRnJwLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDE4LTA0LTI1VDA3OjA4OjI0LjU0N1oiLCJpc3MiOiJodHRwczovL3JwYWV6YmFzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw1YWNiOTM0NTQzYzg3ZjExNDllZmRiMWMiLCJhdWQiOiJ4Nm1FcTF4cVdrcjczMEVLTUQ0M043Z1kyMjdDWm1wZSIsImlhdCI6MTUyNDY0MDEwNiwiZXhwIjoxNTI1NjQwMTA2fQ.VuB7G7FSLSxRntd0nKAlkBrHvJJqMKl2ABRCQWAvW9s";
	Car car = new Car();

	public void setUp() {

		car.setBrand(new Brand());
		rest.carsDAO = Mockito.mock(CarService.class);
		Mockito.when(rest.carsDAO.getAllCars()).thenReturn(Response.status(200).build());
		Mockito.when(rest.carsDAO.getCar(1)).thenReturn(Response.status(200).build());
		Mockito.when(rest.carsDAO.postCar(car)).thenReturn(Response.status(201).build());
		Mockito.when(rest.carsDAO.updateCar(car, 1)).thenReturn(Response.status(200).build());
		Mockito.when(rest.carsDAO.deleteCar(1)).thenReturn(Response.status(204).build());
		Mockito.when(rest.carsDAO.deleteCar(0)).thenReturn(Response.status(404).build());

	}

	public void testGetAllCars() {
		assertEquals(rest.getAllCars(null,null,invalidToken).getStatus(), 401);
		assertEquals(rest.getAllCars(null,null,null).getStatus(), 401);
		assertEquals(rest.getAllCars(null,null,"").getStatus(), 401);
		assertEquals(rest.getAllCars(null,null,userValidToken).getStatus(), 200);
		assertEquals(rest.getAllCars(null,null,adminValidToken).getStatus(), 200);
	}

	public void testGetCarById() {
		assertEquals(rest.getCarbyId(1, invalidToken).getStatus(), 401);
		assertEquals(rest.getCarbyId(44, null).getStatus(), 401);
		assertEquals(rest.getCarbyId(44, "").getStatus(), 401);
		assertEquals(rest.getCarbyId(1, userValidToken).getStatus(), 200);
		assertEquals(rest.getCarbyId(1, adminValidToken).getStatus(), 200);
	}

	public void testPostCar() {
		assertEquals(rest.postCar(car, adminValidToken).getStatus(), 201);
		assertEquals(rest.postCar(car, invalidToken).getStatus(), 401);
		assertEquals(rest.postCar(car, null).getStatus(), 401);
		assertEquals(rest.postCar(car, "").getStatus(), 401);
		assertEquals(rest.postCar(car, userValidToken).getStatus(), 401);
	}

	public void testDeleteCarById() {
		assertEquals(rest.deleteCarbyId(1, userValidToken).getStatus(), 204);
		assertEquals(rest.deleteCarbyId(1, adminValidToken).getStatus(), 204);
		assertEquals(rest.deleteCarbyId(1, invalidToken).getStatus(), 401);
		assertEquals(rest.deleteCarbyId(1, null).getStatus(), 401);
		assertEquals(rest.deleteCarbyId(1, "").getStatus(), 401);
		assertEquals(rest.deleteCarbyId(0, userValidToken).getStatus(), 404);
		assertEquals(rest.deleteCarbyId(0, adminValidToken).getStatus(), 404);
	}

	public void testUpdateCar() {
		assertEquals(rest.updateCar(1, car, userValidToken).getStatus(), 200);
		assertEquals(rest.updateCar(1, car, adminValidToken).getStatus(), 200);
		assertEquals(rest.updateCar(1, car, invalidToken).getStatus(), 401);
		assertEquals(rest.updateCar(1, car, null).getStatus(), 401);
		assertEquals(rest.updateCar(1, car, "").getStatus(), 401);
	}

}
