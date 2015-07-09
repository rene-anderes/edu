package org.anderes.edu.employee.application;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.anderes.edu.employee.domain.LargeProject;
import org.anderes.edu.employee.domain.LargeProjectRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LargeProjectFacade {
    
    @Inject
    private LargeProjectRepository repository;
    
    public Optional<LargeProject> findOne(final Long id) {
        return Optional.ofNullable(repository.findOne(id));
    }
}
