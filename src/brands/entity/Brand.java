package brands.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cars.entity.Car;

@Entity
@Table(name = "Brand")
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = true)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "brand")
//	private List<Car> cars;
//

	
	
//	/**
//	 * @return the cars
//	 */
//	public final List<Car> getCars() {
//		return cars;
//	}
//
//	/**
//	 * @param cars
//	 *            the cars to set
//	 */
//	public final void setCars(List<Car> cars) {
//		this.cars = cars;
//	}
	
	

	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "id: " + this.id + " name: " + this.name;
	}

}
