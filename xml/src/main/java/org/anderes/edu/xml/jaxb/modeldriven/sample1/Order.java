package org.anderes.edu.xml.jaxb.modeldriven.sample1;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
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
