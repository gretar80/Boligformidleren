/*
 * Programutvikling vår 2014
 * Prosjektoppgave - Boligformidleren.
 * 
 * Gruppe 16:
 * Eivind Schulstad	(s198752)
 * Gretar Ævarsson	(s198586)
 * Sigurd Hølleland	(s198597)
 * 
 * Innhold: Main-metode.
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Eivind
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Klassen skal kjøre og avslutte programmet, i tilegg til å skrive data til fil
 * ved lukking.
 */
public class Boligformidleren {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final StartVindu vindu = new StartVindu();
        vindu.setLocation(vindu.getXPOSSTARTVINDU(), vindu.getYPOSSTARTVINDU());
        vindu.addWindowListener(
                new WindowAdapter(){
                    public void windowClosing(WindowEvent e){
                        vindu.skrivTilFil();
                        System.exit(0);
                    }
                });
    }
    
}
