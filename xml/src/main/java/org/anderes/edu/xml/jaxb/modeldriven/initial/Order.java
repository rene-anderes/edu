package org.anderes.edu.xml.jaxb.modeldriven.initial;

import java.math.BigDecimal;

public class Order {

    private String product;
    private BigDecimal price;
    
    public Order(String product, BigDecimal price) {
        super();
        this.product = product;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
}
