package org.anderes.logging;

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

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getIndex() {
        return index;
    }

}
