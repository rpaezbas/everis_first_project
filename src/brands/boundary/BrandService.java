package brands.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import com.mongodb.MongoClient;
import brands.entity.Brand;

@Stateless
public class BrandService {
	
	String dbName = new String("test");
	MongoClient mongo = new MongoClient();
	Morphia morphia = new Morphia();
	Datastore datastore = morphia.createDatastore(mongo, dbName);

	public Response response;
	
	public Brand[] getAllBrands(){
		
		List<Brand> brands = datastore.find(Brand.class).asList();
		Brand[] arrayBrand = brands.toArray(new Brand[brands.size()]);
		
		return arrayBrand;
	}

}