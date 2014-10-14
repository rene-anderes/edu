package org.anderes.edu.beanvalidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.anderes.edu.beanvalidation.constrains.Housenumber;

public class Kundendaten {

    @Size(min=2, max=50)
    private String firma;
    @Size(min=2, max=50)
    private String abteilung;
    @NotNull @Size(min=2, max=150)
    private String name;
    @NotNull @Size(min=2, max=150)
    private String strasse;
    @Housenumber
    private String hausnummer;
    @Min(1000) @Max(9999)
    private int plz;
    @NotNull @Size(min=2, max=100)
    private String ort;
    @Pattern(regexp="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

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
