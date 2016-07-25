package org.anderes.edu.effective.immutability;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.anderes.edu.effective.domain.Employee;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ImmutableEmployee implements EmployeeBase<ImmutableProject, ImmutableAddress> {

    private final Employee employee;
    
    public ImmutableEmployee(Employee employee) {
        this.employee = employee.clone();
    }

    @Override
    public String getName() {
        return employee.getName();
    }

    @Override
    public int getAge() {
        return employee.getAge();
    }

    @Override
    public ImmutableAddress getAddress() {
        return new ImmutableAddress(employee.getAddress());
    }

    @Override
    public Collection<ImmutableProject> getProjects() {
        return employee.getProjects().stream().map(p -> new ImmutableProject(p)).collect(Collectors.toCollection(HashSet<ImmutableProject>::new));
    }

    @Override
    public String[] getResponsibilities() {
        return employee.getResponsibilities();
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
        ImmutableEmployee other = (ImmutableEmployee) obj;
        return new EqualsBuilder()
                        .append(employee.getResponsibilities(), other.getResponsibilities())
                        .append(employee.getAddress(), other.getAddress())
                        .append(employee.getProjects().toArray(), other.getProjects().toArray())
                        .append(employee.getAge(), other.getAge())
                        .append(employee.getName(), other.getName())
                        .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                        .append(employee.getName())
                        .append(employee.getAge())
                        .append(employee.getResponsibilities())
                        .append(employee.getAddress())
                        .append(employee.getProjects().toArray())
                        .toHashCode();
    }

}
