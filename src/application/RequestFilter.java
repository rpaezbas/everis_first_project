package application;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import Logger.Log;

@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {

	/**
	 * The medthod filter is called every time an http request is received. It
	 * validates the url and proceed with the call if its valid or abort it with a
	 * 400 Error
	 * 
	 * @param request
	 *            Http request.
	 * @return Nothing.
	 * @exception IOException
	 *                On bad http request.
	 * @see IOException
	 */

	@Override
	public void filter(ContainerRequestContext request) throws IOException {

		Log.logger.info("Enters RequestFilter.filter");
		Log.logger.info("Request: " + request.getHeaders().toString() + "/n");

		// If the path doesnt match de regex, abort with 404
		String requestedPath = request.getUriInfo().getPath();
		if (validate(requestedPath) == false) {
			System.out.println(requestedPath);
			request.abortWith(Response.status(400).build());
		}
		
		Log.logger.info("Exits RequestFilter.filter");
	}

	/**
	 * This function validates by a regex if the requested url is valid o not
	 * 
	 * @param path
	 *            Requested path.
	 * @return True if the path is valid.
	 * 
	 */
	public boolean validate(String path) {

		return path.matches("/cars((\\/\\d+)|\\/)?") || path.matches("/carsjms");
	}
}