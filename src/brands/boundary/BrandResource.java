package brands.boundary;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import brands.entity.Brand;
import utils.AuthUtil;

@Path("/brands")
public class BrandResource {

	@EJB
	public BrandService brandService;

	@GET
	@Path("/")
	@Produces("application/json")
	public Response getAllBrands(@HeaderParam("authorization") final String authorization) {
		Response response = null;

		if (AuthUtil.verifyRoleInToken(authorization, "user")) {
			Brand[] brands = brandService.getAllBrands();
			if (brands.length > 0) {
				response = Response.status(200).entity(brands).build();
			} else {
				response = Response.status(404).build();
			}
		} else {
			response = Response.status(401).build();
		}

		return response;
	}
}
