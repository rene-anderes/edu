package org.anderes.edu.jpa.relation.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Task {

    @Id @GeneratedValue
    private Long id;
    private Integer taskNo;
    private String description;
    
    public Integer getTaskNo() {
        return taskNo;
    }
    
    public void setTaskNo(Integer taskNo) {
        this.taskNo = taskNo;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((taskNo == null) ? 0 : taskNo.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (taskNo == null) {
            if (other.taskNo != null)
                return false;
        } else if (!taskNo.equals(other.taskNo))
            return false;
        return true;
    }
    
}
