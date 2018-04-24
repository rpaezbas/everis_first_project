package jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Logger.Log;
import cars.boundary.Controller;
import cars.entity.Car;

class UpdateListener implements MessageListener {

	// This constructor specifies the dateFormat expected in the json object
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
	Controller crudController = new Controller();

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String carIdAsString = textMessage.getStringProperty("carId");
			int carId = Integer.parseInt(carIdAsString);
			String jsonCar = textMessage.getText();
			Car carToUpdate = gson.fromJson(jsonCar, Car.class);
			crudController.updateCar(carToUpdate, carId);
			Log.logger.info("Received message:" + textMessage.getText());
			Sender.sendMesg(textMessage.getText(), "Update");
		} catch (JMSException e) {
			Log.logger.warning(e.getMessage());
		}
	}
}
