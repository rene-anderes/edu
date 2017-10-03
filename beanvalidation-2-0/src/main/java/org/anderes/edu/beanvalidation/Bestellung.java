package org.anderes.edu.beanvalidation;

import java.time.LocalDate;

public class Bestellung {

    private LocalDate lieferdatum;
    private Kundendaten kundendaten;
    private Kundendaten rechnungsadresse;
    private boolean rechnungAnKundenadresse;

    public Kundendaten getRechnungsadresse() {
        return rechnungsadresse;
    }

    public Bestellung setRechnungsadresse(Kundendaten rechnungsadresse) {
        this.rechnungsadresse = rechnungsadresse;
        return this;
    }

    public LocalDate getLieferdatum() {
        return lieferdatum;
    }

    public Bestellung setLieferdatum(final LocalDate date) {
        this.lieferdatum = date;
        return this;
    }

    public Bestellung setKundendaten(final Kundendaten kundendaten) {
        this.kundendaten = kundendaten;
        return this;
    }

    public boolean isRechnungAnKundenadresse() {
        return rechnungAnKundenadresse;
    }

    public Bestellung setRechnungAnKundenadresse(boolean rechnungAnKundenadresse) {
        this.rechnungAnKundenadresse = rechnungAnKundenadresse;
        return this;
    }

    public Kundendaten getKundendaten() {
        return kundendaten;
    }

}
