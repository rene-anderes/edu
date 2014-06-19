//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.06.19 um 12:05:02 PM CEST 
//


package org.anderes.edu.employee.application.boundary.dto;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.anderes.edu.employee.application.boundary.dto package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Jobtitle_QNAME = new QName("", "jobtitle");
    private final static QName _Lastname_QNAME = new QName("", "lastname");
    private final static QName _Firstname_QNAME = new QName("", "firstname");
    private final static QName _Salary_QNAME = new QName("", "salary");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.anderes.edu.employee.application.boundary.dto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EmployeeDto }
     * 
     */
    public EmployeeDto createEmployeeDto() {
        return new EmployeeDto();
    }

    /**
     * Create an instance of {@link Employees }
     * 
     */
    public Employees createEmployees() {
        return new Employees();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "jobtitle")
    public JAXBElement<String> createJobtitle(String value) {
        return new JAXBElement<String>(_Jobtitle_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lastname")
    public JAXBElement<String> createLastname(String value) {
        return new JAXBElement<String>(_Lastname_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "firstname")
    public JAXBElement<String> createFirstname(String value) {
        return new JAXBElement<String>(_Firstname_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "salary")
    public JAXBElement<BigDecimal> createSalary(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Salary_QNAME, BigDecimal.class, null, value);
    }

}
