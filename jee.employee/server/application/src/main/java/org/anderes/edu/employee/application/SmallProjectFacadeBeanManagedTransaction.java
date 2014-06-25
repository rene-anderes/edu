package org.anderes.edu.employee.application;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.anderes.edu.employee.domain.SmallProject;
import org.anderes.edu.employee.domain.SmallProjectRepositoryBeanManagedTransaction;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class SmallProjectFacadeBeanManagedTransaction {
    
	@Inject
    private SmallProjectRepositoryBeanManagedTransaction repository;

    public SmallProject save(final SmallProject project) throws Exception {
        return repository.save(project);
    }
    
    public SmallProject findOne(final Long id) throws Exception {
        return repository.findOne(id);
    }

    public void delete(final String projectName) throws Exception {
        repository.delete(projectName);
    }

}
