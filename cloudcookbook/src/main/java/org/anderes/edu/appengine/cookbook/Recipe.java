package org.anderes.edu.appengine.cookbook;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Recipe {

    private @Id Long id;
	private @Index String title;
	private Image image;
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();
	private String preparation;
	private String preamble;
	private Integer noOfPerson;
	private Set<String> tags = new HashSet<String>();
	private Date lastModified;

	public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Image getImage() {
		return image;
	}

	public Recipe setImage(Image image) {
		this.image = image;
		return this;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Recipe setTitle(String title) {
		this.title = title;
		return this;
	}

	public Recipe addIngredient(final Ingredient ingredient) {
		ingredients.add(ingredient);
		return this;
	}
	
	public void removeIngredient(final Ingredient ingredient) {
		ingredients.remove(ingredient);
	}

	public Set<Ingredient> getIngredients() {
		return Collections.unmodifiableSet(ingredients);
	}

	public String getPreparation() {
		return preparation;
	}

	public Recipe setPreparation(final String preparation) {
		this.preparation = preparation;
		return this;
	}

	public String getPreamble() {
		return preamble;
	}

	public Recipe setPreamble(final String preamble) {
		this.preamble = preamble;
		return this;
	}

	public Integer getNoOfPerson() {
		return noOfPerson;
	}

	public Recipe setNoOfPerson(final Integer noOfPerson) {
		this.noOfPerson = noOfPerson;
		return this;
	}

	public Set<String> getTags() {
		return Collections.unmodifiableSet(tags);
	}

	public Recipe addTag(final String tag) {
		this.tags.add(tag);
		return this;
	}
	
	public void removeTag(final String tag) {
		this.tags.remove(tag);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(title).append(image)
				.append(preamble).append(preparation).append(noOfPerson)
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
		Recipe rhs = (Recipe) obj;
		return new EqualsBuilder().append(title, rhs.title)
				.append(preamble, rhs.preamble)
				.append(noOfPerson, rhs.noOfPerson).append(image, rhs.image)
				.append(ingredients.toArray(), rhs.ingredients.toArray())
				.append(preparation, rhs.preparation).isEquals();
	}
	
	@Override
	public String toString() {
	    return new ToStringBuilder(this).append("title", title).append("preamble", preamble).append("image", image).append("noOfPerson", noOfPerson)
	            .append("ingredients", ingredients).append("preparation", preparation).append("tags", tags).build();
	}
}
