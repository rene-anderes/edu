package org.anderes.edu.effective.immutability;

import org.anderes.edu.effective.domain.Project;

public class ImmutableProject implements ProjectBase {

    public ImmutableProject(Project project) {
    }

    @Override
    public String getName() {
        return null;
    }

}
