package org.anderes.edu.employee.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Repräsentiert eine Telefonnummer
 * <p>
 * Hierbei wird ein zusammengesetzer Schlüssel verwendet und dieser
 * Schlüssel wird für die ManyToOne Beziehung genutzt.
 */
@Entity
@Table(name = "PHONE")
@IdClass(PhoneNumber.ID.class)
public class PhoneNumber implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(updatable = false)
    private String type;
    @Basic
    @Column(name = "AREA_CODE")
    private String areaCode;
    @Basic
    @Column(name = "P_NUMBER")
    private String number;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP_ID")
    private Employee owner;

    public PhoneNumber() {
    }

    public PhoneNumber(String type, String areaCode, String number) {
        this();
        setType(type);
        setAreaCode(areaCode);
        setNumber(number);
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String pNumber) {
        this.number = pNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Employee getOwner() {
        return this.owner;
    }

    protected void setOwner(Employee employee) {
        this.owner = employee;
    }

    public static class ID implements Serializable {
        private static final long serialVersionUID = 1L;
        public long owner;
        public String type;

        public ID() {
        }

        public ID(int empId, String type) {
            this.owner = empId;
            this.type = type;
        }

        public boolean equals(Object other) {
            if (other instanceof ID) {
                final ID otherID = (ID) other;
                return otherID.owner == this.owner && otherID.type.equals(type);
            }
            return false;
        }

        public int hashCode() {
            return (int)this.owner + this.type.hashCode();
        }
    }
}
