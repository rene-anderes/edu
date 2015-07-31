package org.anderes.edu.employee.application;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.EmployeeRepository;

/**
 * Mock-Producer: Kann für unterschiedliche zu mockende Klassen verwendet werden
 * 
 * @author NA247
 *
 */
@Singleton
public class DevMocks {
    
    /** Um {@code @Produce}-Felder als Alternative ({@code @Alternative}) verwenden zu können muss ein Stereotyp verwendet werden */
    @Produces @DevMock
    public EmployeeRepository mockEmployeeRepository = mock(EmployeeRepository.class);
    
    @Produces @DevMock
    public EntityManager entityManager = mock(EntityManager.class);
    
    @PostConstruct
    @SuppressWarnings("unchecked")
    public void setup() {
        /* Achtung: 
         * Mockito benutzt zur Konfiguration Thread-Local-Variablen: Daher ist die Lösung nur bedingt einsetzbar */
        
        final Employee value = EmployeeFactory.createEmployeeWithId70();
        when(mockEmployeeRepository.findOneEmployeeAddress(70L)).thenReturn(value);
        when(mockEmployeeRepository.findOne(70L)).thenReturn(value);
        when(mockEmployeeRepository.findProjectsByEmployee(70l)).thenReturn(value);
        when(mockEmployeeRepository.findOneEmployeeAddress(1007L)).thenThrow(NoResultException.class);
        List<Employee> valueList = EmployeeFactory.createRandomEmployeeCollection(9);
        when(mockEmployeeRepository.findEmployeeBySalaryFetchJobtitle(anyDouble())).thenReturn(valueList);
        valueList = EmployeeFactory.createRandomEmployeeCollection(12);
        when(mockEmployeeRepository.findEmployees()).thenReturn(valueList);
        final Employee employeeForSave = EmployeeFactory.createEmployeeForSave();
        final Employee employeeAfterSave = EmployeeFactory.createSavedEmployee();
        when(mockEmployeeRepository.save(employeeForSave)).thenReturn(employeeAfterSave);
    }
}
