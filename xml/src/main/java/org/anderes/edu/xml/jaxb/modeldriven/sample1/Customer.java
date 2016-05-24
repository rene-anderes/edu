package org.anderes.edu.xml.jaxb.modeldriven.sample1;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

    private long id;
    @XmlElement(name="fullname")
    private String name;
    @XmlElement(name = "orders")
    private List<Order> listOfOrder = new ArrayList<Order>();
    
    public Customer(String name) {
        this();
        this.name = name;
    }
    
    public Customer() {
        super();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    public List<Order> getOrders() {
        return listOfOrder;
    }
    
    public void addOrder(final Order order) {
        listOfOrder.add(order);
    }

    public void removeOrder(final Order order) {
        listOfOrder.remove(order);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
