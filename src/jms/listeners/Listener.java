package jms.listeners;

import javax.ejb.EJB;

import cars.boundary.Controller;

public class Listener {
	@EJB 
	Controller crudController = new Controller();
}
