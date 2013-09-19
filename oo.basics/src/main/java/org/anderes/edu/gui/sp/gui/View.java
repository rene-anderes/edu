/**
 *
 */
package org.anderes.edu.gui.sp.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.anderes.edu.gui.domain.CalcIfc;

/**
 * View
 *
 * @author René Anderes
 *
 */
public class View implements ViewIfc, Observer {

    private final JFrame f;
    private final JTextField textField;
    private final JTextArea textArea;
    private EventListener listener = EventListener.NULL;
    private CalcIfc calc;

    /**
     * Konstruktor
     */
    public View(CalcIfc calc, Observable observable) {
	observable.addObserver(this);
	this.calc = calc;
	f = new JFrame("Taschenrechner");
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setSize(300, 200);

	JPanel panel = new JPanel(new GridBagLayout());
	textField = new JTextField(20);
	textField.setBackground(Color.WHITE);
	textField.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		listener.UserInput();
	    }
	});

	textArea = new JTextArea(5, 20);
	textArea.setEditable(false);
	textArea.setBackground(Color.LIGHT_GRAY);

	GridBagConstraints c = new GridBagConstraints();
	c.gridwidth = GridBagConstraints.REMAINDER;
	c.fill = GridBagConstraints.HORIZONTAL;
	panel.add(textField, c);

	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1.0;
	c.weighty = 1.0;
	panel.add(textArea, c);

	f.add(panel);
	f.setVisible(true);
    }

    @Override
    public void addEventListener(EventListener listener) {
	this.listener = listener;
    }

    @Override
    public String getTextFromInputField() {
	return textField.getText();
    }

    @Override
    public void setStack(List<Double> values) {
	textArea.setText("");
	for (Double d : values) {
	    textArea.append(String.format("%1$f", d));
	    textArea.append("\n");
	}
    }

    @Override
    public void setTextForInputField(String text) {
	textField.setText(text);
    }

    @Override
    public void clearInputField() {
	textField.setText("");
    }

    @Override
    public void unknownCommand() {
	textField.setText("Ungültiger Wert");
	textField.selectAll();
    }

    @Override
    public void update(Observable o, Object arg) {
	setStack(calc.getStack());
    }
}
