package org.anderes.edu.jpa.application.dto;


/**
 * Stellt eine Zutat für ein Kochrezept dar.
 * 
 * @author René Anderes
 */
public class IngredientDto {
	
	/** Beschreibung der Zutat */
	private String description;
	/** Mengenangabe der Zutat */
	private String portion;
	/** Bemerkungen */
	private String comment;
	
	public IngredientDto() {
		description = "";
		portion = "";
		comment = "";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPortion() {
		return portion;
	}

	public void setPortion(String portion) {
		this.portion = portion;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
