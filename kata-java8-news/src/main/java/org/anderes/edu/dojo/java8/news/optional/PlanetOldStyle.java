package org.anderes.edu.dojo.java8.news.optional;

public class PlanetOldStyle {

    private String name;
    private Integer moons;
    private Double mass;
    private Double diameter;

    public PlanetOldStyle(String name, Double diameter, Double mass, Integer moons) {
        if (name == null || diameter == null || mass == null) {
            throw new IllegalArgumentException("Parameter dürfen nicht null sein.");
        }
        this.name = name;
        this.diameter = diameter;
        this.mass = mass;
        this.moons = moons;
    }

    public String getName() {
        return name;
    }

    public Integer getMoons() {
        return moons;
    }

    public Double getMass() {
        return mass;
    }

    public Double getDiameter() {
        return diameter;
    }

}
