package org.anderes.edu.xml.xpath;

import static javax.xml.xpath.XPathConstants.NODE;
import static javax.xml.xpath.XPathConstants.NODESET;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.Validate;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathEvaluator {

    public interface XPathQuery {
        /**
         * Gibt einen bestimmten Node als Resultat der Query zurück.
         * 
         * @param query XPath-Query
         * @return Node
         * @throws XPathExpressionException If expression cannot be evaluated.
         */
        public Node getNode(String query) throws XPathExpressionException;
        /**
         * Gibt einen Node-Liste als Resultat der Query zurück.
         * 
         * @param query XPath-Query
         * @return Node-Liste
         * @throws XPathExpressionException If expression cannot be evaluated.
         */
        public NodeList getNodeList(String query) throws XPathExpressionException;
        /**
         * Gibt einen Text als Resultat der Query zurück.
         * 
         * @param query XPath-Query
         * @return Text
         * @throws XPathExpressionException If expression cannot be evaluated.
         */
        public String getString(String query) throws XPathExpressionException;
        /**
         * Gibt eine Zahl als Resultat der Query zurück.
         * 
         * @param query XPath-Query
         * @return Zahl
         * @throws XPathExpressionException If expression cannot be evaluated.
         */
        public int getNumber(String query) throws XPathExpressionException;
        /**
         * Gibt einen Boolean-Wert als Resultat der Query zurück.
         * 
         * @param query XPath-Query
         * @return Boolean-Wert
         * @throws XPathExpressionException If expression cannot be evaluated.
         */
        public boolean getBoolean(String query) throws XPathExpressionException;
        
        /**
         * Null-Object-Pattern
         */
        public class NULL implements XPathQuery {
            @Override
            public boolean getBoolean(String query) throws XPathExpressionException {
                throw new XPathExpressionException("NULL-Object");
            }
            @Override
            public Node getNode(String query) throws XPathExpressionException {
                throw new XPathExpressionException("NULL-Object");
            }
            @Override
            public NodeList getNodeList(String query) throws XPathExpressionException {
                throw new XPathExpressionException("NULL-Object");
            }
            @Override
            public int getNumber(String query) throws XPathExpressionException {
                throw new XPathExpressionException("NULL-Object");
            }
            @Override
            public String getString(String query) throws XPathExpressionException {
                throw new XPathExpressionException("NULL-Object");
            }
        } 
    }
    
    public static  XPathQuery withFile(final InputStream xmlInputStream) {
        Validate.notNull(xmlInputStream);
        try {
            return new XPathQueryImpl(xmlInputStream);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            return new XPathQuery.NULL();
        }
    }
    
    public static  XPathQuery withFile(final Path xmlFilePath) {
        Validate.notNull(xmlFilePath);
        return withFile(xmlFilePath.toFile());
    }
    
    public static XPathQuery withFile(final File xmlFile) {
        Validate.notNull(xmlFile);
        if (!xmlFile.exists() || !xmlFile.isFile()) {
            throw new IllegalArgumentException("Ungültiger Parameter: " + xmlFile);
        }
        try {
            return new XPathQueryImpl(xmlFile);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            return new XPathQuery.NULL();
        }
    }
    
    public static XPathQuery withFile(final String xmlFilename) {
        Validate.notBlank(xmlFilename);
        return withFile(new File(xmlFilename));
    }
  
    private static class XPathQueryImpl implements XPathQuery {

        private XPath xpath;
        private Document domTree;
        
        public XPathQueryImpl(final File xmlFile) throws ParserConfigurationException, SAXException, IOException {
            XPathFactory factory = XPathFactory.newInstance();
            xpath = factory.newXPath();
            try (InputStream inputStream = new FileInputStream(xmlFile)) {
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                domTree = builder.parse(inputStream);
            } 
        }

        public XPathQueryImpl(final InputStream xmlInputStream) throws ParserConfigurationException, SAXException, IOException {
            XPathFactory factory = XPathFactory.newInstance();
            xpath = factory.newXPath();
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            domTree = builder.parse(xmlInputStream);
        }

        @Override
        public boolean getBoolean(String query) throws XPathExpressionException {
            Validate.notBlank(query);
            return !getString(query).isEmpty();
        }

        @Override
        public Node getNode(String query) throws XPathExpressionException {
            Validate.notBlank(query);
            return (Node)xpath.evaluate(query, domTree, NODE);
        }

        @Override
        public NodeList getNodeList(String query) throws XPathExpressionException {
            Validate.notBlank(query);
            return (NodeList) xpath.evaluate(query, domTree, NODESET);
        }

        @Override
        public int getNumber(String query) throws XPathExpressionException {
            Validate.notBlank(query);
            try {
                return Integer.parseInt(getString(query));
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        @Override
        public String getString(String query) throws XPathExpressionException {
            Validate.notBlank(query);
            final Node result = getNode(query);
            if (result != null) {
                return result.getTextContent().trim();
            }
            return "";
        }
    }
   
    public static void dumpNodeList(final NodeList nodelist) {
        Validate.notNull(nodelist);
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node n = nodelist.item(i);
            dumpNode(n);
        }
    }
    
    public static void dumpNode(final Node node) {
        Validate.notNull(node);
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                System.out.println("Element node named \"" + node.getNodeName() + "\"");
                NamedNodeMap attributes = node.getAttributes();
                for (int at = 0; at < attributes.getLength(); at++) {
                    dumpNode(attributes.item(at));
                }
                break;
            case Node.ATTRIBUTE_NODE:
                System.out.println("Attribute node named \""
                        + node.getNodeName() + "\" with value '" + node.getNodeValue() + "'");
                break;
            case Node.TEXT_NODE:
                System.out.println("Text: '" + node.getNodeValue().trim() + "'");
                break;
            default: System.out.println(node);
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node n = childNodes.item(i);
            dumpNode(n);
        }
    }
}
