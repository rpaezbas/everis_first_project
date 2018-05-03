package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	
	// URL of the JMS server.
	private static String url ="tcp://localhost:61616";
	// Queue Name.
	private static String subject = "pasiveMqQueue";

	public static void sendMesg(String messageToSend, String JMSType) throws JMSException {
		
		// Getting JMS connection from the server and starting it
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		// Creating a non transactional session to send/receive JMS message.
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(subject);
		MessageProducer producer = session.createProducer(destination);
		TextMessage message = session.createTextMessage(messageToSend);
		message.setJMSType(JMSType);
		producer.send(message);
		connection.close();
	}
}