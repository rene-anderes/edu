package org.anderes.edu.employee.application;

import static org.mockito.Mockito.*;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.NoResultException;

import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.EmployeeRepository;
import org.mockito.Mockito;

@Singleton
public class DevMocks {
    @Produces @DevMock
    private EmployeeRepository mockEmployeeRepository = Mockito.mock(EmployeeRepository.class);

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void setup() {
        Employee value = EmployeeFactory.createEmployeeWithId70();
        when(mockEmployeeRepository.findOneEmployeeAddress(70L)).thenReturn(value);
        when(mockEmployeeRepository.findOne(70L)).thenReturn(value);
        when(mockEmployeeRepository.findProjectsByEmployee(70l)).thenReturn(value);
        when(mockEmployeeRepository.findOneEmployeeAddress(1007L)).thenThrow(NoResultException.class);
        List<Employee> valueList = EmployeeFactory.createRandomEmployeeCollection(9);
        when(mockEmployeeRepository.findEmployeeBySalaryFetchJobtitle(anyDouble())).thenReturn(valueList);
        valueList = EmployeeFactory.createRandomEmployeeCollection(12);
        when(mockEmployeeRepository.findEmployees()).thenReturn(valueList);
    }
}
