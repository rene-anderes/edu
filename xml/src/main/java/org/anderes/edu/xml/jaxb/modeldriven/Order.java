package org.anderes.edu.xml.jaxb.modeldriven;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Order {
    
    @XmlElement
    @XmlJavaTypeAdapter(OrderDateAdapter.class )
    private LocalDate oderDate;

}
