package org.anderes.edu.xml.jaxb.modeldriven.initial;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private long id;
    private String name;
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
