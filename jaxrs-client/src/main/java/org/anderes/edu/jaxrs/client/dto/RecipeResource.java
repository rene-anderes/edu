package org.anderes.edu.jaxrs.client.dto;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RecipeResource extends Resource {
	
	private String uuid;
	private Date lastUpdate = new Date();
    private Date addingDate = new Date();
	private String title;
	private String preparation;
	private String preamble;
	private String noOfPerson;
	private Set<String> tags = new HashSet<String>();
	private Integer rating = Integer.valueOf(0);
	private List<IngredientResource> ingredients;

    public RecipeResource() {
	    super();
    }
    
    public List<IngredientResource> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(List<IngredientResource> ingredients) {
        this.ingredients = ingredients;
    }
	
	public RecipeResource(final String uuid) {
        this();
        this.uuid = uuid;
    }

	public String getUuid() {
        return uuid;
    }
    
    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by the Date object.
     * @return time
     */
    public Long getEditingDate() {
        return this.lastUpdate.getTime();
    }
    
    /**
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by the Date object.
     * @return time
     */
    public Long getAddingDate() {
        return this.addingDate.getTime();
    }
    
    public RecipeResource setEditingDate(Long lastUpdateTime) {
        this.lastUpdate = new Date(lastUpdateTime);
        return this;
    }
    
    public RecipeResource setAddingDate(Long addingDateTime) {
        this.addingDate = new Date(addingDateTime);
        return this;
    }
    
    public String getTitle() {
		return title;
	}

	public RecipeResource setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getPreparation() {
		return preparation;
	}

	public RecipeResource setPreparation(final String preparation) {
		this.preparation = preparation;
		return this;
	}

	public String getPreamble() {
		return preamble;
	}

	public RecipeResource setPreamble(final String preamble) {
		this.preamble = preamble;
		return this;
	}

	public String getNoOfPerson() {
		return noOfPerson;
	}

	public RecipeResource setNoOfPerson(final String noOfPerson) {
		this.noOfPerson = noOfPerson;
		return this;
	}

	public Set<String> getTags() {
		return Collections.unmodifiableSet(tags);
	}

	public RecipeResource addTag(final String tag) {
		this.tags.add(tag);
		return this;
	}
	
	public RecipeResource removeTag(final String tag) {
		this.tags.remove(tag);
		return this;
	}

    public Integer getRating() {
        return rating;
    }

    public RecipeResource setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("uuid", uuid).append("title", title).append("preamble", preamble)
            .append("noOfPerson", noOfPerson).append("preparation", preparation)
            .append("tags", tags).append("lastUpdate", lastUpdate)
            .append("addingUpdate", addingDate).append("rating", rating).toString();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(uuid).append(title)
				.append(preamble).append(preparation).append(noOfPerson)
				.append(lastUpdate).append(addingDate).append(rating)
				.append(tags).toHashCode();
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
		RecipeResource rhs = (RecipeResource) obj;
		return new EqualsBuilder().append(uuid, rhs.uuid).append(title, rhs.title)
				.append(preamble, rhs.preamble).append(lastUpdate, rhs.lastUpdate)
				.append(addingDate, rhs.addingDate).append(rating, rhs.rating)
				.append(noOfPerson, rhs.noOfPerson).append(tags, rhs.tags)
				.append(preparation, rhs.preparation).isEquals();
	}

}
