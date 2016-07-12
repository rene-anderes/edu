package org.anderes.edu.jpa.manytomany;

import static java.util.Calendar.JANUARY;
import static org.anderes.edu.jpa.manytomany.Employee.Gender.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeTest {

    private EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
    
    @BeforeClass
    public static void setUpOnce() {
        // Der Name der Persistence-Unit entspricht der in der Konfigurationsdatei META-INF/persistence.xml
        entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
    }
    
    @Before
    public void setUp() throws Exception {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        entityManager.close();
    }
    
    @AfterClass
    public static void tearDownOnce() throws Exception {
        entityManagerFactory.close();
    }
    
    @Test
    public void simpleTest() {
        
        // given
        Project projectLaboro = new Project("Laboro");
        addEmployeeMonaLisa(projectLaboro);
        addEmployeeLeonardo(projectLaboro);
        
        // when
        final Collection<Employee> employees = getAllEmployees();
        final Collection<Project> projects = getAllProjects();
        
        // then
        assertThat(employees, is(not(nullValue())));
        assertThat(employees.size(), is(2));
        assertThat(projects, is(not(nullValue())));
        assertThat(projects.size(), is(1));
    }
        
    private Collection<Project> getAllProjects() {
        final TypedQuery<Project> query = entityManager.createQuery("Select p From Project p", Project.class);
        return query.getResultList();
    }

    private Collection<Employee> getAllEmployees() {
        final TypedQuery<Employee> query = entityManager.createQuery("Select e From Employee e", Employee.class);
        return query.getResultList();
    }

    private void addEmployeeMonaLisa(Project project) {
        final Employee employee = new Employee("Mona-Lisa", "DaVinci");
    	employee.setBirthday(birthday());
    	employee.setSalary(BigDecimal.valueOf(45000D));
    	employee.setGender(FEMALE);
    	employee.addProject(project);

        entityManager.getTransaction().begin();    
        entityManager.persist(employee);
        entityManager.getTransaction().commit(); 
    }
    
    private void addEmployeeLeonardo(Project project) {
        final Employee employee = new Employee("Leonardo", "DaVinci");
        employee.setBirthday(birthday());
        employee.setSalary(BigDecimal.valueOf(45000D));
        employee.setGender(MALE);
        employee.addProject(project);

        entityManager.getTransaction().begin(); 
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }
    
    private Calendar birthday() {
    	final Calendar birthday = Calendar.getInstance();
    	birthday.clear();
    	birthday.set(1973, JANUARY, 1);
    	return birthday;
    }
}
