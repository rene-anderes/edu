package org.anderes.edu.beanvalidation;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class Development {
    
    @Past
    private LocalDate lastRelease;
    @NotNull @Future
    private LocalDate nextRelease;
    
    public LocalDate getLastRelease() {
        return lastRelease;
    }
    public void setLastRelease(LocalDate lastRelease) {
        this.lastRelease = lastRelease;
    }
    public LocalDate getNextRelease() {
        return nextRelease;
    }
    public void setNextRelease(LocalDate nextRelease) {
        this.nextRelease = nextRelease;
    }
    
    

}
