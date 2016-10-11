package org.anderes.edu.jpa.inheritance.mappedsuperclass;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CREDITCARD_PAYMENT")
public class CreditCard extends Payment {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
