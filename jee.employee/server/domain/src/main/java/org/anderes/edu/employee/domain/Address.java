package org.anderes.edu.employee.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Einfache Adresse mit Basic-Mapping
 */
@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ADDRESS_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Basic
    private String city;
    @Basic
    private String country;
    @Basic
    private String province;
    @Basic
    @Column(name = "P_CODE")
    private String postalCode;
    @Basic
    private String street;

    public Address() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long addressId) {
        this.id = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
