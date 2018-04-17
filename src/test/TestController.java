package test;

import junit.framework.*;
import controller.Controller;
import org.mockito.Mockito;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Car;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestController extends TestCase {

	List<Car> mockCarList = new ArrayList<Car>();
	Controller controller = new Controller();
	Session mockSession = Mockito.mock(Session.class);
	Car validCar = new Car();
	Car invalidCar = new Car();

	public void setUp() {
		
		controller.session = mockSession;
		
		//GetAllCars mocks
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockSession.createQuery("from Car")).thenReturn(mockQuery);
		Mockito.when(mockQuery.list()).thenReturn(mockCarList);

		//Get and delete mocks
		Mockito.when(mockSession.get(Car.class, 1)).thenReturn(new Car());
		Mockito.doNothing().when(mockSession).update(new Car());
		Mockito.doNothing().when(mockSession).delete(new Car());
		
		//Save mocks
		// The session.update(car) returns a Serializable that has to be mocked,
		// otherwise Mockito throws a null pointer exception ( even when the method
		// doesn´t use that object! )
		Serializable mockSerializable = Mockito.mock(Serializable.class);
		Mockito.when(mockSession.save(validCar)).thenReturn(mockSerializable);
		Mockito.when(mockSession.save(invalidCar)).thenReturn(new HibernateException(""));

		// Mock commit, rollback and session.close
		Transaction mockTransaction = Mockito.mock(Transaction.class);
		Mockito.when(mockSession.getTransaction()).thenReturn(mockTransaction);
		Mockito.doNothing().when(mockTransaction).commit();
		Mockito.doNothing().when(mockTransaction).rollback();
	}

	public void testControllerGetAllCars() {
		assertEquals(controller.getAllCars().getStatus(), 200);
	}

	public void testControllerGetCarById() {
		assertEquals(controller.getCar(1).getStatus(), 200);
		assertEquals(controller.getCar(0).getStatus(), 404);
		assertEquals(controller.getCar(-1).getStatus(), 404);
	}

	public void testControllerPostCar() {
		assertEquals(controller.postCar(validCar).getStatus(), 201);
		//assertEquals(controller.postCar(invalidCar).getStatus(), 500);
	}

	public void testControllerUpdateCar() {
		assertEquals(controller.updateCar(new Car(), 0).getStatus(), 404);
		assertEquals(controller.updateCar(new Car(), 1).getStatus(), 200);
	}
	
	public void testControllerDeleteCar() {
		assertEquals(controller.deleteCar(0).getStatus(), 404);
		assertEquals(controller.deleteCar(1).getStatus(), 204);
	}
}
