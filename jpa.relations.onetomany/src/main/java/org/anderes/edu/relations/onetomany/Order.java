package org.anderes.edu.relations.onetomany;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

@Entity(name = "ORDERING")
public class Order {
    
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Long orderNumber;

    @OneToMany(mappedBy="order", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();
    
    Order() {
        super();
    }
    
    public Order(Long orderNumber) {
        super();
        this.orderNumber = orderNumber;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }


    public Collection<OrderItem> getOrderItems() {
        return Collections.unmodifiableSet(items);
    }

    /* ------------- Pattern für JPA bidirektionale Beziehung ------------ */ 

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }
    
    public void removeOrderItem(OrderItem orderItem) {
        if (!items.contains(orderItem)) {
            throw new IllegalArgumentException("Die Bestellposition ist in der Bestellung nicht vorhanden.");
        }
        orderItem.setOrder(null);
    }
    
    public void internalRemoveOrderItem(OrderItem orderItem) {
        items.remove(orderItem);
    }

    public void internalAddOrderItem(OrderItem orderItem) {
        items.add(orderItem);
    }
    
    @PreRemove
    public void preRemove() {
        final HashSet<OrderItem> orderItems = new HashSet<OrderItem>(getOrderItems());
        for (OrderItem orderItem : orderItems) {
            removeOrderItem(orderItem);
        }
    }

    /* /----------- Pattern für JPA bidirektionale Beziehung ------------ */ 
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderNumber == null) ? 0 : orderNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (orderNumber == null) {
            if (other.orderNumber != null)
                return false;
        } else if (!orderNumber.equals(other.orderNumber))
            return false;
        return true;
    }

}
