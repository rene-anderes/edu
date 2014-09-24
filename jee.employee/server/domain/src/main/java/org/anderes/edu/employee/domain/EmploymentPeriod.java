package org.anderes.edu.employee.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(year, month + 1, day);
        setStartDate(date);
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(year, month + 1, day);
        setEndDate(date);
    }
}
