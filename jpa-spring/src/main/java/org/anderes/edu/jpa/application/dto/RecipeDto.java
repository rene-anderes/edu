package org.anderes.edu.jpa.application.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Stellt ein Kochrezept dar.
 * 
 * @author René Anderes
 */
@XmlRootElement(name = "recipe")
public class RecipeDto {
    /** UID als eindeutige Kennung */
    private String id;
    /** Zutaten für x Personen */
    private String noOfPeople;
    /** Liste von Zutaten */
    private List<IngredientDto> ingredients;
    /** Liste von möglichen Tag bzw. Stichwörter */
    private List<String> tags;
    /** Titel des Rezept */
    private String title;
    /** Zubereitung */
    private String preparation;
    /** Vorwort */
    private String preample;
    /** Bewertung */
    private int rating;
    /** Hinzu gefügt am */
    private long addingDate;
    /** Letzte Mutation */
    private long editingDate;
    /** Bild des Rezept */
    private String image;

    public RecipeDto() {
        ingredients = new ArrayList<IngredientDto>();
        tags = new ArrayList<String>();
        title = "";
        preparation = "";
        preample = "";
        rating = 0;
        addingDate = 0L;
        editingDate = 0L;
        image = "";
        noOfPeople = "";
    }
    
    public List<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getPreample() {
        return preample;
    }

    public void setPreample(String preample) {
        this.preample = preample;
    }

    public String getId() {
        return id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(long addingDate) {
        this.addingDate = addingDate;
    }

    public long getEditingDate() {
        return editingDate;
    }

    public void setEditingDate(long editingDate) {
        this.editingDate = editingDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(String noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

}
