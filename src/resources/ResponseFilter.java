package resources;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import Logger.Log;

//This class includes headers needed in http responses when requests come from any origin. It solves the typical CORS issues. When the server 
//sends a response, it adds the different headers needed. Also it handles the preflight http response before the UPDATE,PUT and DELETE requests.
//The Provider annotation makes it been used by every Application
@Provider
public class ResponseFilter implements ContainerResponseFilter {

	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
		MultivaluedMap<String, Object> headers = responseContext.getHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		headers.add("Access-Control-Max-Age", "2000");

		if (requestContext.getMethod().equals("OPTIONS")) {
			responseContext.setStatus(200);
		}
		
		Log.logger.info("Response: Status-" + responseContext.getStatus() + " Headers: " + responseContext.getHeaders().toString());
	}
}