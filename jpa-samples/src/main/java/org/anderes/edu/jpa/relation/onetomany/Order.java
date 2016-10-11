package org.anderes.edu.jpa.relation.onetomany;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "ORDERING")
public class Order {
    
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Long orderNumber;

    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "ORDER_ID")
    private Set<OrderItem> items = new HashSet<>();
    
    Order() {
        super();
    }
    
    public Order(final Long orderNumber) {
        super();
        this.orderNumber = orderNumber;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public Collection<OrderItem> getOrderItems() {
        return Collections.unmodifiableSet(items);
    }

   
    void removeOrderItem(OrderItem orderItem) {
        items.remove(orderItem);
    }

    void addOrderItem(OrderItem orderItem) {
        items.add(orderItem);
    }
}
