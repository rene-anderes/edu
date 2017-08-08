package org.anderes.edu.jaxrs.client.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RecipeCollection {

    private List<RecipeShortResource> content = new ArrayList<>();
    private int numberOfElements;

    public RecipeCollection() {
        super();
    }
    
    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<RecipeShortResource> getContent() {
        return content;
    }

    public void setContent(List<RecipeShortResource> content) {
        this.content = content;
    }
}
