package org.anderes.edu.jpa.application.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Datencontainer für eine Rezepte (Kurzform)
 * 
 * @author René Anderes
 */
@XmlRootElement(name = "recipeShort")
public class RecipeShortDto {
    private String title;
    private String id;
    private long editingDate;
    
    public RecipeShortDto() {
        title = "";
        id = "";
        editingDate = 0L;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getId() {
        return id;
    }
    
    public long getEditingDate() {
        return editingDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEditingDate(long editingDate) {
        this.editingDate = editingDate;
    }
}
