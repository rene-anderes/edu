package org.anderes.edu.di.initial;

public class CarImpl implements Car {
    
    private Engine engine = new PetrolEngineImpl();

    @Override
    public void setPedalPressure(int value) {
	engine.setFuelConsumptionRate(value);
	
    }

    @Override
    public int getSpeed() {
	return engine.getEngineRotation();
    }

}
