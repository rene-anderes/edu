package org.anderes.edu.appengine.cookbook.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tagElement")
public class TagDto {
    private String name;
    private int quantity;
    public TagDto(String name, int quantity) {
        super();
        this.name = name;
        this.quantity = quantity;
    }
    public TagDto() {
        this.name = "";
        this.quantity = 0;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
