package org.anderes.edu.jpa.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "JP")
@DiscriminatorValue(value = "JP")
public class LegalPerson extends Person {

    @Column(name = "LEGALFORM")
    private String legalForm;
 
    public String getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }
}
