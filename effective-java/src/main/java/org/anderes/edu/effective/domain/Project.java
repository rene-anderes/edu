package org.anderes.edu.effective.domain;

import org.anderes.edu.effective.immutability.ProjectBase;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Project implements Cloneable, ProjectBase {

    private String name;

    public Project(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public Project clone() {
        Project clone = null;
        try {
            clone = (Project) super.clone();
            clone.name = name;
            return clone;
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
        Project other = (Project) obj;
        return new EqualsBuilder().append(name, other.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).toHashCode();
    }
}
