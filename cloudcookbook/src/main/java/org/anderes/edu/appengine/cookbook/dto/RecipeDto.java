package org.anderes.edu.appengine.cookbook.dto;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RecipeDto {

    private String id;
	private String title;
	private ImageDto image;
	private Set<IngredientDto> ingredients = new HashSet<IngredientDto>();
	private String preparation;
	private String preamble;
	private String noOfPeople;
	private Set<String> tags = new HashSet<String>();
	private Date editingDate;
	private Date addingDate;
	private Integer rating;

	public Date getEditingDate() {
        return editingDate;
    }

	public Date getAddingDate() {
        return addingDate;
    }

	public RecipeDto setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
        return this;
    }

	public Integer getRating() {
        return rating;
    }

	public RecipeDto setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public RecipeDto setEditingDate(Date editingDate) {
        this.editingDate = editingDate;
        return this;
    }

    public ImageDto getImage() {
		return image;
	}

	public RecipeDto setImage(ImageDto image) {
		this.image = image;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public RecipeDto setTitle(String title) {
		this.title = title;
		return this;
	}

	public RecipeDto addIngredient(final IngredientDto ingredient) {
		ingredients.add(ingredient);
		return this;
	}
	
	public void removeIngredient(final IngredientDto ingredient) {
		ingredients.remove(ingredient);
	}

	public Set<IngredientDto> getIngredients() {
		return Collections.unmodifiableSet(ingredients);
	}

	public String getPreparation() {
		return preparation;
	}

	public RecipeDto setPreparation(final String preparation) {
		this.preparation = preparation;
		return this;
	}

	public String getPreamble() {
		return preamble;
	}

	public RecipeDto setPreamble(final String preamble) {
		this.preamble = preamble;
		return this;
	}

	public String getNoOfPeople() {
		return noOfPeople;
	}

	public RecipeDto setNoOfPeople(final String noOfPeople) {
		this.noOfPeople = noOfPeople;
		return this;
	}

	public Set<String> getTags() {
		return Collections.unmodifiableSet(tags);
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public RecipeDto addTag(final String tag) {
		this.tags.add(tag);
		return this;
	}
	
	public void removeTag(final String tag) {
		this.tags.remove(tag);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(title).append(image)
				.append(preamble).append(preparation).append(noOfPeople)
				.append(ingredients.toArray()).append(tags.toArray()).toHashCode();
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
		RecipeDto rhs = (RecipeDto) obj;
		return new EqualsBuilder().append(title, rhs.title)
				.append(preamble, rhs.preamble)
				.append(noOfPeople, rhs.noOfPeople).append(image, rhs.image)
				.append(ingredients.toArray(), rhs.ingredients.toArray())
				.append(preparation, rhs.preparation).isEquals();
	}
	
	@Override
	public String toString() {
	    return new ToStringBuilder(this).append("title", title).append("preamble", preamble).append("image", image).append("noOfPerson", noOfPeople)
	            .append("ingredients", ingredients).append("preparation", preparation).append("tags", tags).build();
	}

    public void setId(String id) {
        this.id = id;
    }
}
