package org.anderes.edu.jms.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(mappedName = "jms/eduTopic",
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
    })
public class MessageDrivenBeanTopic implements MessageListener {

    @Override
    public void onMessage(Message message) {
        
        try {
            if (message instanceof TextMessage) {
                final TextMessage msg = (TextMessage) message;
                System.out.format("MESSAGE BEAN (%s): Message received: '%s'\n", this.getClass().getName(), msg.getText());
            } else {
                System.out.println("Message of wrong type: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            System.err.println(e.getMessage());
        }
    }

}
