/*
 * Innhold: Tabellmodell for visning av kontrakter
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Eivind
 */

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class KontraktTabellmodell extends AbstractTableModel {

    private final String[] KOLONNENAVN = {"Adresse", "Sted",
        "Utleier", "Leietaker", "Begynte", "Avsluttes"};
    private Object[][] celler;

    // Tegner tabell i kontruktør
    public KontraktTabellmodell(List<Kontrakt> l) {
        int maxAntRader = l.size();
        celler = new Object[maxAntRader][KOLONNENAVN.length];
        Iterator<Kontrakt> iter = l.iterator();
        Kontrakt k;
        for (int rad = 0; rad < maxAntRader; rad++) {
            if (iter.hasNext()) {
                k = iter.next();
                celler[rad] = k.tilRad();
            }
        }
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

    public Class getColumnClass(int kolonne) {
        switch (kolonne) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Date.class;
            case 5:
                return Date.class;
            default:
                return String.class;
        }
    }
}
