package org.anderes.edu.appengine.sample.dto;


public class MessageDto {

    private String message;

    public MessageDto() {
        super();
    }
    
    public MessageDto(String message) {
        this.message = message;
    }
 
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
