package org.anderes.edu.employee.application;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.anderes.edu.employee.domain.SmallProject;
import org.anderes.edu.employee.domain.SmallProjectRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SmallProjectFacade {
    
    @Inject
    private SmallProjectRepository repository;
    
    /**
     * Beispiel bei dem die Tranakstion notwendig wird und die Methode
     * entsprechend annotiert ist.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(final String project) {
        final List<SmallProject> smallProjects = repository.find(project);
        for (SmallProject smallProject : smallProjects) {
            repository.delete(smallProject);
        }
    }

    public Optional<SmallProject> findOne(final Long id) {
        return Optional.ofNullable(repository.findOne(id));
    }

    /**
     * Beispiel bei dem die Tranakstion notwendig wird und die Methode
     * entsprechend annotiert ist.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SmallProject save(final SmallProject smallProject) {
        return repository.save(smallProject);
    }

}
