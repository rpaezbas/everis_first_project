package jms;

import javax.ejb.Singleton;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

@Singleton
public class Consumer {
	public Consumer() throws JMSException {
		
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.start();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(session.createQueue("activeMqQueue"));
        consumer.setMessageListener(new Listener());
        
    }
}