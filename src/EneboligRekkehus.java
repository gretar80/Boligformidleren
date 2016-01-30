/*
 * Innhold: Klassen er subklasse til Bolig og skal mer spesifikt representere en enebolig eller rekkehus.
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Eivind, Gretar
 */

import java.util.Date;

public class EneboligRekkehus extends Bolig {

    private int antEtasjer, tomtAreal;
    private boolean kjeller;

    // konstruktør
    public EneboligRekkehus(String gateadresse, int postnr, String poststed, String type, String beskrivelse,
            Date annonsedato, int inneAreal, int antRom, int byggeaar, int pris,
            int antEtasjer, int tomtAreal, boolean kjeller) {
        
        super(gateadresse, postnr, poststed, type, beskrivelse, annonsedato, inneAreal, antRom,
                byggeaar, pris);
        this.antEtasjer = antEtasjer;
        this.tomtAreal = tomtAreal;
        this.kjeller = kjeller;
    }
    
    // get-metoder
    public int getAntEtasjer(){
        return antEtasjer;
    }
    
    public int getTomtAreal(){
        return tomtAreal;
    }
    
    public boolean getKjeller(){
        return kjeller;
    }
    
    // set-metoder
    public void setAntEtasjer( int a ){
        antEtasjer = a;
    }
    
    public void setTomtAreal( int t ){
        tomtAreal = t;
    }
    
    public void setKjeller( boolean k ){
        kjeller = k;
    }
    
    public String toString(){
        String s = super.toString() + "\nEtasjer: " + antEtasjer + "\nTomteareal: "
                + tomtAreal + "\nKjeller: " + (kjeller ? "Ja" : "Nei") + "\n";
        return s;
    }
}
