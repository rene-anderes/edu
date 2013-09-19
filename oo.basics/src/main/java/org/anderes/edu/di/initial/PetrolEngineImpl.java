package org.anderes.edu.di.initial;

public class PetrolEngineImpl implements Engine {

    private int engineRotation;

    @Override
    public void setFuelConsumptionRate(int rate) {
	engineRotation = rate * 10;
    }

    @Override
    public int getEngineRotation() {
	return engineRotation;
    }
}
