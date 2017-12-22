package org.anderes.edu.jpa.beanvalidation;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Car {

    @Id
    public String id = UUID.randomUUID().toString();
    
    @NotNull @Size(min = 2, max = 25)
    private String color;
    
    @NotNull @Min(value = 1000000) @Max(value = 9999999)
    private Integer serialNumber;

    public String getColor() {
        return color;
    }

    public Car setColor(String color) {
        this.color = color;
        return this;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public Car setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getId() {
        return id;
    }
}
