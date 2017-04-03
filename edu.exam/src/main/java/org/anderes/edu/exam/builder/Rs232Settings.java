package org.anderes.edu.exam.builder;

/**
 * Settings für die Einstellungen einer seriellen Schnittstelle
 * 
 * @author René Anderes
 *
 */
public class Rs232Settings {

    private int baud = 9600;
    private String port = "COM1";
    private int stopBits = 1;
    private int dataBits = 8;
    private String parity = "none";
    private String flowControl = "none";
    
    /**
     * Konstruktor
     * @param baud Baud
     * @param port Port
     * @param stopBits Anzahl Stop-Bits
     * @param dataBits Anzal Data-Bits
     * @param parity Parity
     * @param flowControl Flusskontrolle
     */
    public Rs232Settings(int baud, String port, int stopBits, 
	    int dataBits, String parity, String flowControl) {
	this.baud = baud;
	this.port = port;
	this.stopBits = stopBits;
	this.dataBits = dataBits;
	this.parity = parity;
	this.flowControl = flowControl;
    }
    
    /**
     * Konstruktor
     * @param baud Baud
     * @param port Port
     * @param stopBits Anzahl Stop-Bits
     * @param dataBits Anzal Data-Bits
     * @param parity Parity
     */
    public Rs232Settings(int baud, String port, int stopBits, 
	    int dataBits, String parity) {
	this.baud = baud;
	this.port = port;
	this.stopBits = stopBits;
	this.dataBits = dataBits;
	this.parity = parity;
    }
 
    /**
     * Konstruktor
     * @param baud Baud
     * @param port Port
     * @param stopBits Anzahl Stop-Bits
     * @param dataBits Anzal Data-Bits
     */
    public Rs232Settings(int baud, String port, 
	    int stopBits, int dataBits) {
	this.baud = baud;
	this.port = port;
	this.stopBits = stopBits;
	this.dataBits = dataBits;
    }
}
