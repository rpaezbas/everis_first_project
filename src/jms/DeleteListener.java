package jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import cars.boundary.Controller;

class DeleteListener implements MessageListener {

	Controller crudController = new Controller();

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			int carToDeleteId = Integer.parseInt(textMessage.getText());
			crudController.deleteCar(carToDeleteId);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
