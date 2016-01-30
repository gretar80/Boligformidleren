/*
 * Innhold: Den abstrakte klassen Bolig.
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Eivind, Gretar
 */

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Klassen skal representere en bolig og lagre opplysninger om denne, samt
 * inneholde nødvendige metoder som set/get og toString.
 */
public abstract class Bolig implements Serializable {

    private String gateadresse, poststed, type, beskrivelse;
    private int postnr, areal, soverom, byggeaar, pris;
    private Date dato;
    private boolean ledig = true;
    //bilde

    // konstruktør
    public Bolig(String gateadresse, int postnr, String poststed, String type, String beskrivelse,
            Date dato, int areal, int soverom, int byggeaar, int pris) {

        this.gateadresse = gateadresse;
        this.postnr = postnr;
        this.poststed = poststed;

        this.areal = areal;
        this.byggeaar = byggeaar;
        this.dato = dato;
        this.pris = pris;
        this.soverom = soverom;
        this.type = type;

        this.beskrivelse = beskrivelse;
    }

    // get-metoder
    public String getGateadresse() {
        return gateadresse;
    }

    public int getPostnr() {
        return postnr;
    }

    public String getPoststed() {
        return poststed;
    }

    public String getType() {
        return type;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public Date getDato() {
        return dato;
    }

    public int getAreal() {
        return areal;
    }

    public int getSoverom() {
        return soverom;
    }

    public int getByggeaar() {
        return byggeaar;
    }

    public int getPris() {
        return pris;
    }

    public boolean getLedig() {
        return ledig;
    }

    // set-metoder
    public void setGateadresse(String a) {
        gateadresse = a;
    }

    public void setPostnr(int p) {
        postnr = p;
    }

    public void setPoststed(String p) {
        poststed = p;
    }

    public void setType(String t) {
        type = t;
    }

    public void setBeskrivelse(String b) {
        beskrivelse = b;
    }

    public void setDato(Date a) {
        dato = a;
    }

    public void setInneAreal(int i) {
        areal = i;
    }

    public void setAntRom(int a) {
        soverom = a;
    }

    public void setByggeaar(int b) {
        byggeaar = b;
    }

    public void setPris(int p) {
        pris = p;
    }

    public void boligErOpptatt() {
        ledig = false;
    }

    public void boligErLedig() {
        ledig = true;
    }

    // Lager en array som skal tilsvare en rad i en boligtabell.
    public Object[] tilRad() {
        Object[] rad = {gateadresse, poststed, new Integer(postnr), 
            new Integer(pris), new Integer(areal), new Integer(byggeaar), 
            dato};
        return rad;
    }

    // redefinert equals-metode for å sammelikne to boliger
    public boolean equals(Object o) {
        if (o instanceof Bolig) {
            Bolig b = (Bolig) o;

            // Boligene regnes som like hvis de har samme adresse.
            if (b.getGateadresse().equals(gateadresse)
                    && b.getPostnr() == postnr
                    && b.getPoststed().equals(poststed)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String s = "Gateadresse: " + gateadresse + "\nPostnummer: " + postnr + 
                "\nPoststed: " + poststed + "\nType: " + type + 
                "\nBeskrivelse: " + beskrivelse + "\nAvertert fra: " + 
                StartVindu.getENKELDATOFORMAT().format(dato) + "\nAreal: " + areal
                + "\nAntall rom: " + soverom + "\nByggeår: " + byggeaar
                + "\nPris: " + pris + "\nLedig: " + (ledig ? "ja" : "nei");

        return s;
    }
}
