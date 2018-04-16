package test;

import javax.ws.rs.core.Response;


import junit.framework.*;
import resources.Rest;

public class Tests extends TestCase {
	
	
	public void testgetAllCars() {
		
		Rest rest = new Rest();
		assertEquals(rest.getAllCars("Bearer invalid.token.token").getStatus(),Response.status(401).build().getStatus());
		assertEquals(rest.getAllCars(null).getStatus(),Response.status(401).build().getStatus());
		assertEquals(rest.getAllCars("").getStatus(),Response.status(401).build().getStatus());
	}
	
	
	public void testgeCarsById() {
		
		Rest rest = new Rest();
		assertEquals(rest.getCarbyId(0,"Bearer invalid.token.token").getStatus(),Response.status(401).build().getStatus());
		assertEquals(rest.getCarbyId(44,null).getStatus(),Response.status(401).build().getStatus());
		assertEquals(rest.getCarbyId(44,"").getStatus(),Response.status(401).build().getStatus());
	}
	
}
