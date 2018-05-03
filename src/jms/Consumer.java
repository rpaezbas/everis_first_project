package jms;

import javax.ejb.Singleton;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import Logger.Log;
import jms.listeners.DeleteListener;
import jms.listeners.PostListener;
import jms.listeners.UpdateListener;

@Singleton
public class Consumer {

	Session session;
	Connection connection;
	ActiveMQConnectionFactory connectionFactory;
	MessageConsumer deleteConsumer;
	MessageConsumer postConsumer;
	MessageConsumer updateConsumer;

	Consumer() throws JMSException {
		
		Log.logger.info("Enters Consumer.Consumer");

		connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");

		connection = connectionFactory.createConnection();
		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		deleteConsumer = session.createConsumer(session.createQueue("activeMqQueue"), "JMSType = 'Delete'"); // Message selector																								
		deleteConsumer.setMessageListener(new DeleteListener());

		postConsumer = session.createConsumer(session.createQueue("activeMqQueue"), "JMSType = 'Post'");
		postConsumer.setMessageListener(new PostListener());

		updateConsumer = session.createConsumer(session.createQueue("activeMqQueue"), "JMSType = 'Update'");
		updateConsumer.setMessageListener(new UpdateListener());
		
		Log.logger.info("Exits Consumer.Consumer");

	}

	public void destroy() {
		try {
			session.close();
		} catch (JMSException e) {
			Log.logger.warning(e.getMessage());
		}
	}
}