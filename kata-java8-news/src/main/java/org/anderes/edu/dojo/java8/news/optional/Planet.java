package org.anderes.edu.dojo.java8.news.optional;

import java.util.Optional;

public class Planet {

    private String name;
    private Integer moons;
    private Double mass;
    private Double diameter;

    public Planet(String name, Double diameter, Double mass, Optional<Integer> moons) {
        if (name == null || diameter == null || mass == null || moons == null) {
            throw new IllegalArgumentException("Parameter name, diameter oder mass dürfen nicht null sein.");
        }
        this.name = name;
        this.diameter = diameter;
        this.mass = mass;
        this.moons = moons.orElse(null);
    }

    public String getName() {
        return name;
    }

    public Optional<Integer> getMoons() {
        return Optional.ofNullable(moons);
    }

    public Double getMass() {
        return mass;
    }

    public Double getDiameter() {
        return diameter;
    }
}
