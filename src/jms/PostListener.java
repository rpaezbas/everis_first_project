package jms;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import com.google.gson.Gson;
import cars.boundary.Controller;
import cars.entity.Car;

class PostListener implements MessageListener {

	static Gson gson = new Gson();

	@EJB
	Controller statelessCrudEJB;

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println("Type: " +  textMessage.getJMSType());
			System.out.println("PostListener received message: " + textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
