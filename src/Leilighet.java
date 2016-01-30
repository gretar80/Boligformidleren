/*
 * Innhold: Klassen Leilighet, som er subklasse til Bolig og skal representere en leilighet.
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Eivind, Gretar
 */

import java.util.Date;

public class Leilighet extends Bolig {
    private int etasje;
    private boolean heis, balkong;

    // konstruktør
    public Leilighet(String gateadresse, int postnr, String poststed, String type, String beskrivelse,
            Date annonsedato, int inneAreal, int antRom, int byggeaar, int pris,
            int etasje, boolean heis, boolean balkong){

        super(gateadresse, postnr, poststed, type, beskrivelse, annonsedato, inneAreal, antRom,
                byggeaar, pris);
        this.etasje = etasje;
        this.heis = heis;
        this.balkong = balkong;
    }

    // get-metoder
    public int getEtasje(){
        return etasje;
    }

    public boolean getHeis(){
        return heis;
    }

    public boolean getBalkong(){
        return balkong;
    }

    // set-metoder
    public void setEtasje( int e ){
        etasje = e;
    }

    public void setHeis( boolean h ){
        heis = h;
    }

    public void setBalkong( boolean b ){
        balkong = b;
    }
    
    public String toString(){
        String s = super.toString() + "\nEtasje: " + etasje + "\nHeis: "
                + (heis ? "Ja" : "Nei") + "\nBalkong: " + (balkong ? "Ja" : "Nei") + "\n";
        return s;
    }
}
