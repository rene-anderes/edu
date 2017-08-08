package org.anderes.edu.jaxrs.client.dto;

public class RecipeShortResource extends Resource {

    private String uuid;
    private String title;
    
    public RecipeShortResource() {
        super();
    }
    
    public RecipeShortResource(String uuid, String title) {
        super();
        this.uuid = uuid;
        this.title = title;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public String getTitle() {
        return title;
    }
}
