package resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Car;

@Path("/")
public class Rest {
	// Get every car in table Car
	@GET
	@Produces("application/json")
	public Response getAllCars() {
		Response response;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			List<Car> cars = session.createQuery("from Car").list();
			session.getTransaction().commit();
			response = Response.status(201).entity(cars).build();
		} catch (HibernateException hibernateEx) {
			try {
				response = Response.status(500).build();
				hibernateEx.printStackTrace();
			} catch (HibernateException rollbackEx) {
				response = Response.status(500).build();
				rollbackEx.printStackTrace();
			}
		}
		return response;
	}

	// Get single car by id
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response getCarbyId(@PathParam("id") int id) {
		Response response;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Car car = (Car) session.get(Car.class, id);
			session.getTransaction().commit();
			response = Response.status(201).entity(car).build();
		} catch (HibernateException hibernateEx) {
			try {
				transaction.rollback();
				response = Response.status(500).build();
				hibernateEx.printStackTrace();
				session.close();
			} catch (HibernateException rollbackEx) {
				response = Response.status(500).build();
				rollbackEx.printStackTrace();
				session.close();
			}
		}
		return response;
		//return controller.getCar(id);
	}

	// Create car
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response postCar(final Car car) {
		Response response;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			System.out.println(car.getCreatedAt());
			session.save(car);
			session.getTransaction().commit();
			response = Response.status(201).entity(car).build();
		} catch (HibernateException hibernateEx) {
			try {
				transaction.rollback();
				response = Response.status(500).build();
				hibernateEx.printStackTrace();
			} catch (HibernateException rollbackEx) {
				response = Response.status(500).build();
				rollbackEx.printStackTrace();
			}
		}
		return response;
	}

	// Update car by id
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateCar(@PathParam("id") int id, final Car updatedCar) {
		Response response;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Car deprecatedCar = (Car) session.get(Car.class, id); // This is the car we want to update
			System.out.println(updatedCar);
			System.out.println(deprecatedCar);
			deprecatedCar.copyValuesFrom(updatedCar);
			session.update(deprecatedCar);
			session.getTransaction().commit();
			response = Response.status(200).entity(deprecatedCar).build();
		} catch (HibernateException hibernateEx) {
			try {
				transaction.rollback();
				response = Response.status(400).build();
				hibernateEx.printStackTrace();
				//session.close();
			} catch (HibernateException rollbackEx) {
				response = Response.status(500).build();
				rollbackEx.printStackTrace();
				//session.close();
			}
		}
		return response;
	}

	// Delete single car by id
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	public Response deleteCarbyId(@PathParam("id") int id) {
		Response response;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Car car = (Car) session.get(Car.class, id);
			session.delete(car);
			transaction.commit();
			response = Response.status(201).entity(car).build();
		} catch (HibernateException hibernateEx) {
			try {
				transaction.rollback();
				response = Response.status(500).build();
				hibernateEx.printStackTrace();
				session.close();
			} catch (HibernateException rollbackEx) {
				response = Response.status(500).build();
				rollbackEx.printStackTrace();
				session.close();
			}
		}
		return response;
	}
}
