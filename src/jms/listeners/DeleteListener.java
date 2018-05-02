package jms.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import Logger.Log;
import jms.Sender;

public class DeleteListener extends Listener implements MessageListener {

	// The message received by this listener only contains the id of the car whe
	// want to delete and the JMSType 'Delete', once it passes the order to the
	// crud, it sends the message to other queue

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			int carToDeleteId = Integer.parseInt(textMessage.getText());
			super.crudController.deleteCar(carToDeleteId);
			Log.logger.info("Received message: " + textMessage.getText());
			Sender.sendMesg(textMessage.getText(), "Delete");
		} catch (JMSException e) {
			Log.logger.warning(e.getMessage());
		}
	}

}
