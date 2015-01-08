package org.anderes.edu.xml.xpath;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.anderes.edu.xml.xpath.XPathEvaluator.XPathQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathEvaluatorTest {

    private InputStream xmlInputStream;
    
    @Before
    public void setUp() throws Exception {
        xmlInputStream = getClass().getResourceAsStream("/org/anderes/edu/xml/introduction/contactlist.xml");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldBeTwoContacts() throws Exception {
        final XPathQuery xpathQuery = XPathEvaluator.withFile(xmlInputStream);
        final NodeList nodelist = xpathQuery.getNodeList("//contact");
        assertThat(nodelist, is(notNullValue()));
        assertThat(nodelist.getLength(), is(2));
    }
    
    @Test
    public void shouldBeOneContact() throws Exception {
        final XPathQuery xpathQuery = XPathEvaluator.withFile(xmlInputStream);
        final Node node = xpathQuery.getNode("//contact[name='Leonardo Da Vinci']");
        assertThat(node, is(notNullValue()));
    }
    
    @Test
    public void shouldBeName() throws Exception {
        final XPathQuery xpathQuery = XPathEvaluator.withFile(xmlInputStream);
        final String text = xpathQuery.getString("//contact[name='Leonardo Da Vinci']/name");
        assertThat(text, is(notNullValue()));
        assertThat(text, is("Leonardo Da Vinci"));
    }

}
