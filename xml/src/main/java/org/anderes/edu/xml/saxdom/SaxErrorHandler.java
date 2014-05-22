package org.anderes.edu.xml.saxdom;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SaxErrorHandler implements ErrorHandler {
    private Logger logger = Logger.getLogger(this.getClass().getPackage().getName());

    @Override
    public void error(final SAXParseException exception) throws SAXException {
        logger.log(Level.WARNING, exception.getMessage());
    }

    @Override
    public void fatalError(final SAXParseException exception) throws SAXException {
        logger.log(Level.WARNING, exception.getMessage());
    }

    @Override
    public void warning(final SAXParseException exception) throws SAXException {
        logger.log(Level.WARNING, exception.getMessage());
    }
}
