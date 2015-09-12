package org.anderes.edu.appengine.cookbook;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonAutoDetect;


@JsonAutoDetect
public class RecipeDto {

    private String message;

    public RecipeDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
