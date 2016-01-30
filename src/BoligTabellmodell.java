/*
 * Innhold: Tabellmodell for visning av boliger
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Eivind
 */

import java.util.Date;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;

/**
 * Bestemmer kolonnenavn og størrelse på tabellarray. Bestemmer også hvilke
 * kolonner som er nøkkelkolonner, altså boligens ID. Tabellmodellen henter
 * radene fra tilArray-metode i klassen Bolig.
 */
public class BoligTabellmodell extends AbstractTableModel {

    public static final int ANTALLKOLONNER = 7;
    private final String[] KOLONNENAVN = {"Adresse", "Poststed", "Postnummer",
        "Pris", "Areal", "Byggeår", "Avertert fra"};
    private final int[] NOKKELKOLONNER = {0, 1, 2};
    private Object[][] celler;

    //Tegner tabell i konstruktør
    public BoligTabellmodell() {
        int maxAntRader = StartVindu.getUtleierVindu().getUtleierMengde().antallBoliger();
        celler = new Object[maxAntRader][KOLONNENAVN.length];
        Iterator<Utleier> utleierIter = StartVindu.getUtleierVindu().getUtleierMengde().getSortertMengde().iterator();
        int radTeller = 0;

        while (utleierIter.hasNext()) {
            Utleier u = utleierIter.next();
            Iterator<Bolig> boligIter = u.getBoligliste().getListe().iterator();
            for (int rad = radTeller; rad < maxAntRader; rad++) {
                if (boligIter.hasNext()) {
                    Bolig b = boligIter.next();
                    celler[rad] = b.tilRad();
                    radTeller++;
                }
            }
        }
    }
    //Konstruktør som tar imot en allerede ferdiglagd tabellarray
    public BoligTabellmodell(Object[][] celler){
        this.celler = celler;
    }

    // Redefinerer arvede metoder
    public String getColumnName(int kolonne) {
        return KOLONNENAVN[kolonne];
    }

    public Object getValueAt(int rad, int kolonne) {
        return celler[rad][kolonne];
    }

    public int getColumnCount() {
        return KOLONNENAVN.length;
    }

    public int getRowCount() {
        return celler.length;
    }

    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            case 4:
                return Integer.class;
            case 5:
                return Integer.class;
            case 6:
                return Date.class;
            default:
                return String.class;
        }
    }

    // Returnerer kolonneindeksen til kolonnene som utgjør boligens ID.
    public int[] getNokellkolonner() {
        return NOKKELKOLONNER;
    }
}
