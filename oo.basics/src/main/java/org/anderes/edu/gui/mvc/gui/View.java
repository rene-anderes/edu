package org.anderes.edu.gui.mvc.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.anderes.edu.gui.domain.CalcObservable;

/**
 * View des MVC-Pattern für den Taschenrechner
 * 
 * @author René Anderes
 */
public class View implements Observer {

    private final JFrame f;
    private final JTextField textField;
    private final JTextArea textArea;

    private CalcObservable calc;
    private Controller controller;

    /**
     * Konstruktor
     */
    public View() {
	f = new JFrame("Taschenrechner");
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setSize(300, 200);

	JPanel panel = new JPanel(new GridBagLayout());
	textField = new JTextField(20);
	textField.setBackground(Color.WHITE);

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

    }

    /**
     * Initialisierung der View
     * 
     * @param calc
     *            Model
     */
    public void initialize(final CalcObservable calc) {
	calc.addObserver(this);
	controller = new Controller(calc, this);
	textField.addActionListener(controller);
	this.calc = calc;
	f.setVisible(true);
    }

    /**
     * Löscht das Eingabefeld für die nächste Eingabe
     */
    /* package */void clearInputField() {
	textField.setText("");
    }

    /**
     * Befehl oder Zeichen unbekannt
     */
    /* package */void unknownCommand() {
	textField.setText("Ungültiger Wert");
	textField.selectAll();
    }

    /**
     * Gibt den vom Benutzer eingegeben Text zurück.
     * 
     * @return Text (üblicherweise Zahl oder Kommando)
     */
    /* package */String getInputText() {
	return textField.getText();
    }

    @Override
    public void update(Observable o, Object arg) {
	textArea.setText("");
	for (Double d : calc.getStack()) {
	    textArea.append(String.format("%1$f", d));
	    textArea.append("\n");
	}
    }
}

/**
 * Kontroller des MVC-Pattern
 * 
 * @author René Anderes
 */
/* package */class Controller implements Observer, ActionListener {

    private final CalcObservable calc;
    private final View view;
    private int stackSize;

    /**
     * Konstruktor
     * 
     * @param calc
     *            Model
     * @param view
     *            View
     */
    public Controller(final CalcObservable calc, final View view) {
	calc.addObserver(this);
	this.calc = calc;
	this.view = view;
    }

    @Override
    public void update(final Observable o, final Object arg) {
	stackSize = calc.getStack().size();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
	String text = view.getInputText();
	if (isDigit(text)) {
	    view.clearInputField();
	    calc.pushToStack(Double.parseDouble(text));
	} else if (isMultiply(text) && stackSize >= 2) {
	    view.clearInputField();
	    calc.multiply();
	} else if (isAddition(text) && stackSize >= 2) {
	    view.clearInputField();
	    calc.addition();
	} else {
	    view.unknownCommand();
	}
    }

    /**
     * Check, ob es eine Zahl ist.
     * 
     * @param text
     *            Eingabetext
     * @return <code>true</code>, wenn es eine Zahl ist.
     */
    private boolean isDigit(final String text) {
	return text.matches("\\d+");
    }

    /**
     * Check, ob es sich um eine Multiplikation handelt.
     * 
     * @param text
     *            Eingabetext
     * @return <code>true</code>, wenn es ein Kommando für die Multiplikation
     *         ist
     */
    private boolean isMultiply(final String text) {
	return text.matches("[*]");
    }

    /**
     * Check, ob es sich um eine Addition handelt.
     * 
     * @param text
     *            Eingabetext
     * @return <code>true</code>, wenn es ein Kommando für die Addition ist
     */
    private boolean isAddition(final String text) {
	return text.matches("[+]");
    }
}
