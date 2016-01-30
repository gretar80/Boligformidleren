/*
 * INNHOLD: Klassen Personmengde.
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Eivind, Gretar
 */

import java.util.*;
import java.io.*;

/* Kort beskrivelse:
 * Klassen inneholder en TreeSet-mengde og metoder for å sette inn/fjerne/finne
 * Utleier-objekter. Klassen inneholder også metode for å registrere en bolig
 * til en utleiers boligliste.
 */
public class UtleierMengde implements Serializable {

    private Comparator komp = new Personsammenlikner();
    private Set<Utleier> mengde = new TreeSet<>(komp);

    // setter inn ny person i mengden
    public void settInn(Utleier ul) {
        mengde.add(ul);
    }

    // fjerner et Utleier-objekt fra mengde
    public boolean fjern(Utleier ul) {
        if (ul.getBoligliste().getListe().isEmpty()) {
            return mengde.remove(ul);
        }
        return false;
    }

    // returnerer mengden (usortert)
    public Set<Utleier> getSortertMengde() {
        return mengde;
    }

    // Returnere et personobjektet som hører til navnet
    public Utleier finnUtleier(String fornavn, String etternavn) {
        Iterator<Utleier> utleierIter = mengde.iterator();
        Utleier ul;
        while (utleierIter.hasNext()) {
            ul = utleierIter.next();
            if (ul.getFornavn().equals(fornavn) && ul.getEtternavn().equals(etternavn)) {
                return ul;
            }
        }
        return null;
    }

    // Returnerer utleieren som leier ut parameter bolig. Returnerer null hvis bolig ikke finnes.
    public Utleier finnUtleierViaBolig(Bolig b) {
        Iterator<Utleier> utleierIter = mengde.iterator();
        Utleier ul;
        while (utleierIter.hasNext()) {
            ul = utleierIter.next();
            if (ul.getBoligliste().getListe().contains(b)) {
                return ul;
            }
        }
        return null;
    }

    // søker i hele utleiermengden etter bolig ut fra en gitt adresse
    public Bolig finnBolig(String gateadresse, int postnr, String poststed) {
        Iterator<Utleier> utleierIter = mengde.iterator();
        Utleier ul;
        BoligListe bl;
        Bolig b;

        while (utleierIter.hasNext()) {
            ul = utleierIter.next();
            bl = ul.getBoligliste();
            b = bl.finnBolig(gateadresse, postnr, poststed);
            if (b != null) {
                return b;
            }
        }
        return null;
    }

    // registrerer ny bolig til en utleier
    public boolean regBolig(Utleier u, Bolig b) {

        if (b == null || u == null) {
            return false;
        }

        /**
         * finner utleieren sin boligliste og kaller på containsmetoden for å
         * sjekke om boligen finnes fra før i lista, må evt. gjøres for hver
         * utleier for maks sikkerhet. Vil komplisere hele funksjonen.
         */
        Iterator<Utleier> iter = mengde.iterator();
        BoligListe bl;
        while (iter.hasNext()) {
            bl = iter.next().getBoligliste();
            List l = bl.getListe();
            if (l.contains(b)) {
                return false;
            }
        }

        //Boligen kan registreres
        u.regBolig(b);
        return true;
    }

    // Returnere totalt antall registrerte boliger.
    public int antallBoliger() {
        int ant = 0;
        Iterator<Utleier> utleierIter = mengde.iterator();
        Utleier utleier;
        while (utleierIter.hasNext()) {
            utleier = utleierIter.next();
            ant += utleier.getBoligliste().getListe().size();
        }
        return ant;
    }

    // For filbehandling. ruleBasedKollator er ikke serialiserbar.
    public Set<Utleier> kopierMengdeUsortert() {
        Set<Utleier> usortertKopiMengde = new TreeSet<>();
        Iterator<Utleier> iter = mengde.iterator();
        while (iter.hasNext()) {
            Utleier u = iter.next();
            usortertKopiMengde.add(u);
        }
        return usortertKopiMengde;
    }
    
    // skriver ut alt som ligger i mengden
    public String toString() {
        Iterator<Utleier> iter = mengde.iterator();

        String utleiere = "";
        Utleier ul;
        while (iter.hasNext()) {
            ul = iter.next();
            utleiere += ul.toString() + "\n";
        }
        return utleiere;
    }
}
