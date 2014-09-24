package org.anderes.edu.employee.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
    
}
