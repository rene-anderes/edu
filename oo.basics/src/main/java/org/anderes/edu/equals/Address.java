package org.anderes.edu.equals;

public class Address {

    private String street;
    private String housenumber;
    private String sufix;

    public Address(String street, String housenumber, String sufix) {
        this.street = street;
        this.housenumber = housenumber;
        this.sufix = sufix;
    }

    public String getStreet() {
        return street;
    }
    
    public String getHousenumber() {
        return housenumber;
    }
    
    public String getSufix() {
        return sufix;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((housenumber == null) ? 0 : housenumber.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((sufix == null) ? 0 : sufix.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (housenumber == null) {
            if (other.housenumber != null)
                return false;
        } else if (!housenumber.equals(other.housenumber))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (sufix == null) {
            if (other.sufix != null)
                return false;
        } else if (!sufix.equals(other.sufix))
            return false;
        return true;
    }
}
