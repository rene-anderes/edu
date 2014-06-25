package org.anderes.edu.employee.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.jboss.arquillian.persistence.CleanupStrategy.DEFAULT;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.jboss.arquillian.persistence.TestExecutionPhase.NONE;
import static org.junit.Assert.assertThat;

import java.io.File;

import javax.ejb.EJB;

import org.anderes.edu.employee.domain.SmallProject;
import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.anderes.edu.employee.persistence.EntityManagerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
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
public class SmallProjectFacadeIT {
    @EJB
    private SmallProjectFacade facade;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
            .create(JavaArchive.class, "test.jar")
            // Application-Layer
            .addClass(SmallProjectFacade.class)
            // Domain-Layer-Klassen
            .addPackage(SmallProject.class.getPackage())
            // EntityManager Producer vor Small- and LargeProjectRepository
            .addClass(EntityManagerProducer.class)
            // Logger Producer
            .addClass(LoggerProducer.class)
            // Resourcen
            .addAsManifestResource(new File("target/test-classes/META-INF/derby-persistence.xml"), "persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    @InSequence(1)
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = { 
        "prepare-address.json", "prepare-employee.json", "prepare-degree.json",
        "prepare-email.json", "prepare-jobtitle.json", "prepare-emp_job.json",
        "prepare-project.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-response.json", "prepare-salary.json"
        })
    public void fillDatabaseForSequence() {
        /* Test-Daten in die Datenbank abfüllen */
    }
    
    @Test
    @InSequence(2)
    @Transactional(value = TransactionMode.DISABLED) 
    public void shouldBeFindOneSmallProject() throws Exception {

        final SmallProject project = facade.findOne(53L);
        assertThat(project, is(notNullValue()));
        assertThat(project.getName(), is("Accounting Query Tool"));
    }
    
    @Test
    @InSequence(3)
    @Transactional(value = TransactionMode.DISABLED)
    @ShouldMatchDataSet(value = {
        // Die veränderten Tabellen
        "expected-project-afterInsert-smallProject.json",
        // Die unveränderten Tabellen
        "prepare-address.json", "prepare-degree.json", "prepare-email.json", "prepare-emp_job.json",
        "prepare-employee.json", "prepare-jobtitle.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-response.json", "prepare-salary.json" },
    excludeColumns = { "PROJECT.PROJ_ID" })
    public void shouldBeSaveNewSmallProject() throws Exception {

        final SmallProject project = facade.save(createSmallProject());
        assertThat(project, is(notNullValue()));
        assertThat(project.getId(), is(notNullValue()));
        assertThat(project.getId() > 100, is(true));
        assertThat(project.getName(), is("Laboro"));
    }

    @Test
    @InSequence(4)
    @Transactional(value = TransactionMode.DISABLED)
    @ShouldMatchDataSet(value = { 
        "prepare-address.json", "prepare-degree.json", "prepare-email.json", "prepare-emp_job.json",
        "prepare-employee.json", "prepare-jobtitle.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-project.json", "prepare-response.json", "prepare-salary.json" 
        })
    public void shouldBeDeleteSmallProject() throws Exception {
        facade.delete("Laboro");
    }
    
    private SmallProject createSmallProject() {
        final SmallProject project = new SmallProject("Laboro", "Ein Produkt für die Auftragsabwicklung");
        return project;
    }
}

