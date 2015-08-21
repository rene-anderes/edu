package org.anderes.edu.relations.onetomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String description;
    
    @ManyToOne
    private Order order;
    
    OrderItem() {
        super();
    };
    
    public OrderItem(String item) {
        this.description = item;
    }

    public String getDescription() {
        return description;
    }

    public Order getOrder() {
        return order;
    }

    /* ------------- Pattern für JPA bidirektionale Beziehung ------------ */ 
    
    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.internalRemoveOrderItem(this);
        }
        this.order = order;
        if (order != null) {
            order.internalAddOrderItem(this);
        }
    }

    @PreRemove
    public void preRemove() {
        setOrder(null);
    }
    
    /* /------------ Pattern für JPA bidirektionale Beziehung ------------ */ 
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((order == null) ? 0 : order.hashCode());
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
        OrderItem other = (OrderItem) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (order == null) {
            if (other.order != null)
                return false;
        } else if (!order.equals(other.order))
            return false;
        return true;
    }

}
