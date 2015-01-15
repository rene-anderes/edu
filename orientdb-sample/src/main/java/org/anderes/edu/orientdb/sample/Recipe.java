package org.anderes.edu.orientdb.sample;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.orientechnologies.orient.core.annotation.OVersion;

public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String title;
	@OneToOne(cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Image image;
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();
	private String preparation;
	private String preample;
	private String noOfPerson;
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<String> tags = new HashSet<String>();
	@OVersion
	private Long version;

	public Recipe() {
	    super();
	}
	
	public Recipe(final String uuid) {
	    this();
	    Validate.notNull(uuid);
	    this.id = uuid;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("title", getTitle()).append("preample", getPreample())
		                .append("image", getImage()).append("noOfPerson", getNoOfPerson())
		                .append("ingredients", getIngredients().toArray()).append("preparation", getPreparation())
		                .append("tags", getTags().toArray()).toString();
	}
	
	public void removeIngredient(final Ingredient ingredient) {
		ingredients.remove(ingredient);
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(final String preparation) {
		this.preparation = preparation;
	}

	public String getPreample() {
		return preample;
	}

	public void setPreample(final String preample) {
		this.preample = preample;
	}

	public String getNoOfPerson() {
		return noOfPerson;
	}

	public void setNoOfPerson(final String noOfPerson) {
		this.noOfPerson = noOfPerson;
	}

	public Set<String> getTags() {
		return tags;
	}

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
	
	public void removeTag(final String tag) {
		this.tags.remove(tag);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(title).append(image)
				.append(preample).append(preparation).append(noOfPerson)
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
				.append(preample, rhs.preample)
				.append(noOfPerson, rhs.noOfPerson).append(image, rhs.image)
				.append(ingredients.toArray(), rhs.ingredients.toArray())
				.append(preparation, rhs.preparation).isEquals();
	}
}
