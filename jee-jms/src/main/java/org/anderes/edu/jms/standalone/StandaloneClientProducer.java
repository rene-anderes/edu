package org.anderes.edu.jms.standalone;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class StandaloneClientProducer {

    private InitialContext jndiContext;
    private ConnectionFactory connectionFactory;
    private Destination destination;
    
    public static void main(String[] args) {
        final StandaloneClientProducer client = new StandaloneClientProducer();
        final String text = String.format("This is the TEST message, sent at %1$s", LocalTime.now());
        
        try {
            client.setup();
        } catch (NamingException e) {
            System.err.println("Could not create JNDI API context: " + e.toString());
        } catch (IOException e) {
            System.err.println("Could not read properties-file.");
        }
        try {
            client.publishMessage(text);
        } catch ( JMSException e) {
            System.err.println("Could not send the message: " + e.toString());
        }
    }
    
    void setup() throws NamingException, IOException {
            final Properties enviroment = new Properties();
            enviroment.load(getClass().getResourceAsStream("/jndi.properties"));
            
            jndiContext = new InitialContext(enviroment);
            connectionFactory = (ConnectionFactory) jndiContext.lookup("jms/__defaultConnectionFactory");
            destination = (Destination) jndiContext.lookup("jms/eduTopic");
    }
    
    void publishMessage(final String text) throws JMSException {
        
        final Connection connection = connectionFactory.createConnection();
        final Session session = connection.createSession();
        final MessageProducer producer = session.createProducer(destination);
        final TextMessage message = session.createTextMessage();
        
        message.setText(text);
        producer.send(message);
    }
}
