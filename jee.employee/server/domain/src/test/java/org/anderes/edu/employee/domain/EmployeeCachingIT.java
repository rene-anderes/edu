package org.anderes.edu.employee.domain;

import static org.jboss.arquillian.persistence.CleanupStrategy.DEFAULT;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.jboss.arquillian.persistence.TestExecutionPhase.NONE;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.anderes.edu.employee.persistence.EntityManagerProducer;
import org.apache.commons.lang3.time.StopWatch;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Cleanup(phase = NONE, strategy = DEFAULT)
public class EmployeeCachingIT {

    @Inject
    private EmployeeRepository repository;
    @Inject
    private Logger logger;
    
    final Random random = new Random();
    final StopWatch timer = new StopWatch();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
            .create(JavaArchive.class, "test.jar")
            // Domain-Klassen
            .addPackage(Employee.class.getPackage())
            // EntityManager-Producer
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
        logger.info("Test-Daten in die Datenbank abfuellen");
    }

    /**
     * Das Employee-Objekt mit Datenbankidentität ist, 
     * wenn der Shared-Cache ingeschaltet ist, nach der
     * Abfrage aus der Datenbank im Cache gespeichert
     */
    @Test
    @InSequence(2)
    public void ShouldBeStoreInCache() {
        
        logger.info("----------------------- zwischen hier ....");
        repository.findOne(70l);
        logger.info("/---------------------- und hier gibt es eine SQL Abfrage.");
        
    }

    /**
     * Wird mittels z.B. {@code <shared-cache-mode>ALL</shared-cache-mode>} der
     * Shared Cache eingeschaltet, so darf keine Abfrage an die Datenbank erfolgen.
     */
    @Test
    @InSequence(3)
    public void ShouldBeFromSharedCache() {
        
        logger.info("----------------------- zwischen hier ....");
        repository.findOne(70l);
        logger.info("/---------------------- und hier gibt es keine SQL Abfrage, wenn der Shared Cache aktiviert ist.");
        
    }
    
    /**
     * Demonstriert das Verhalten des Query-Cache.<br>
     * Bei eingeschalteten Query-Result-Cache werden nur 12 Abfragen an die Database gemacht.
     */
    @Test
    @InSequence(4)
    public void ShouldBe12SQLStatement() {
        final Long[] ids = { 50L,56L,57L,59L,65L,85L,86L,88L,97L,98L,70L,96L };
        final int interations = 50;
        
        logger.info("----------------------- zwischen hier ....");
        timer.start();
        for (int i = 0 ; i < interations; i++) {
            repository.findOneQueryCache(ids[random.nextInt(ids.length)]);
        }
        timer.stop();
        logger.info(String.format("----------------------- Zeit fuer %s Abfragen: %s ms", interations, timer.getTime()));
        logger.info("/---------------------- und hier gibt es 12 SQL Abfragen an die Datanebank (für 50 Anfragen), wenn der Query-Cache aktiviert ist.");
    }
}
