package jms.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Logger.Log;
import cars.entity.Car;
import jms.Sender;

public class UpdateListener extends Listener implements MessageListener{

	// This constructor specifies the dateFormat expected in the json object
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

	@Override
	public void onMessage(Message message) {
		
		Log.logger.info("Enters UpdateListener.onMessage");
		
		TextMessage textMessage = (TextMessage) message;
		try {
			String carIdAsString = textMessage.getStringProperty("carId");
			String carId = carIdAsString;
			String jsonCar = textMessage.getText();
			Car carToUpdate = gson.fromJson(jsonCar, Car.class);
			Car deprecatedCar = super.carService.getCar(carId);
			// super.carService.updateCar(carToUpdate, deprecatedCar);
			Log.logger.info("Received message:" + textMessage.getText());
			Sender.sendMesg(textMessage.getText(), "Update");
		} catch (JMSException e) {
			Log.logger.warning(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.logger.info("Exits UpdateListener.onMessage");
		
	}
}
