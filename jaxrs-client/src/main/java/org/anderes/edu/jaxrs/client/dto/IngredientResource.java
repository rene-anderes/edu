
package org.anderes.edu.jaxrs.client.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IngredientResource extends Resource {

    private String resourceId;
    private String portion;
    private String description;
    private String comment;

    public IngredientResource() {
        super();
    }
    
    public IngredientResource(String resourceId) {
        this();
        this.resourceId = resourceId;
    }

    public IngredientResource(final String portion, final String description, final String comment) {
        this.portion = portion;
        this.description = description;
        this.comment = comment;
    }
    
    public IngredientResource(final String resourceId, final String portion, final String description, final String comment) {
        this(resourceId);
        this.portion = portion;
        this.description = description;
        this.comment = comment;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(final String portion) {
        this.portion = portion;
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
        IngredientResource rhs = (IngredientResource) obj;
        return new EqualsBuilder().append(portion, rhs.portion).append(description, rhs.description).append(comment, rhs.comment).isEquals();
    }

    @Override
    public String toString() {
    	return new ToStringBuilder(this).append("quantity", portion).append("description", description).append("annotation", comment).build();
    }
}
