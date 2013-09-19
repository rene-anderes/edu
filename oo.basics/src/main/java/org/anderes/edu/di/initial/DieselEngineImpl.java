package org.anderes.edu.di.initial;

public class DieselEngineImpl implements Engine {

    private int engineRotation;

    @Override
    public void setFuelConsumptionRate(int rate) {
	engineRotation = rate * 9;
    }

    @Override
    public int getEngineRotation() {
	return engineRotation;
    }
}
