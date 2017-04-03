package org.anderes.edu.exam.builder;

public class Rs232App {

    /**
     * @param args
     */
    public static void main(String[] args) {
	
	Rs232Settings settings = 
		new Rs232Settings(2400, "COM2", 2, 7, "none", "none");

	/**
	 * Der Konstruktor ist fehleranfällig, da die Parameter
	 * einfach verwechselt werden können. Die Lesbarkeit des
	 * Codes leidet sehr unter dieser Art der Instanzierung.
	 * 
	 * Die Klasse Rs232Settings ist sehr aufwändig, da für jede
	 * mögliche Kombination von Werten ein eigener Konstruktor
	 * definiert werden muss.
	 * 
	 * Mit welchem Pattern verbessern Sie den Code?
	 * Zeichnen sie ein UML-Klassendiagramm das die
	 * vollständige Lösung durch das Pattern zeigt.
	 */
    }

}
