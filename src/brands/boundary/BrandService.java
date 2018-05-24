package brands.boundary;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Session;

import brands.entity.Brand;
import cars.entity.Car;
import utils.HibernateUtil;

@Stateless
public class BrandService {
	
	public Session session = HibernateUtil.getSessionFactory().openSession();
	
	public List<Brand> getAllBrands(){
		session.beginTransaction();
		List<Brand> brands = session.createQuery("from Brand").list();
		session.getTransaction().commit();
		return brands;
	}

}