package ch.vrsg.edu.webservice.application;

public class EmployeeNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String firstname;
    private final String lastname;
    
    public EmployeeNotFoundException(String firstname, String lastname) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
    
}
