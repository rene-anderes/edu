package org.anderes.edu.jms.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

@Stateless
public class EnterpriseJavaBeanProducer {

    @Resource(mappedName="jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName="jms/eduTopic")
    private Topic destination;
    
    public void publishMessage(String text) throws JMSException {
        
        final Connection connection = connectionFactory.createConnection();
        final Session session = connection.createSession();
        final MessageProducer producer = session.createProducer(destination);
        final TextMessage message = session.createTextMessage();
        
        message.setText(text);
        producer.send(message);

    }

}
