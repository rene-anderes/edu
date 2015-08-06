package org.anderes.edu.effective.immutability;

import org.anderes.edu.effective.domain.Project;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ImmutableProject implements ProjectBase {

    private final Project project;
    
    public ImmutableProject(Project project) {
        this.project = project.clone();
    }

    @Override
    public String getName() {
        return project.getName();
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
        ImmutableProject other = (ImmutableProject) obj;
        return new EqualsBuilder().append(project.getName(), other.getName()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(project.getName()).toHashCode();
    }
}
