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

	@Override
	public void filter(ContainerRequestContext request) throws IOException {

		Log.logger.info("Request: " + request.getHeaders().toString() + "/n");

		// If the path doesnt match de regex, abort with 404
		String requestedPath = request.getUriInfo().getPath();
		if (validate(requestedPath) == false) {
			System.out.println(requestedPath);
			request.abortWith(Response.status(400).build());
		}
	}
	public boolean validate(String path) {
		return path.matches("/cars((\\/\\d+)|\\/)?") || path.matches("/carsjms");
	}
}