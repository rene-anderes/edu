package org.anderes.edu.jms.standalone;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class StandaloneClientSubscriber {
    
    private static final int TIMEOUT = 30000;
    private InitialContext jndiContext;
    private ConnectionFactory connectionFactory;
    private Destination destination;

    public static void main(String[] args) {
        final StandaloneClientSubscriber client = new StandaloneClientSubscriber();
        
        try {
            client.setup();
        } catch (NamingException e) {
            System.err.println("Could not create JNDI API context: " + e.toString());
        } catch (IOException e) {
            System.err.println("Could not read properties-file.");
        }
        try {
            client.subscribe();
        } catch (JMSException e) {
            System.err.println("Exception occurred: " + e.toString());
        }
        

        System.out.format("---------------- Warten auf Messages (%sms) %n", TIMEOUT);
        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            // nothing to do ...
        }
        System.out.println("---------------- die Zeit ist abgelaufen");
    }
    
    void setup() throws NamingException, IOException {
        final Properties enviroment = new Properties();
        enviroment.load(getClass().getResourceAsStream("/jndi.properties"));
        
        jndiContext = new InitialContext(enviroment);
        connectionFactory = (ConnectionFactory) jndiContext.lookup("jms/__defaultConnectionFactory");
        destination = (Destination) jndiContext.lookup("jms/eduTopic");
    }
    
    private void subscribe() throws JMSException {
        final TopicConnection connection = (TopicConnection) connectionFactory.createConnection();
        final TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicSubscriber subscriber = session.createSubscriber((Topic) destination);
        subscriber.setMessageListener(new MessageListenerTopic());
        connection.start();
    }
    
    public static class MessageListenerTopic implements MessageListener {
        @Override
        public void onMessage(Message message) {
            if (message instanceof TextMessage) {
                final TextMessage msg = (TextMessage) message;
                try {
                    System.out.format("standalone client - Message received at %s: '%s' %n", LocalTime.now(), msg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Message of wrong type");
            }
        }
    }
}
