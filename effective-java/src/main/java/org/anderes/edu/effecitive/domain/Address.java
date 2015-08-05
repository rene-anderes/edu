package org.anderes.edu.effecitive.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Address implements Cloneable {

    private String street;
    private String city;

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public Address clone() {
        Address clone = null;
        try {
            clone = (Address) super.clone();
            clone.city = city;
            clone.street = street;
        } catch (CloneNotSupportedException e) { }  // Won't happen
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Address other = (Address) obj;
        return new EqualsBuilder()
                        .append(street, other.street)
                        .append(city, other.city)
                        .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(street).append(city).toHashCode();
    }
}
