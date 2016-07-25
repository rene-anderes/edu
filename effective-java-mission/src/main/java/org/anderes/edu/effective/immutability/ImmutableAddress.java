package org.anderes.edu.effective.immutability;

import org.anderes.edu.effective.domain.Address;

public class ImmutableAddress implements AddressBase {
    
    
    public ImmutableAddress(Address address) {
        
    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public String getStreet() {
        return null;
    }
}
