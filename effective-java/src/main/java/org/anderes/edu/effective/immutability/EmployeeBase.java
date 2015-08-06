package org.anderes.edu.effective.immutability;

import java.util.Collection;


public interface EmployeeBase<P extends ProjectBase, A extends AddressBase> {

    String getName();
    int getAge();
    A getAddress();
    Collection<P> getProjects();
    String[] getResponibilities();
}
