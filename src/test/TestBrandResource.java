package test;


import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;
import brands.boundary.BrandResource;
import brands.boundary.BrandService;
import brands.entity.Brand;
import junit.framework.TestCase;

public class TestBrandResource extends TestCase {

	BrandResource brandResource = new BrandResource();
	String userValidToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJodHRwczovL3JwLmNvbS91c2VyX21ldGFkYXRhIjp7InJvbCI6ImFkbWluIn0sIm5pY2tuYW1lIjoicnBhZXpiYXMiLCJuYW1lIjoicnBhZXpiYXNAZXZlcmlzLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci8wMTZhNzA2NWIxZjZmMjcxNGVkNTVmNzBjZDg0NDFiYz9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRnJwLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDE4LTA1LTI0VDEwOjM2OjIyLjIyNFoiLCJpc3MiOiJodHRwczovL3JwYWV6YmFzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw1YWNiOTM0NTQzYzg3ZjExNDllZmRiMWMiLCJhdWQiOiJ4Nm1FcTF4cVdrcjczMEVLTUQ0M043Z1kyMjdDWm1wZSIsImlhdCI6MTUyNzE1ODE4NCwiZXhwIjoxNTI4MTU4MTg0fQ.V5QhWh8F9I_UIVCP1WgPzn_FsNxHMUyqR3gd1Ybtc6k";
	String invalidToken = "Bearer invalid.token.token";
	
	BrandService mockBrandService = Mockito.mock(BrandService.class);
	Brand brand = new Brand();
	List<Brand> mockBrandList = new ArrayList<Brand>();
	List<Brand> mockBrandListEmpty = new ArrayList<Brand>();

	public void setUp() {
		brandResource.brandService = mockBrandService;
		mockBrandList.add(brand);
	}
	
	public void testBrandServiceGetAllBrands() {
		
		assertEquals(brandResource.getAllBrands(invalidToken).getStatus(), 401);
		
		Mockito.when(mockBrandService.getAllBrands()).thenReturn(mockBrandList);
		assertEquals(brandResource.getAllBrands(userValidToken).getStatus(), 200);
		
		Mockito.when(mockBrandService.getAllBrands()).thenReturn(mockBrandListEmpty);
		assertEquals(brandResource.getAllBrands(userValidToken).getStatus(), 404);
	}
	


}
