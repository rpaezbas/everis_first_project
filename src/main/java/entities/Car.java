package entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import controller.JSONRequest;

@Entity
@Table(name = "Car")
public class Car {
	
	public Car() {
	}
	
	public Car(JSONRequest jsonRequest) {
		this.setId(jsonRequest.id);
		this.setBrand(jsonRequest.brand);
		this.setCountry(jsonRequest.country);
		this.setRegistration( new Timestamp (jsonRequest.registration));
		this.setCreatedAt( new Timestamp (jsonRequest.createdAt));
		this.setLastUpdated(new Timestamp (jsonRequest.lastUpdated));
	}
	
	public void update(JSONRequest jsonRequest) {
		this.setBrand(jsonRequest.brand);
		this.setCountry(jsonRequest.country);
		this.setRegistration( new Timestamp (jsonRequest.registration));
		this.setCreatedAt( new Timestamp (jsonRequest.createdAt));
		this.setLastUpdated(new Timestamp (jsonRequest.lastUpdated));
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id",nullable = true)
	private int id;
	
	@Column(name = "brand",nullable = false)
	private String brand;

	@Column(name = "registration",nullable = false)
	private Timestamp registration; 
	
	@Column(name = "country",nullable = false)
	private String country;
	
	@Column(name = "createdAt",nullable = false)
	private Timestamp createdAt;
	
	@Column(name = "lastUpdated",nullable = false)
	private Timestamp lastUpdated;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the registration
	 */
	public Timestamp getRegistration() {
		return registration;
	}

	/**
	 * @param registration the registration to set
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
	 * @param country the country to set
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
	 * @param createdAt the createdAt to set
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
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	

}
