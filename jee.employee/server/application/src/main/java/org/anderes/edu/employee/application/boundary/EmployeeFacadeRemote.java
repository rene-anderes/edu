/*
 * Copyright (c) 2013 VRSG | Verwaltungsrechenzentrum AG, St.Gallen
 * All Rights Reserved.
 */

package org.anderes.edu.employee.application.boundary;

import java.util.List;

import javax.ejb.Remote;

import org.anderes.edu.employee.application.boundary.dto.EmployeeSalary;

/**
 * Remote-Interface für den JNDI-Lookup
 * Via RMI kann der Client diese Schnittstelle benutzen.
 */
@Remote
public interface EmployeeFacadeRemote {

    /**
     * Liefert eine Liste mit allen Mitarebitern, deren Jahresgehalt
     * grösser dem übergebenen Wert ist.
     * 
     * @param salary
     *            Jahreslohn
     * @return Mitarbeiterliste mit Jahreslohn
     */
    public List<EmployeeSalary> getSalaryList(final Double salary);
}
