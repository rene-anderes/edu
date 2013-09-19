package org.anderes.edu.gui.domain;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation des UPN-Taschenrechners
 * 
 * @author René Anderes
 */
public class Calc implements CalcIfc {

    /** Stack für den UPN Rechner */
    private Deque<Double> stack = new LinkedList<Double>();

    @Override
    public void addition() {
	double d1 = stack.pop();
	double d2 = stack.pop();
	pushToStack(d2 + d1);
    }

    @Override
    public void divide() {
	double d1 = stack.pop();
	double d2 = stack.pop();
	pushToStack(d2 / d1);
    }

    @Override
    public List<Double> getStack() {
	List<Double> list = new ArrayList<Double>(stack.size());
	for (Iterator<Double> i = stack.iterator(); i.hasNext();) {
	    list.add(i.next());
	}
	return list;
    }

    @Override
    public void multiply() {
	double d1 = stack.pop();
	double d2 = stack.pop();
	pushToStack(d2 * d1);

    }

    @Override
    public void pushToStack(double value) {
	stack.push(value);
    }

    @Override
    public void removeFromStack() {
	if (!stack.isEmpty()) {
	    stack.pop();
	}
    }

    @Override
    public void subtract() {
	double d1 = stack.pop();
	double d2 = stack.pop();
	pushToStack(d2 - d1);
    }

}
