/*
 * Innhold: Boligsøkermengden selv hvor alle boligsøker-objektene ligger
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Eivind, Gretar
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Eivind
 */
public class BoligsoekerMengde {

    private Comparator komp = new Personsammenlikner();

    private Set<Boligsoeker> mengde = new TreeSet<>(komp);

    // setter inn ny person i mengden
    public void settInn(Boligsoeker b) {
        mengde.add(b);
    }

    public boolean fjern(Boligsoeker bs) {
        return mengde.remove(bs);
    }
    
    public Set<Boligsoeker> getMengde(){
        return mengde;
    }
    
    // Returnere et boligsoeker som hører til navnet
    public Boligsoeker finnBoligsoeker(String fornavn, String etternavn) {
        Iterator<Boligsoeker> bsIter = mengde.iterator();
        Boligsoeker bs;
        while (bsIter.hasNext()) {
            bs = bsIter.next();
            if (bs.getFornavn().equals(fornavn) && bs.getEtternavn().equals(etternavn)) {
                return bs;
            }
        }
        return null;
    }
    // For filbehandling. ruleBasedKollator er ikke serialiserbar.
    public Set<Boligsoeker> kopierMengdeUsortert(){
        Set<Boligsoeker> usortertKopiMengde = new TreeSet<>();
        Iterator<Boligsoeker> iter = mengde.iterator();
        while(iter.hasNext()){
            Boligsoeker b = iter.next();
            usortertKopiMengde.add(b);
        }
        return usortertKopiMengde;
    }

    public String toString() {
        Iterator<Boligsoeker> iter = mengde.iterator();

        String boligsoekere = "";
        Boligsoeker bs;
        while (iter.hasNext()) {
            bs = iter.next();
            boligsoekere += bs.toString() + "\n";
        }
        return boligsoekere;
    }
}
