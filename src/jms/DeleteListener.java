package jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import cars.boundary.Controller;
import Logger.Log;

class DeleteListener implements MessageListener {

	Controller crudController = new Controller();

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			int carToDeleteId = Integer.parseInt(textMessage.getText());
			crudController.deleteCar(carToDeleteId);
			Log.logger.info("Received message: " + textMessage.getText());
			Sender.sendMesg(textMessage.getText(), "Delete");
		} catch (JMSException e) {
			Log.logger.warning(e.getMessage());
		}
	}
	
}
