package jms.listeners;

import javax.ejb.EJB;

import cars.boundary.CarResource;
import cars.boundary.CarService;

public class Listener {
	@EJB 
	CarService carService;
}
