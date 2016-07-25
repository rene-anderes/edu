package org.anderes.edu.effective.immutability;

import java.util.Collection;

import org.anderes.edu.effective.domain.Employee;

public class ImmutableEmployee implements EmployeeBase<ImmutableProject, ImmutableAddress> {

    public ImmutableEmployee(Employee employee) {
        
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public ImmutableAddress getAddress() {
        return null;
    }

    @Override
    public Collection<ImmutableProject> getProjects() {
        return null;
    }

    @Override
    public String[] getResponsibilities() {
        return null;
    }
}
