package cars.entity;


import java.sql.Timestamp;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import brands.entity.Brand;

@Entity
public class Car {

	public Car() {
	}

	@Id
    protected String id;

	private Timestamp registration;

	private String country;

	private Timestamp createdAt;

	private Timestamp lastUpdated;

	@Reference
	private Brand brand;

	/**
	 * @return the brand
	 */

	public Brand getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the registration
	 */
	public Timestamp getRegistration() {
		return registration;
	}

	/**
	 * @param registration
	 *            the registration to set
	 */
	public void setRegistration(Timestamp registration) {
		this.registration = registration;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the createdAt
	 */
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the lastUpdated
	 */
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @param lastUpdated
	 *            the lastUpdated to set
	 */
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void copyValuesFrom(Car car) {
		this.setBrand(car.getBrand());
		this.setCountry(car.getCountry());
		this.setCreatedAt(car.getCreatedAt());
		this.setLastUpdated(car.getLastUpdated());
		this.setRegistration(car.getRegistration());
		this.setBrand(car.getBrand());
	}

	@Override
	public String toString() {
		return " Brand:" + this.brand.toString() + " Country:" + this.country + " CreateAt:"
				+ this.createdAt + " LastUpdated:" + this.lastUpdated + " registration: " + this.registration;
	}

}
