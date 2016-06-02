package ch.vrsg.edu.webservice.application.ws;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

/**
 * Der {@code LogicalHandler} liest alle Daten aus den Paylod
 * ({@code LogicalMessageContext}) des SOAP Body einer SOAP Message aus
 * und schreibt diese Informationen ins Log.
 * <p/>
 * 
 * @author René Anderes
 * @see {@link https://jax-ws.java.net/articles/handlers_introduction.html}
 *
 */
public class PayloadLoggingHandler implements LogicalHandler<LogicalMessageContext> {

    @Inject
    private Logger logger;

    @Override
    public void close(MessageContext context) {
        // nothing to do...
    }

    @Override
    public boolean handleFault(LogicalMessageContext context) {
        return processMessage(context);
    }

    @Override
    public boolean handleMessage(LogicalMessageContext context) {
        return processMessage(context);
    }

    private boolean processMessage(final LogicalMessageContext context) {
        if (!logger.isTraceEnabled()) {
            return true;
        }
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            if (isOutboundProperty(context)) {
                outputStream.write("Outbound message:\n".getBytes());
            } else {
                outputStream.write("Inbound message:\n".getBytes());
            }
            final LogicalMessage lm = context.getMessage();
            final Source payload = lm.getPayload();
            printSource(payload, outputStream);
            logger.trace(outputStream.toString());
        } catch (IOException | TransformerException e) {
            logger.warn(e);
        }
        return true;
    }
    
    private Boolean isOutboundProperty(final LogicalMessageContext context) {
        return (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    }

    private void printSource(final Source payload, final OutputStream outputStream) throws TransformerException {
        final TransformerFactory factory = TransformerFactory.newInstance();
        final Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        final Result result = new StreamResult(outputStream);
        transformer.transform(payload, result);
    }
}
