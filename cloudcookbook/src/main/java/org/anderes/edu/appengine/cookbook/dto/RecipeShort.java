package org.anderes.edu.appengine.cookbook.dto;

import java.util.Date;

public class RecipeShort {
    private String title;
    private String id;
    private Date editingDate;
    
    public RecipeShort() {
        super();
    }
    
    public RecipeShort(String title, String id, Date editingDate) {
        super();
        this.title = title;
        this.id = id;
        this.editingDate = editingDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getEditingDate() {
        return editingDate;
    }

    public void setEditingDate(Date editingDate) {
        this.editingDate = editingDate;
    }
    
    
}
