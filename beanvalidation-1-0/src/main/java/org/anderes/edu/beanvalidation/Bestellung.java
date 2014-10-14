package org.anderes.edu.beanvalidation;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.anderes.edu.beanvalidation.constrains.ValidBestellung;

@ValidBestellung
public class Bestellung {

    @Future
    private Date lieferdatum;
    @Valid @NotNull
    private Kundendaten kundendaten;
    @Valid
    private Kundendaten rechnungsadresse;
    @Valid
    private boolean rechnungAnKundenadresse;

    public Kundendaten getRechnungsadresse() {
        return rechnungsadresse;
    }

    public void setRechnungsadresse(Kundendaten rechnungsadresse) {
        this.rechnungsadresse = rechnungsadresse;
    }

    public Date getLieferdatum() {
        return lieferdatum;
    }

    public void setLieferdatum(final Date date) {
        this.lieferdatum = date;
    }

    public void setKundendaten(final Kundendaten kundendaten) {
        this.kundendaten = kundendaten;
        
    }

    public boolean isRechnungAnKundenadresse() {
        return rechnungAnKundenadresse;
    }

    public void setRechnungAnKundenadresse(boolean rechnungAnKundenadresse) {
        this.rechnungAnKundenadresse = rechnungAnKundenadresse;
    }

    public Kundendaten getKundendaten() {
        return kundendaten;
    }

}
