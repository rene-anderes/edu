package org.anderes.edu.xml.jaxb;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.anderes.edu.xml.jaxb.generated.ObjectFactory;
import org.junit.Test;

public class CheckJAXBImplementationTest {

    private Logger logger = Logger.getLogger(this.getClass().getPackage().getName());
    
    private enum JAXB_CONTEXT { 
        /** javax.xml.bind.context.factory=org.eclipse.persistence.jaxb.JAXBContextFactory */
        MOXY_JAXB_CONTEXT("org.eclipse.persistence.jaxb.JAXBContext", "Eclipselink MOXy"),
        /** javax.xml.bind.context.factory=com.sun.xml.bind.v2.ContextFactory */
        METRO_JAXB_CONTEXT("com.sun.xml.bind.v2.runtime.JAXBContextImpl", "Glassfish Metro"),
        /** javax.xml.bind.context.factory=com.sun.xml.internal.bind.v2.ContextFactory */
        JDK_JAXB_CONTEXT("com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl", "JDK internal"),
        UNKNOWN("", "");
        
        private String jaxbImplName;
        private String jaxbName;

        private JAXB_CONTEXT(String jaxbImplName, final String jaxbName) {
            this.jaxbImplName = jaxbImplName;
            this.jaxbName = jaxbName;
        }
        
        public static String getJaxbName(final String jaxbImplName) {
            JAXB_CONTEXT result = UNKNOWN;
            for (JAXB_CONTEXT jc : JAXB_CONTEXT.values()) {
                if (jc.jaxbImplName.equals(jaxbImplName)) {
                    result = jc;
                }
            }
            return result.jaxbName;
        }
    }
   
    @Test
    public void jaxbImplementationTest() throws JAXBException {
        final JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
        String jaxbname = JAXB_CONTEXT.getJaxbName(jc.getClass().getName());
        assertThat(jaxbname, is("Glassfish Metro"));
        logger.info("JAXB Implementation: " + jaxbname);
    }

}
