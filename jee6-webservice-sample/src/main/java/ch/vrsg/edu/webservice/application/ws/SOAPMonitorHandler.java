package ch.vrsg.edu.webservice.application.ws;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.log4j.Logger;

public class SOAPMonitorHandler implements SOAPHandler<SOAPMessageContext> {

    @Inject
    private Logger logger;
    
    @Override
    public void close(MessageContext context) {
        // Clean up Resources
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        if (!logger.isTraceEnabled()) {
            return true;
        }
        final Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        final StringBuilder builder = new StringBuilder();
        if (outbound) {
            builder.append("SOAP message departing --> ");
        } else {
            builder.append("SOAP message incoming --> ");
        }
        builder.append(getMessage(context).orElse("No message data"));
        logger.trace(builder.toString());
        return true;
    }

    private Optional<String> getMessage(SOAPMessageContext context) {
        Optional<SOAPMessage> message = Optional.ofNullable(context.getMessage());
        if (message.isPresent()) {
            final StringBuilder builder = new StringBuilder();
            builder.append("Number of header elements: ").append(noOfHeaderElements(message.get()).orElse("No header elements"));
            builder.append(" | ");
            builder.append("Number of body elements: ").append(noOfBodyElements(message.get()).orElse("No body elements"));
            return Optional.of(builder.toString());
        }
        return Optional.empty();
    }
    
    private Optional<String> noOfBodyElements(SOAPMessage message) {
        try {
            Optional<SOAPBody> body = Optional.ofNullable(message.getSOAPBody());
            if (body.isPresent()) {
                return Optional.of(countElements(body.get().getChildElements()));
            }
        } catch (SOAPException e) {
            logger.warn(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Optional<String> noOfHeaderElements(SOAPMessage message) {
        try {
            Optional<SOAPHeader> header = Optional.ofNullable(message.getSOAPHeader());
            if (header.isPresent()) {
                return Optional.of(countElements(header.get().getChildElements()));
            }
        } catch (SOAPException e) {
            logger.warn(e.getMessage(), e);
        }
        return Optional.empty();
        
    }

    private String countElements(Iterator<?> i) {
        int count = 0;
        while (i.hasNext()) {
            count++;
            i.next();
        }
        return Integer.toString(count);
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

}
