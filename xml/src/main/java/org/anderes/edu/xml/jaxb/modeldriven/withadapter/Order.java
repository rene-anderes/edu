package org.anderes.edu.xml.jaxb.modeldriven.withadapter;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
    
    @XmlJavaTypeAdapter(OrderDateAdapter.class )
    private LocalDate oderDate;
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

    public LocalDate getOderDate() {
        return oderDate;
    }

    public void setOderDate(LocalDate oderDate) {
        this.oderDate = oderDate;
    }
}
