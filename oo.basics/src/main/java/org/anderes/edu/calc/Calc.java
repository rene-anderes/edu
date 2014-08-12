package org.anderes.edu.calc;

import java.util.Stack;

/**
 * Copyright(c) 2008 René Anderes
 * created date: 31.07.2008
 */

/**
 * Abstrakte Basisklasse für den Taschenrechner
 *
 * @author René Anderes
 */
public abstract class Calc implements CalcIfc {

    /** Stack des Taschenrechners */
    private Stack<Double> calcStack = new Stack<Double>();
    
    @Override
	public int incremental(int value, int step) {
		return value += step;
	}

	@Override
	public double multiply(double x, double y) {
		return x * y;
	}

    @Override
    public double popFromStack() {
        return calcStack.pop();
    }

    @Override
    public void pushToStack(double value) {
        calcStack.push(value);
    }
	
	
}
