package resources;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		String requestedPath = request.getUriInfo().getPath();
		if (validate(requestedPath) == false) {
			request.abortWith(Response.status(404).build());
		}
	}

	public boolean validate(String path) {
		return path.matches("cars((\\/\\d+)|\\/)?");
	}
}