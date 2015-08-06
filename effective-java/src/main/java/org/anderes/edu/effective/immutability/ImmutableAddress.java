package org.anderes.edu.effective.immutability;

import org.anderes.edu.effective.domain.Address;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ImmutableAddress implements AddressBase {
    
    private final Address address;

    public ImmutableAddress(Address address) {
        this.address = address.clone();
    }

    @Override
    public String getCity() {
        return address.getCity();
    }

    @Override
    public String getStreet() {
        return address.getStreet();
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
        ImmutableAddress other = (ImmutableAddress) obj;
        return new EqualsBuilder()
                        .append(address.getStreet(), other.getStreet())
                        .append(address.getCity(), other.getCity())
                        .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(address.getStreet()).append(address.getCity()).toHashCode();
    }
}
