package org.anderes.edu.employee.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Repräsentiert eine zeitliche Periode
 * <p>
 * Die mit Java 8 eingeführte Time-API wird von JPA 2.1 nicht unterstützt.
 */
@Embeddable
public class EmploymentPeriod implements Serializable {

	private static final long serialVersionUID = 1L;
	@Temporal(TemporalType.TIMESTAMP)
    private Calendar startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endDate;

    public LocalDate getStartDate() {
        if (startDate == null) {
            return LocalDate.MIN;
        }
        return LocalDate.from(startDate.toInstant().atZone(ZoneId.systemDefault()));
    }

    public void setStartDate(LocalDate startDate) {
        final Date date = Date.from(startDate.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant());
        this.startDate = Calendar.getInstance();
        this.startDate.setTime(date);
    }

    public void setStartDate(int year, int month, int day) {
        setStartDate(LocalDate.of(year, month, day));
    }

    public LocalDate getEndDate() {
        if (endDate == null) {
            return LocalDate.MAX;
        }
        return LocalDate.from(endDate.toInstant().atZone(ZoneId.systemDefault()));
    }

    public void setEndDate(LocalDate endDate) {
        final Date date = Date.from(endDate.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant());
        this.endDate = Calendar.getInstance();
        this.endDate.setTime(date);
    }

    public void setEndDate(int year, int month, int day) {
        setEndDate(LocalDate.of(year, month, day));
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(startDate).append(endDate).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        final EmploymentPeriod rhs = (EmploymentPeriod) obj;
        return new EqualsBuilder().append(startDate, rhs.startDate).append(endDate, rhs.endDate).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("startDate", startDate).append("endDate", endDate).toString();
    }
}
