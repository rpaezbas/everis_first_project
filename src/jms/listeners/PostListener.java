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

public class PostListener extends Listener implements MessageListener {

	// This constructor specifies the dateFormat expected in the json object
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

	@Override
	public void onMessage(Message message) {
		
		Log.logger.info("Enters PostListener.onMessage");
		
		TextMessage textMessage = (TextMessage) message;
		try {
			String jsonCar = textMessage.getText();
			Car carToSave = gson.fromJson(jsonCar, Car.class);
			super.carService.postCar(carToSave);
			Log.logger.info("Received message: " + textMessage.getText());
			Sender.sendMesg(textMessage.getText(), "Post");
		} catch (JMSException e) {
			Log.logger.warning(e.getMessage());
		}
		
		Log.logger.info("Exits PostListener.onMessage");
	}
}
