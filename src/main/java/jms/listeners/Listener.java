package jms.listeners;

import javax.ejb.EJB;

import car.boundary.CarService;

public class Listener {
	@EJB 
	CarService crudController;
}
