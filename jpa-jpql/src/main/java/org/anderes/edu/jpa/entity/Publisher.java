package org.anderes.edu.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Publisher {

    @Id @GeneratedValue
    private Long id;
    
    private String name;
}
