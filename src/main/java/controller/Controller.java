package controller;

import java.sql.Timestamp;
public class Controller {
	
	private Controller() {	
	}
	
	public static Controller core;
	
	//Convert millis to timestamp
	public Timestamp millisToTimestamp(long seconds) {
		return new Timestamp(seconds);
	}
}
