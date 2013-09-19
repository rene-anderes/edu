package org.anderes.edu.calc;

import java.util.Stack;

/**
 * Copyright(c) 2008 Ren� Anderes
 * created date: 31.07.2008
 */

/**
 * Abstrakte Basisklasse f�r den Taschenrechner
 *
 * @author Ren� Anderes
 */
public abstract class Calc implements CalcIfc {

    /** Stack des Taschenrechners */
    private Stack<Double> calcStack = new Stack<Double>();
    
	/** {@inheritDoc} */
    @Override
	public int incremental(int value, int step) {
		return value += step;
	}

	/** {@inheritDoc} */
	@Override
	public double multiply(double x, double y) {
		return x * y;
	}

	/** {@inheritDoc} */
    @Override
    public double popFromStack() {
        return calcStack.pop();
    }

    /** {@inheritDoc} */
    @Override
    public void pushToStack(double value) {
        calcStack.push(value);
    }
	
	
}
