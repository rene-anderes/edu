package org.anderes.edu.jpa.inheritance.mappedsuperclasses;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "JP")
public class LegalPerson extends Person {
    
    private String legalForm;
    
    LegalPerson() {
        super();
    }

    public LegalPerson(final String legalForm) {
        super();
        this.legalForm = legalForm;
    }

    public String getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(final String legalForm) {
        this.legalForm = legalForm;
    }

}
