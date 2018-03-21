package model;

import javax.ws.rs.Path;
import javax.ws.rs.GET;


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