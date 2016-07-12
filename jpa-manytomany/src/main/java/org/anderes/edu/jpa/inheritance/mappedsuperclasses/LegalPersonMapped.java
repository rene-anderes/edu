package org.anderes.edu.jpa.inheritance.mappedsuperclasses;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "JP_MAPPED")
public class LegalPersonMapped extends PersonMapped {
    
    private String legalForm;
    
    LegalPersonMapped() {
        super();
    }

    public LegalPersonMapped(final String legalForm) {
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
