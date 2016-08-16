package org.anderes.edu.jpa.cookbook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entität für eine Datenbank-View
 *
 */
@Entity
@Table(name = "TAG_COUNT")
public class TagCounterView {

    @Column(name = "NUMBER_OF_APPEARANCE")
    private Integer number;
    @Id
    @Column(name = "TAGS")
    private String tag;
           
    TagCounterView() {
        super();
    }

    public Integer getNumber() {
        return number;
    }

    public String getTag() {
        return tag;
    }
    
}
