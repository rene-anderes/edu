package org.anderes.edu.gui.pm.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 * View für das Presentation-Model-Pattern
 * 
 * @author René Anderes
 */
public class View {

	private final JFrame f;
	private final JTextField textField;
	private final JTextArea textArea;
	private final PresentationModel presentationModel;
	
	/**
	 * Konstruktor
	 */
	public View(final PresentationModel presentationModel) {
		this.presentationModel = presentationModel;
		f = new JFrame( "Taschenrechner" ); 
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 
		f.setSize(300, 200);
		
		JPanel panel = new JPanel(new GridBagLayout());
		textField = new JTextField(20);
		textField.setBackground(Color.WHITE);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				textField.setText("");
				if (text.matches("\\d+")) {
					presentationModel.input(text);
					updateStack();
				} else if (presentationModel.isCommandEnabled()) {
					presentationModel.command(text);
					updateStack();
				} else {
					textField.setText("Ung�ltige Funktion");
					textField.selectAll();
				}
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
	
	/**
	 * Die Anzeige des Stacks wird aktualisiert.
	 */
	private void updateStack() {
		textArea.setText("");
		for (Double d : presentationModel.getStack()) {
			textArea.append(String.format("%1$f", d));
			textArea.append("\n");
		}
	}
}
