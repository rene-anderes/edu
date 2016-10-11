package org.anderes.edu.jpa.relation.onetomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String description;
    
    OrderItem() {
        super();
    };
    
    public OrderItem(String item) {
        this.description = item;
    }

    public String getDescription() {
        return description;
    }
}
