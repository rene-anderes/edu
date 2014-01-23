/*
 * Copyright (c) 2012 VRSG | Verwaltungsrechenzentrum AG, St.Gallen
 * All Rights Reserved.
 */

package org.anderes.edu.jpa.cookbook.solution1;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.text.StrBuilder;

@Entity
@NamedQueries({
		@NamedQuery(name = "Recipe.All", query = "Select r from Recipe r"),
		@NamedQuery(name = "Recipe.ByTitle", query = "Select r from Recipe r where r.title like :title")
		})
public class Recipe {

	public final static String RECIPE_QUERY_ALL = "Recipe.All";
	public final static String RECIPE_QUERY_BYTITLE = "Recipe.ByTitle";

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private Timestamp lastUpdate;

	@Column(nullable = false, length = 200)
	private String title;

	@Embedded
	@Column(nullable = true)
	private Image image;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "RECIPE_ID")
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();

	@Column(nullable = false)
	private String preparation;

	@Column(nullable = true)
	private String preample;

	@Column(nullable = false, length = 2)
	private Integer noOfPerson;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return new StrBuilder().append(title).append(preample).append(image).append(noOfPerson)
				.appendAll(ingredients).append(preparation).toString();
	}

	public void addIngredient(final Ingredient ingredient) {
		ingredients.add(ingredient);
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

	public Integer getNoOfPerson() {
		return noOfPerson;
	}

	public void setNoOfPerson(final Integer noOfPerson) {
		this.noOfPerson = noOfPerson;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(title).append(image)
				.append(preample).append(preparation).append(noOfPerson)
				.append(ingredients.toArray()).toHashCode();
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
