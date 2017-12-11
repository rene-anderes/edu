package org.anderes.edu.beanvalidation;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Kundendaten {

    private String firma;
    private String abteilung;
    private String name;
    private String strasse;
    private String hausnummer;
    @Min(value=1000) @Max(value=9999)
    private int plz;
    private String ort;
    @Email
    private String email;

    public String getFirma() {
        return firma;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public String getName() {
        return name;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public int getPlz() {
        return plz;
    }

    public String getOrt() {
        return ort;
    }

    public String getEmail() {
        return email;
    }
    
    public Kundendaten setFirma(final String firma) {
        this.firma = firma;
        return this;
    }

    public Kundendaten setAbteilung(String abteilung) {
        this.abteilung = abteilung;
        return this;
    }

    public Kundendaten setName(String name) {
        this.name = name;
        return this;
    }

    public Kundendaten setStrasse(String strasse) {
        this.strasse = strasse;
        return this;
    }

    public Kundendaten setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
        return this;
    }

    public Kundendaten setPlz(int plz) {
        this.plz = plz;
        return this;
    }

    public Kundendaten setOrt(String ort) {
        this.ort = ort;
        return this;
    }

    public Kundendaten setEmail(String email) {
        this.email = email;
        return this;
    }

}
