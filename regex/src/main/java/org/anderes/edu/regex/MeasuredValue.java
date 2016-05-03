package org.anderes.edu.regex;

public class MeasuredValue {

    private Integer index;
    private String description;
    private String value;
    private String unit;

    public MeasuredValue(Integer index, String description, String value, String unit) {
        this.index = index;
        this.description = description;
        this.value = value;
        this.unit = unit;
    }

    public Integer getIndex() {
        return index;
    }

}
