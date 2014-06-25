package org.anderes.edu.employee.domain;

import static org.jboss.arquillian.persistence.CleanupStrategy.DEFAULT;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.jboss.arquillian.persistence.TestExecutionPhase.NONE;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;

import javax.inject.Inject;
import javax.transaction.TransactionalException;

import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.anderes.edu.employee.persistence.EntityManagerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Cleanup(phase = NONE, strategy = DEFAULT)
public class JobTitleRepositoryIT {

	 @Inject
    private JobTitleRepository repository;

    @Deployment
    public static JavaArchive createDeployment() {
	    return ShrinkWrap
	        .create(JavaArchive.class, "test.jar")
	        // Domain-Klassen
	        .addPackage(JobTitleRepository.class.getPackage())
	        // EntityManager-Producer
	        .addClass(EntityManagerProducer.class)
	        // Logger Producer
	        .addClass(LoggerProducer.class)
	        // Resourcen
	        .addAsManifestResource(new File("target/test-classes/META-INF/derby-persistence.xml"), "persistence.xml")
	        .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    @Transactional(value = TransactionMode.DISABLED) 
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = "prepare-jobtitle.json")
    public void shouldBeFindOne() {
    	
        final JobTitle jobTitle = repository.findOne(4L);
        
        assertThat(jobTitle, is(notNullValue()));
        assertThat(jobTitle.getTitle(), is("Developer"));
    }
    
    @Test
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = "prepare-jobtitle.json")
    @ShouldMatchDataSet( value = "expected-jobtitle-afterDelete.json")
    public void shouldBeDelete() {
    	
    	// given
    	final JobTitle jobTitle = repository.findOne(4L);
    	
    	// when
        repository.delete(jobTitle);
    }
    
    @Test(expected = TransactionalException.class)
    @Transactional(value = TransactionMode.DISABLED)
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = "prepare-jobtitle.json")
    public void shouldBeDeleteWithException() {
    	
    	// given
    	final JobTitle jobTitle = repository.findOne(4L);
    	
    	// when
        repository.delete(jobTitle);
    }
}
