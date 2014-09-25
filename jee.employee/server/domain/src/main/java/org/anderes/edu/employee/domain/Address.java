package org.anderes.edu.employee.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(street).append(postalCode).append(province).append(city).append(country).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        final Address rhs = (Address) obj;
        return new EqualsBuilder().append(street, rhs.street).append(postalCode, rhs.postalCode)
                        .append(province, rhs.province).append(city, rhs.city).append(country, rhs.country).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("street", street).append("postalCode", postalCode)
                        .append("province", province).append("city", city).append("country", country).toString();
    }
}
