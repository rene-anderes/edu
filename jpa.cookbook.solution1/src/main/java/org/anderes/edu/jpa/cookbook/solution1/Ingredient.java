
package org.anderes.edu.jpa.cookbook.solution1;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String quantity;
    private String description;
    private String comment;

    public Ingredient() {
    }

    public Ingredient(final String quantity, final String description, final String comment) {
        super();
        this.quantity = quantity;
        this.description = description;
        this.comment = comment;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(final String quantity) {
        this.quantity = quantity;
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
        return new HashCodeBuilder(17, 37).append(quantity).append(description).append(comment).toHashCode();
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
        return new EqualsBuilder().append(quantity, rhs.quantity).append(description, rhs.description).append(comment, rhs.comment).isEquals();
    }

    @Override
    public String toString() {
        return String.format("Zutat: [portion='%s'], [description='%s'], [comment='%s']", quantity, description, comment);
    }
}
