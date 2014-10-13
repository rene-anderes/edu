package org.anderes.edu.employee.domain;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Repräsentiert einen Parkplatz
 * <b>
 * Beispiel für bidirektionale One-To-One Beziehung
 */
@Entity
public class ParkingSpace {

    @Id
    @Column(name = "PS_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer parkNo;
    private String gpsData;
    private String description;
    @OneToOne(mappedBy = "parkingSpace")
    private Employee owner;

    public void setOwner(final Employee owner) {
        this.owner = owner;
    }
    
    public Optional<Employee> getOwner() {
        return Optional.ofNullable(owner); 
    }
    
    public Integer getParkNo() {
        return parkNo;
    }

    public void setParkNo(Integer parkNo) {
        this.parkNo = parkNo;
    }

    public String getGpsData() {
        return gpsData;
    }

    public void setGpsData(String gpsData) {
        this.gpsData = gpsData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(parkNo).append(gpsData).append(description).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        final ParkingSpace rhs = (ParkingSpace) obj;
        return new EqualsBuilder().append(parkNo, rhs.parkNo).append(gpsData, rhs.gpsData).append(description, rhs.description).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("parkNo", parkNo).append("gpsData", gpsData).append("description", description).toString();
    }

}
