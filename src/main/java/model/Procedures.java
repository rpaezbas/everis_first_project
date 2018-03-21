package model;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

import org.hibernate.Session;

import resources.HibernateUtil;

@Path("/procedures")
public class Procedures {
	
	@GET
	@Path("/")
	public String generateTablesFromEntities() {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		session.beginTransaction();
		session.getTransaction().commit();
		return "finished";
	}
}