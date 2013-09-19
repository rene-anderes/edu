package org.anderes.edu.gui.mvc.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.anderes.edu.gui.domain.CalcObservable;

/**
 * Alternative Anzeige für den Stack
 * 
 * @author René Anderes
 */
@SuppressWarnings("serial")
public class ViewAlternative extends JFrame implements Observer {

    private JTextField textField;
    private CalcObservable calc;

    /**
     * Konstruktor
     */
    public ViewAlternative() {
	setTitle("Last Stack-Value");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(300, 100);

	JPanel panel = new JPanel(new GridBagLayout());
	textField = new JTextField(20);
	textField.setBackground(Color.WHITE);
	textField.setEditable(false);

	GridBagConstraints c = new GridBagConstraints();
	c.gridwidth = GridBagConstraints.REMAINDER;
	c.fill = GridBagConstraints.HORIZONTAL;
	panel.add(textField, c);

	add(panel);
    }

    /**
     * Initialisierung der View
     * 
     * @param calc
     *            Model
     */
    public void initialize(final CalcObservable calc) {
	calc.addObserver(this);
	this.calc = calc;
	setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
	if (calc.getStack().size() > 0) {
	    textField.setText(String.format("%1$s", calc.getStack().get(0)));
	} else {
	    textField.setText("");
	}
    }
}
