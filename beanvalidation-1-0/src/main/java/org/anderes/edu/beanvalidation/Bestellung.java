package org.anderes.edu.beanvalidation;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.anderes.edu.beanvalidation.constrains.ValidRechnungsadresse;
import org.anderes.edu.beanvalidation.groups.Minimal;

@ValidRechnungsadresse
public class Bestellung {

    @NotNull(groups = { Default.class, Minimal.class })
    @Future(groups = { Default.class, Minimal.class })
    private Date lieferdatum;
    @Valid @NotNull
    private Kundendaten kundendaten;
    @Valid
    private Kundendaten rechnungsadresse;
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
