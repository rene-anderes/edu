package org.anderes.edu.xml.jaxb.modeldriven;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {

    @XmlAttribute(name="identifier")
    private long id;
    private String name;
    
    @XmlElement
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    private List<Order> listOfOrder = new ArrayList<Order>();
    
    @XmlElement(name = "orders")
    public List<Order> getOrders() {
        return listOfOrder;
    }

    public Customer(String name) {
        this();
        this.name = name;
    }

    public Customer() {
        super();
    }
}
