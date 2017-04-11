package org.anderes.edu.exam.theory;

public class Address {

    private String street;
    private String town;
    private int zipCode;

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setTown(String town) {
        this.town = town;
        
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getTown() {
        return town;
    }

}
