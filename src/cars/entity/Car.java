package cars.entity;

import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import brands.entity.Brand;

@Cacheable(true)
@Entity
@Table(name = "Car")
public class Car {

	public Car() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = true)
	private int id;

	@Column(name = "registration", nullable = false)
	private Timestamp registration;

	@Column(name = "country", nullable = false)
	private String country;

	@Column(name = "createdAt", nullable = false)
	private Timestamp createdAt;

	@Column(name = "lastUpdated", nullable = false)
	private Timestamp lastUpdated;

	@ManyToOne
	@JoinColumn(name = "brand")
	private Brand brand;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

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
		return "Id:" + this.id + " Brand:" + this.brand.toString() + " Country:" + this.country + " CreateAt:"
				+ this.createdAt + " LastUpdated:" + this.lastUpdated + " registration: " + this.registration;
	}

}
