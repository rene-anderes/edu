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
public class LogicalLoggingHandler implements LogicalHandler<LogicalMessageContext> {

    @Inject
    private Logger logger;

    @Override
    public void close(MessageContext context) {
        // Clean up Resources
    }

    @Override
    public boolean handleFault(LogicalMessageContext context) {
        return processMessage(context);
    }

    @Override
    public boolean handleMessage(LogicalMessageContext context) {
        return processMessage(context);
    }

    private boolean processMessage(LogicalMessageContext context) {
        if (!logger.isTraceEnabled()) {
            return true;
        }
        final Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            if (outboundProperty) {
                outputStream.write("Outbound message:\n".getBytes());
            } else {
                outputStream.write("Inbound message:\n".getBytes());
            }
            LogicalMessage lm = context.getMessage();
            Source payload = lm.getPayload();
            printSource(payload, outputStream);
            logger.trace(outputStream.toString());
        } catch (IOException | TransformerException e) {
            logger.warn(e);
        }
        return true;
    }

    private void printSource(final Source payload, final OutputStream outputStream) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Result result = new StreamResult(outputStream);
        transformer.transform(payload, result);
    }
}
