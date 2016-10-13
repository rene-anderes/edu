package org.anderes.edu.jpa.model;

public class AuthorInfo {
    private String firstName;
    private String lastName;

    public AuthorInfo(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
}
