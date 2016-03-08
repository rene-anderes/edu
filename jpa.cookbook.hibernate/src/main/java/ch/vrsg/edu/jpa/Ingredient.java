/*
 * Copyright (c) 2012 VRSG | Verwaltungsrechenzentrum AG, St.Gallen
 * All Rights Reserved.
 */

package ch.vrsg.edu.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Ingredient {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String portion;
    private String description;
    private String comment;

    public Ingredient() {
    }

    public Ingredient(String portion, String description, String comment) {
        super();
        this.portion = portion;
        this.description = description;
        this.comment = comment;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(portion).append(description).append(comment).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Ingredient rhs = (Ingredient) obj;
        return new EqualsBuilder().append(portion, rhs.portion).append(description, rhs.description).append(comment, rhs.comment).isEquals();
    }

    @Override
    public String toString() {
        return String.format("Zutat: [portion='%s'], [description='%s'], [comment='%s']", portion, description, comment);
    }
}
