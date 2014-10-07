package org.anderes.edu.jpa.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String uuid;

	@Version
	private Integer version;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastUpdate = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
    private Date addingDate = new Date();

	@Column(nullable = false, length = 200)
	private String title;

	@Embedded
	@Column(nullable = true)
	private Image image;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "RECIPE_ID")
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();

	@Column(nullable = false, length = 8000)
	private String preparation;

	@Column(nullable = true, length = 8000)
	private String preample;

    @Column(nullable = false, length = 5)
	private String noOfPerson;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="TAGS", joinColumns=@JoinColumn(name="RECIPE_ID"))
	private Set<String> tags = new HashSet<String>();
	
	@Column(nullable = false)
	private Integer rating = Integer.valueOf(0);

	/*package*/ public Recipe() {
	    super();
    }
	
	public Recipe(final String uuid) {
        this();
        this.uuid = uuid;
    }

    public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addIngredient(final Ingredient ingredient) {
		ingredients.add(ingredient);
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
		return Collections.unmodifiableSet(tags);
	}

	public void addTag(final String tag) {
		this.tags.add(tag);
	}
	
	public void removeTag(final String tag) {
		this.tags.remove(tag);
	}

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("uuid", uuid).append("title", title).append("preample", preample)
            .append("image", image).append("noOfPerson", noOfPerson).append("ingredients", ingredients)
            .append("preparation", preparation).append("tags", tags).append("lastUpdate", lastUpdate)
            .append("addingUpdate", addingDate).append("rating", rating).toString();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(uuid).append(title).append(image)
				.append(preample).append(preparation).append(noOfPerson)
				.append(lastUpdate).append(addingDate).append(rating)
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
		return new EqualsBuilder().append(uuid, rhs.uuid).append(title, rhs.title)
				.append(preample, rhs.preample).append(lastUpdate, rhs.lastUpdate)
				.append(addingDate, rhs.addingDate).append(rating, rhs.rating)
				.append(noOfPerson, rhs.noOfPerson).append(image, rhs.image)
				.append(ingredients.toArray(), rhs.ingredients.toArray())
				.append(tags.toArray(), rhs.tags.toArray())
				.append(preparation, rhs.preparation).isEquals();
	}
}
