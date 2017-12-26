
package org.anderes.edu.appengine.cookbook.objectify;

import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Ingredient implements Comparable<Ingredient>{

	private @Id String id;
	private String portion;
	private String description;
	private String comment;

    private Ingredient() {
    	super();
    }

    public Ingredient(final String id, final String portion, final String description, final String comment) {
        this();
        this.id = id;
        this.portion = portion;
        this.description = description;
        this.comment = comment;
    }
    
    public Ingredient(final String portion, final String description, final String comment) {
		this(UUID.randomUUID().toString(), portion, description, comment);
	}

	public String getId() {
    	return id;
    }
    
    public void setId(String id) {
    	this.id = id;
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
        return new HashCodeBuilder(17, 37).append(id).append(portion).append(description).append(comment).toHashCode();
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
        return new EqualsBuilder().append(id, rhs.id).append(portion, rhs.portion).append(description, rhs.description).append(comment, rhs.comment).isEquals();
    }

    @Override
    public String toString() {
    	return new ToStringBuilder(this).append(id).append(portion).append(description).append(comment).build();
    }

	@Override
	public int compareTo(Ingredient other) {
		return this.description.compareToIgnoreCase(other.description);
	}
}
