/*******************************************************************************
 * Copyright (c) 1998, 2012 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *              dclarke - initial JPA Employee example using XML (bug 217884)
 *              mbraeuer - annotated version
 ******************************************************************************/
package org.anderes.edu.employee.domain;

import static java.lang.Boolean.TRUE;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

/**
 * This class represent a project that a group of employees work on.
 * This demonstrates JOINED inheritance.
 * </p>
 * Die Klasse demonstriert die Möglichkeit eines Konverterts (JPA 2.1)
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PROJ_TYPE")
public abstract class Project implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PROJ_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Basic
    @Column(name = "PROJ_NAME")
    private String name;
    @Basic
    @Column(name = "DESCRIP")
    private String description;
    @Version
    private long version;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEADER_ID")
    private Employee teamLeader;
    // JPA 2.1 Converter
    @Convert(converter = BooleanActiveConverter.class)
    @Column(name = "STATUS")
    private Boolean isActive = TRUE;

    public Project() {
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String descrip) {
        this.description = descrip;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String projName) {
        this.name = projName;
    }

    public long getVersion() {
        return version;
    }

    public Employee getTeamLeader() {
        return this.teamLeader;
    }

    public void setTeamLeader(Employee employee) {
        this.teamLeader = employee;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }
}
