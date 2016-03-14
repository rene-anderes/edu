
package org.anderes.edu.client.rest.jersey.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.text.StrBuilder;

public class Ingredient {

    private String portion;
    private String description;
    private String comment;

    public Ingredient() {
    }

    public Ingredient(final String portion, final String description, final String comment) {
        super();
        this.portion = portion;
        this.description = description;
        this.comment = comment;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(final String quantity) {
        this.portion = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(portion).append(description).append(comment).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
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
    	return new StrBuilder().append(portion).append(description).append(comment).build();
    }
}
