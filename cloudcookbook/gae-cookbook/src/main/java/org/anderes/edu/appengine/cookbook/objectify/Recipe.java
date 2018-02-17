package org.anderes.edu.appengine.cookbook.objectify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Recipe {

    private @Id String id;
	private @Index String title;
	private Ref<Image> image;
	private List<Ref<Ingredient>> ingredients = new ArrayList<>();
	private String preparation;
	private String preamble;
	private String noOfPeople;
	private Set<String> tags = new TreeSet<String>();
	private Date editingDate;
	private Date addingDate;
	private Integer rating;

	public Recipe() {
		super();
		this.id = UUID.randomUUID().toString();
	}

    public Recipe setId(String id) {
        this.id = id;
        return this;
    }
    
	public Date getEditingDate() {
        return editingDate;
    }

	public Date getAddingDate() {
        return addingDate;
    }

	public Recipe setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
        return this;
    }

	public Integer getRating() {
        return rating;
    }

	public Recipe setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Recipe setEditingDate(Date editingDate) {
        this.editingDate = editingDate;
        return this;
    }

    public Optional<Image> getImage() {
    	if (image == null) {
    		return Optional.empty();
    	}
		return Optional.of(image.get());
	}

	public Recipe setImage(Image image) {
		this.image = Ref.create(image);
		return this;
	}

	public String getId() {
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
		ingredients.add(Ref.create(ingredient));
		return this;
	}
	
	public void removeIngredient(final Ingredient ingredient) {
		ingredients.remove(Ref.create(ingredient));
	}

	public Set<Ingredient> getIngredients() {
		return ingredients.stream()
				.map(ref -> ref.get())
				.filter(i -> i != null)
				.collect(Collectors.toCollection(TreeSet::new));
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

	public String getNoOfPeople() {
		return noOfPeople;
	}

	public Recipe setNoOfPeople(final String noOfPeople) {
		this.noOfPeople = noOfPeople;
		return this;
	}

	public Set<String> getTags() {
		return Collections.unmodifiableSet(tags);
	}

	public void setTags(final Set<String> tags) {
		this.tags = tags;
	}

	public Recipe addTag(final String tag) {
		this.tags.add(tag);
		return this;
	}
	
	public Recipe removeTag(final String tag) {
		this.tags.remove(tag);
		return this;
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
		Recipe rhs = (Recipe) obj;
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

}
