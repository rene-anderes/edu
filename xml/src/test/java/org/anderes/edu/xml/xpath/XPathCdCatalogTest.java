package org.anderes.edu.xml.xpath;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathCdCatalogTest {

    private XPath xPath;
    private InputSource inputSource;
    private final InputStream xmlInputStream = getClass().getResourceAsStream("/org/anderes/edu/xml/xpath/cd_catalog.xml");
    
    @Before
    public void setUp() throws Exception {
        inputSource = new InputSource(xmlInputStream);
        xPath = XPathFactory.newInstance().newXPath();
    }

    @After
    public void tearDown() {
        try {
            xmlInputStream.close();
        } catch (IOException e) {
            fail("Der InputStream konnte nich geschlossen werden");
        }
    }
    
    @Test
    public void shouldBeAllCdsFromWillSmith() throws XPathExpressionException {
        final String expression = "//cd[contains(artist, 'Will Smith')]";
        final NodeList cdList = (NodeList) xPath.evaluate(expression, inputSource, XPathConstants.NODESET);
        assertThat(cdList.getLength(), is(1));
        final Node cd = cdList.item(0);
        final NodeList cdNodeList = cd.getChildNodes();
        for (int i = 0; i < cdNodeList.getLength(); i++) {
            if (cdNodeList.item(i).getNodeName().equals("artist")) {
                assertThat(cdNodeList.item(i).getTextContent(), is("Will Smith"));
            }
        }
    }
    
}
