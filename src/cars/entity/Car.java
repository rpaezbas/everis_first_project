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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import brands.entity.Brand;
import validations.ValidDate;

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

	//This is a custom validation see the package ultils.validations
	@ValidDate
	@NotNull(message = "The value for registration cannot be empty.")
	@Column(name = "registration")
	private Timestamp registration;

	@NotBlank(message = "The value for country cannot be null or empty.")
	@NotEmpty(message = "The value for country cannot be null or whitespace.")
	@Column(name = "country")
	private String country;

	@NotNull(message = "The value for createdAt cannot be empty.")
	@Past(message = "The date for the propierty createdAt must be preavious than today's date.")
	@Column(name = "createdAt")
	private Timestamp createdAt;

	@NotNull(message = "The value for lastUpdated cannot be empty.")
	@Column(name = "lastUpdated")
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
