/*
 * Innhold: Vindu som brukes for registrering, slettning og endring av boliger.
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Gretar, Eivind
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Iterator;

public class BoligVindu extends JFrame implements ActionListener, FocusListener {

    /* Startvinduet er oppbygget sånn at en masterpanel inneholder allt (vinduet selv).
     * Top-panelen inneholder tre paneler:
     *      øverst er en panel for felles felt, 
     *      i midten er enten enebolig felt eller leilighet felt
     *      nederst er en panel som inneholder knappene
     * Under-panelen er brukt for utskrift-området
     */
    private JPanel masterPanel, top, fellesPanel, knappPanel, eneboligPanel, leilighetPanel, under;

    // felles for eneboliger og leiligheter
    private JTextField gateadresse, postnr, poststed, areal, byggeaar, beskrivelse, pris, avertertDato;
    private JComboBox boligtype, antRom;
    private final String[] TYPE = {"Velg", "Enebolig/rekkehus", "Leilighet"};
    private final int ANTRADFELLES = 10, ANTRADKNAPP = 3, ANTRADENEBOLIG = 3, ANTRADLEILIGHET = 3, 
            ANTKOL = 3, GAP = 5, BREDDE = 400, HOYDE = 700, FELTLENGDE = 10, SELECTEDINDEX = 0;

    // for knappPanel
    private JTextField utleierFornavn, utleierEtternavn;
    private JButton endreType, endreAreal, endreRom, endreByggeaar, endrePris, endreDato, endreBeskrivelse, 
            endreEtasjer, endreTomtestorrelse, endreKjeller,
            endreEtasje, endreHeis, endreBalkong,
            regBolig, slettBolig, blankFelter;

    // kun for eneboliger
    private JTextField tomtestorrelse;
    private JComboBox etasjer;
    private JCheckBox kjeller;

    // kun for leiligheter
    private final String[] ETASJELISTE = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
        "10", "11", "12", "13", "14", "15"};
    private JComboBox etasje;
    private JCheckBox heis, balkong;

    private JTextArea output;

    // konstruktør
    public BoligVindu() {
        super("Bolig");

        // paneler
        masterPanel = new JPanel(new BorderLayout());
        top = new JPanel(new BorderLayout());
        fellesPanel = new JPanel(new GridLayout(ANTRADFELLES, ANTKOL, GAP, GAP));
        knappPanel = new JPanel(new GridLayout(ANTRADKNAPP, ANTKOL, GAP, GAP));
        eneboligPanel = new JPanel(new GridLayout(ANTRADENEBOLIG, ANTKOL, GAP, GAP));
        eneboligPanel.setVisible(true);
        leilighetPanel = new JPanel(new GridLayout(ANTRADLEILIGHET, ANTKOL, GAP, GAP));
        leilighetPanel.setVisible(true);
        under = new JPanel(new BorderLayout());
        masterPanel.add(top, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        top.add(fellesPanel, BorderLayout.PAGE_START);
        top.add(knappPanel, BorderLayout.PAGE_END);
        this.getContentPane().add(masterPanel);
        setSize(BREDDE, HOYDE);

        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);

        // felles textfields og drop-down list
        fellesPanel.add(new JLabel("Gateadresse: "));
        gateadresse = new JTextField(FELTLENGDE);
        gateadresse.addFocusListener(this);
        fellesPanel.add(gateadresse);
        fellesPanel.add(new JLabel(""));    // tom felt
        
        fellesPanel.add(new JLabel("Postnummer: "));
        postnr = new JTextField(FELTLENGDE);
        postnr.addFocusListener(this);
        fellesPanel.add(postnr);
        fellesPanel.add(new JLabel(""));    // tom felt
        
        fellesPanel.add(new JLabel("Poststed: "));
        poststed = new JTextField(FELTLENGDE);
        poststed.addFocusListener(this);
        fellesPanel.add(poststed);
        fellesPanel.add(new JLabel(""));    // tom felt

        fellesPanel.add(new JLabel("Boligtype: "));
        boligtype = new JComboBox(TYPE);
        boligtype.setSelectedIndex(SELECTEDINDEX);
        boligtype.addActionListener(this);
        fellesPanel.add(boligtype);
        endreType = new JButton("Endre");
        endreType.addActionListener(this);
        fellesPanel.add(endreType);

        fellesPanel.add(new JLabel("Areal: "));
        areal = new JTextField(FELTLENGDE);
        fellesPanel.add(areal);
        
        endreAreal = new JButton("Endre");
        endreAreal.addActionListener(this);
        fellesPanel.add(endreAreal);

        fellesPanel.add(new JLabel("Antall soverom: "));
        antRom = new JComboBox(StartVindu.getANTSOVEROM());
        antRom.setSelectedIndex(SELECTEDINDEX);
        fellesPanel.add(antRom);
        
        endreRom = new JButton("Endre");
        endreRom.addActionListener(this);
        fellesPanel.add(endreRom);

        fellesPanel.add(new JLabel("Byggeår: "));
        byggeaar = new JTextField(FELTLENGDE);
        fellesPanel.add(byggeaar);

        endreByggeaar = new JButton("Endre");
        endreByggeaar.addActionListener(this);
        fellesPanel.add(endreByggeaar);
        
        fellesPanel.add(new JLabel("Leiepris: "));
        pris = new JTextField(FELTLENGDE);
        fellesPanel.add(pris);

        endrePris = new JButton("Endre");
        endrePris.addActionListener(this);
        fellesPanel.add(endrePris);
        
        fellesPanel.add(new JLabel("Avetert dato: "));
        avertertDato = new JTextField(FELTLENGDE);
        Date d = new Date();
        avertertDato.setText(StartVindu.getENKELDATOFORMAT().format(d));
        avertertDato.addFocusListener(this);
        fellesPanel.add(avertertDato);

        endreDato = new JButton("Endre");
        endreDato.addActionListener(this);
        fellesPanel.add(endreDato);
        
        fellesPanel.add(new JLabel("Beskrivelse: "));
        beskrivelse = new JTextField(FELTLENGDE);
        fellesPanel.add(beskrivelse);
        
        endreBeskrivelse = new JButton("Endre");
        endreBeskrivelse.addActionListener(this);
        fellesPanel.add(endreBeskrivelse);

        knappPanel.add(new JLabel("Utleier fornavn: "));
        utleierFornavn = new JTextField(FELTLENGDE);
        knappPanel.add(utleierFornavn);
        knappPanel.add(new JLabel(""));

        knappPanel.add(new JLabel("Utleier etternavn: "));
        utleierEtternavn = new JTextField(FELTLENGDE);
        knappPanel.add(utleierEtternavn);
        knappPanel.add(new JLabel(""));

        // enebolig textfields
        eneboligPanel.add(new JLabel("Antall etasjer: "));
        etasjer = new JComboBox(StartVindu.getETASJERENEBOLIG());
        etasjer.setSelectedIndex(SELECTEDINDEX);
        eneboligPanel.add(etasjer);
        
        endreEtasjer = new JButton("Endre");
        endreEtasjer.addActionListener(this);
        eneboligPanel.add(endreEtasjer);

        eneboligPanel.add(new JLabel("Tomtestørrelse: "));
        tomtestorrelse = new JTextField(FELTLENGDE);
        eneboligPanel.add(tomtestorrelse);

        endreTomtestorrelse = new JButton("Endre");
        endreTomtestorrelse.addActionListener(this);
        eneboligPanel.add(endreTomtestorrelse);
        
        eneboligPanel.add(new JLabel("Kjeller: "));
        kjeller = new JCheckBox("");
        eneboligPanel.add(kjeller);

        endreKjeller = new JButton("Endre");
        endreKjeller.addActionListener(this);
        eneboligPanel.add(endreKjeller);
        
        // leilighet textfields
        leilighetPanel.add(new JLabel("Etasje: "));
        etasje = new JComboBox(ETASJELISTE);
        etasje.setSelectedIndex(SELECTEDINDEX);
        leilighetPanel.add(etasje);
        
        endreEtasje = new JButton("Endre");
        endreEtasje.addActionListener(this);
        leilighetPanel.add(endreEtasje);

        leilighetPanel.add(new JLabel("Heis: "));
        heis = new JCheckBox("");
        leilighetPanel.add(heis);
        
        endreHeis = new JButton("Endre");
        endreHeis.addActionListener(this);
        leilighetPanel.add(endreHeis);

        leilighetPanel.add(new JLabel("Balkong: "));
        balkong = new JCheckBox();
        leilighetPanel.add(balkong);

        endreBalkong = new JButton("Endre");
        endreBalkong.addActionListener(this);
        leilighetPanel.add(endreBalkong);
        
        // buttons
        regBolig = new JButton("Register bolig");
        regBolig.addActionListener(this);
        knappPanel.add(regBolig);

        slettBolig = new JButton("Slett bolig");
        slettBolig.addActionListener(this);
        knappPanel.add(slettBolig);

        blankFelter = new JButton("Blank felter");
        blankFelter.addActionListener(this);
        knappPanel.add(blankFelter);
    }

    // registrer bolig
    public void regBolig(String boligtype) {
        
        // regex
        if(!regexOK()){
            return;
        }
        
        Bolig bolig;
        Date dato = StartVindu.konverterDato(avertertDato.getText());

        // Kontrollerer tallverdier ved RegEx for å unngå parseException.
        JTextField[] testRegEx = {postnr, areal, byggeaar, pris, tomtestorrelse};
        if (!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNHELTALL(), testRegEx))) {
            output.setText("Feil ved innlesning av tallverdier. Bruk kun heltall");
            return;
        }

        if (boligtype.equals(TYPE[1])) {
            if (tomtestorrelse.getText().equals("")) {
                output.setText("Vennligst fylle inn tomtestørrelse!");
                return;
            }
            bolig = new EneboligRekkehus(gateadresse.getText(), Integer.parseInt(postnr.getText()), poststed.getText(), TYPE[1], beskrivelse.getText(), dato,
                    Integer.parseInt(areal.getText()), Integer.parseInt((String) antRom.getSelectedItem()), Integer.parseInt(byggeaar.getText()),
                    Integer.parseInt(pris.getText()), Integer.parseInt((String) etasjer.getSelectedItem()), Integer.parseInt(tomtestorrelse.getText()), kjeller.isSelected());
        } else {
            bolig = new Leilighet(gateadresse.getText(), Integer.parseInt(postnr.getText()), poststed.getText(), TYPE[2], beskrivelse.getText(), dato,
                    Integer.parseInt(areal.getText()), Integer.parseInt((String) antRom.getSelectedItem()), Integer.parseInt(byggeaar.getText()),
                    Integer.parseInt(pris.getText()), Integer.parseInt((String) etasje.getSelectedItem()), heis.isSelected(), balkong.isSelected());
        }

        Utleier ul = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(utleierFornavn.getText(), utleierEtternavn.getText());

        if (ul == null) {
            output.setText("Fann ikke utleier " + utleierFornavn.getText() + " " + utleierEtternavn.getText());
        } else {
            UtleierMengde um = StartVindu.getUtleierVindu().getUtleierMengde();
            if (um.regBolig(ul, bolig)) {
                blankFelter();
                output.setText("Bolig registrert");
            } else {
                output.setText("Boligen er allerede registrert");
            }
        }
    }

    // slett bolig
    public void slettBolig() {
        String fornavn = utleierFornavn.getText();
        String etternavn = utleierEtternavn.getText();
        Utleier ul = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(fornavn, etternavn);
        
        if (ul == null) {
            output.setText("Fant ikke utleier " + fornavn + " " + etternavn);
        }
        else {
            if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNPOSTNUMMER(), postnr.getText())){
                output.setText("Feil - ikke gyldig postnummer");
                return;
            }
            BoligListe bl = ul.getBoligliste();
            Bolig b = bl.finnBolig(gateadresse.getText(), Integer.parseInt(postnr.getText()), poststed.getText());
            // Sjekker at boligen ikke inngår i en kontrakt
            if(!b.getLedig()){
                output.setText("Boligen inngår i en kontrakt og kan derfor ikke"
                        + " slettes!");
                return;
            }
            if( StartVindu.visJaNeiMelding("Vil du slette boligen?", "Slett bolig").equals("Nei"))
                return;
            
            if (bl.fjern(b)) {
                output.setText("Boligen ble fjernet");
                blankFelter();
            } 
            else {
                output.setText("Fjerning mislyktes. Sjekk om boligen finnes i registeret.");
            }
        }
    }
    
    // sjekker regular expression på alle feltene
    public boolean regexOK(){
        // Kontrollerer alle felt ved RegEx for å unngå exceptions (f.eks parseException, NullPointerException, osv.)
        // gateadresse
        if(gateadresse.getText().equals("")){
            output.setText("Feil - du må fylle inn gateadresse");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNTALLBOKSTAV(), gateadresse.getText()))){
                output.setText("Feil - du må kun bruke bokstaver (min. 4 tegn)\nog heltall (1-3 sifre) i gateadresse");
                return false;
            }
        }
        // postnummer
        if(postnr.getText().equals("")){
            output.setText("Feil - du må fylle inn postnummer");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNPOSTNUMMER(), postnr.getText()))){
                output.setText("Feil - du må kun bruke heltall (4 sifre) i postnummer");
                return false;
            }
        }
        // poststed
        if(poststed.getText().equals("")){
            output.setText("Feil - du må fylle inn poststed");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNBOKSTAV(), poststed.getText()))){
                output.setText("Feil - du må kun bruke bokstaver (min. 2 tegn) i poststed");
                return false;
            }
        }
        // areal
        if(areal.getText().equals("")){
        output.setText("Feil - du må fylle inn areal");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNHELTALL(), areal.getText()))){
                output.setText("Feil - areal må kun inneholde heltall");
                return false;
            }
        }
        // byggeår
        if(byggeaar.getText().equals("")){
        output.setText("Feil - du må fylle inn byggeår");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNHELTALL(), byggeaar.getText()))){
                output.setText("Feil - byggeår må kun inneholde heltall");
                return false;
            }
        }
        // pris
        if(pris.getText().equals("")){
        output.setText("Feil - du må fylle inn pris");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNHELTALL(), pris.getText()))){
                output.setText("Feil - pris må kun inneholde heltall");
                return false;
            }
        }
        // avertert dato
        if(StartVindu.konverterDato(avertertDato.getText()) == null){
            output.setText("Feil - dato må være på format ('" + StartVindu.getDATOFORMAT() + "').");
            return false;
        }
        // beskrivelse
        if(!beskrivelse.getText().equals("")){
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNTALLELLERBOKSTAV(), beskrivelse.getText()))){
                output.setText("Feil - beskrivelse må kun inneholde norkse bokstaver og/eller heltall");
                return false;
            }
        }
        // tomtestørrelse
        if(((String)boligtype.getSelectedItem()).equals(TYPE[1])){
            if(!tomtestorrelse.getText().equals("")){
                if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNHELTALL(), tomtestorrelse.getText()))){
                    output.setText("Feil - tomtestørrelse må kun inneholde heltall");
                    return false;
                }
            }
        }
        
        return true;
    }
    
    // metoden endrer en felt for boligen
    public void endreFelt(String felt, String ny){
        if(ny.equals("")){
            output.setText("Feil - du må skrive noe i feltet");
            return;
        }
        
        if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNPOSTNUMMER(), postnr.getText())){
            output.setText("Feil - ikke gyldig postnummer");
            return;
        }
            
        Bolig b = StartVindu.getUtleierVindu().getUtleierMengde().finnBolig(gateadresse.getText(), Integer.parseInt(postnr.getText()), poststed.getText());
        
        if(b == null){
            output.setText("Finner ikke bolig");
            return;
        }
        
        EneboligRekkehus e = null;
        Leilighet l = null;
        
        if(b instanceof EneboligRekkehus)
            e = (EneboligRekkehus)b;
        else
            l = (Leilighet)b;
        
        String gammel = "";
        
        switch(felt){
            case "Boligtype":{
                if(ny.equals(TYPE[0])){
                    output.setText("Feil - du må velge type");
                    return;
                }
                    
                if(ny.equals(b.getType())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre boligtypen?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                // hvis vi endrer typen, må vi slette den fra utleieren og registrere den nye typen
                gammel = b.getType();
                ny = "";
                Utleier u = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleierViaBolig(b);
                
                if(b instanceof EneboligRekkehus){
                    u.regBolig(new Leilighet(e.getGateadresse(), e.getPostnr(), e.getPoststed(), TYPE[2], e.getBeskrivelse(), e.getDato(),
                    e.getAreal(), e.getSoverom(), e.getByggeaar(), e.getPris(),
                    Integer.parseInt((String) etasje.getSelectedItem()), heis.isSelected(), balkong.isSelected()));
                    ny = TYPE[2];
                    u.slettBolig(b);
                }
                else{
                    u.regBolig(new EneboligRekkehus(l.getGateadresse(), l.getPostnr(), l.getPoststed(), TYPE[1], l.getBeskrivelse(), l.getDato(),
                    l.getAreal(), l.getSoverom(), l.getByggeaar(), l.getPris(),
                    Integer.parseInt((String) etasjer.getSelectedItem()), (tomtestorrelse.getText().equals("") ? 0: Integer.parseInt(tomtestorrelse.getText())), kjeller.isSelected()));
                    ny = TYPE[1];
                    u.slettBolig(b);
                }
                    
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ny);
                
                break;
            }
            case "Areal":{
                if(ny.equals(Integer.toString(b.getAreal()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNHELTALL(), ny)){
                    output.setText("Feil - areal må kun inneholde heltall");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre arealen?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(b.getAreal());
                b.setInneAreal(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + b.getAreal());
                
                break;
            }
            case "Soverom":{
                if(ny.equals(Integer.toString(b.getSoverom()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre antall soverom?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(b.getSoverom());
                b.setAntRom(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + b.getSoverom());
                
                break;
            }
            case "Byggeår":{
                if(ny.equals(Integer.toString(b.getByggeaar()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNHELTALL(), ny)){
                    output.setText("Feil - byggeår må kun inneholde heltall");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre byggeåret?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(b.getByggeaar());
                b.setByggeaar(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + b.getByggeaar());
                
                break;
            }
            case "Leiepris":{
                if(ny.equals(Integer.toString(b.getPris()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNHELTALL(), ny)){
                    output.setText("Feil - pris må kun inneholde heltall");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre leieprisen?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(b.getPris());
                b.setPris(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + b.getPris());
                
                break;
            }
            case "Avertert dato":{
                // regex
                if(ny.equals(StartVindu.getDATOFORMAT())){
                    output.setText("Feil - du må skrive noe i feltet");
                    return;
                }
                if(!ny.equals(StartVindu.getDATOFORMAT())){
                    if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNDATO(), ny))){
                        output.setText("Feil - bruk format '" + StartVindu.getDATOFORMAT() + "' for dato");
                        return;
                    }
                }
                if(!(ny.equals(StartVindu.getDATOFORMAT())))
                    if(b.getDato() != null && (b.getDato()).equals(StartVindu.konverterDato(ny))){
                        output.setText(felt + " allerede lik: " + ny);
                        return;
                    }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre avertert dato?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                if(b.getDato()!= null)
                    gammel = StartVindu.getENKELDATOFORMAT().format(b.getDato());
                else
                    gammel = StartVindu.getDATOFORMAT();
                
                b.setDato(StartVindu.konverterDato(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + StartVindu.getENKELDATOFORMAT().format(b.getDato()));
                
                break;
            }
            case "Beskrivelse":{
                if(ny.equals(b.getBeskrivelse())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!ny.equals("")){
                    if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNTALLELLERBOKSTAV(), ny)){
                        output.setText("Feil - beskrivelse må kun inneholde\nbokstaver og/eller heltall");
                        return;
                    }
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre beskrivelsen?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = b.getBeskrivelse();
                b.setBeskrivelse(ny);

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + b.getBeskrivelse());
                
                break;
            }
            case "Etasjer":{
                if(ny.equals(Integer.toString(e.getAntEtasjer()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre antall etasjer?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(e.getAntEtasjer());
                e.setAntEtasjer(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + e.getAntEtasjer());
                
                break;
            }
            case "Tomtestørrelse":{
                if(ny.equals(Integer.toString(e.getTomtAreal()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNHELTALL(), ny)){
                    output.setText("Feil - tomtestørrelse må kun inneholde heltall");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre tomtestørrelsen?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(e.getTomtAreal());
                e.setTomtAreal(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + e.getTomtAreal());
                
                break;
            }
            case "Kjeller":{
                if(ny.equals(Boolean.toString(e.getKjeller()))){
                    output.setText(felt + " allerede lik: " + (ny.equals("true") ? "ja":"nei"));
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre kjeller?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }

                gammel = Boolean.toString(e.getKjeller());
                e.setKjeller(Boolean.parseBoolean(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + (gammel.equals("true") ? "ja":"nei")
                + "\nNy:\t" + (e.getKjeller() ? "ja":"nei"));
                
                break;
            }
            case "Etasje":{
                if(ny.equals(Integer.toString(l.getEtasje()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre etasjen?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(l.getEtasje());
                l.setEtasje(Integer.parseInt(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + l.getEtasje());
                
                break;
            }
            case "Heis":{
                if(ny.equals(Boolean.toString(l.getHeis()))){
                    output.setText(felt + " allerede lik: " + (ny.equals("true") ? "ja":"nei"));
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre heis?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }

                gammel = Boolean.toString(l.getHeis());
                l.setHeis(Boolean.parseBoolean(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + (gammel.equals("true") ? "ja":"nei")
                + "\nNy:\t" + (l.getHeis() ? "ja":"nei"));
                
                break;
            }
            case "Balkong":{
                if(ny.equals(Boolean.toString(l.getBalkong()))){
                    output.setText(felt + " allerede lik: " + (ny.equals("true") ? "ja":"nei"));
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre balkong?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }

                gammel = Boolean.toString(l.getBalkong());
                l.setBalkong(Boolean.parseBoolean(ny));

                output.setText(felt + " endret"
                + "\nGammel:\t" + (gammel.equals("true") ? "ja":"nei")
                + "\nNy:\t" + (l.getBalkong() ? "ja":"nei"));
                
                break;
            }
        }
    }

    public void boligUtskrift() {
        String utskrift = "";
        UtleierMengde ul = StartVindu.getUtleierVindu().getUtleierMengde();
        Iterator<Utleier> iter = ul.getSortertMengde().iterator();
        BoligListe utleierSinBoligliste;
        while (iter.hasNext()) {
            utleierSinBoligliste = iter.next().getBoligliste();
            utskrift += utleierSinBoligliste.toString() + "\n";
        }
        output.setText(utskrift);
    }

    // blanker alle felter i vinduet
    public void blankFelter() {
        gateadresse.setText("");
        postnr.setText("");
        poststed.setText("");
        boligtype.setSelectedIndex(SELECTEDINDEX);
        areal.setText("");
        antRom.setSelectedIndex(SELECTEDINDEX);
        byggeaar.setText("");
        pris.setText("");
        avertertDato.setText(StartVindu.getDATOFORMAT());
        beskrivelse.setText("");
        etasjer.setSelectedIndex(SELECTEDINDEX);
        tomtestorrelse.setText("");
        kjeller.setSelected(false);
        etasje.setSelectedIndex(SELECTEDINDEX);
        heis.setSelected(false);
        balkong.setSelected(false);
        utleierFornavn.setText("");
        utleierEtternavn.setText("");
        output.setText("");
    }
    
    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        String valgtType = (String) boligtype.getSelectedItem();

        if (e.getSource() == regBolig) {
            if (valgtType.equals(TYPE[0])) {
                output.setText("Feil - du må velge type!");
            } else if (valgtType.equals(TYPE[1])) {
                regBolig(TYPE[1]);
            } else {
                regBolig(TYPE[2]);
            }
        } else if (e.getSource() == slettBolig) {
            slettBolig();
        } else if (e.getSource() == blankFelter) {
            blankFelter();
        } else if (e.getSource() == endreType){
            endreFelt("Boligtype", valgtType);
        } else if (e.getSource() == endreAreal){
            endreFelt("Areal", areal.getText());
        } else if (e.getSource() == endreRom){
            endreFelt("Soverom", (String)antRom.getSelectedItem());
        } else if (e.getSource() == endreByggeaar){
            endreFelt("Byggeår", byggeaar.getText());
        } else if (e.getSource() == endrePris){
            endreFelt("Leiepris", pris.getText());
        } else if (e.getSource() == endreDato){
            endreFelt("Avertert dato", avertertDato.getText());
        } else if (e.getSource() == endreBeskrivelse){
            endreFelt("Beskrivelse", beskrivelse.getText());
        } else if (e.getSource() == endreEtasjer){
            endreFelt("Etasjer", (String)etasjer.getSelectedItem());
        } else if (e.getSource() == endreTomtestorrelse){
            endreFelt("Tomtestørrelse", tomtestorrelse.getText());
        } else if (e.getSource() == endreKjeller){
            endreFelt("Kjeller", Boolean.toString(kjeller.isSelected()));
        } else if (e.getSource() == endreEtasje){
            endreFelt("Etasje", (String)etasje.getSelectedItem());
        } else if (e.getSource() == endreHeis){
            endreFelt("Heis", Boolean.toString(heis.isSelected()));
        } else if (e.getSource() == endreBalkong){
            endreFelt("Balkong", Boolean.toString(balkong.isSelected()));
        } else if (e.getSource() == boligtype) {
            // drop-down box

            BorderLayout layout = (BorderLayout) top.getLayout();

            if (valgtType.equals(TYPE[0])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    top.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
            } else if (valgtType.equals(TYPE[1])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    top.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
                top.add(eneboligPanel, BorderLayout.CENTER);
            } else if (valgtType.equals(TYPE[2])) {
                if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
                    top.remove(layout.getLayoutComponent(BorderLayout.CENTER));
                }
                top.add(leilighetPanel, BorderLayout.CENTER);
            }
            // refresh vinduet
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
    }
    
    public void focusGained(FocusEvent fe){
        // fungerer ikke hvis vi bruker "else-if" for flere */
    }

    public void focusLost(FocusEvent fe) {
        
        if(fe.getSource() == avertertDato)
            if(avertertDato.getText().equals(""))
                avertertDato.setText(StartVindu.getDATOFORMAT());
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        if(fe.getSource() == gateadresse || fe.getSource() == postnr || fe.getSource() == poststed)
            if(!gateadresse.getText().equals("") && !postnr.getText().equals("") && !poststed.getText().equals("") && StartVindu.kontrollerRegEx(StartVindu.getPATTERNPOSTNUMMER(), postnr.getText())){
                
                Bolig b = StartVindu.getUtleierVindu().getUtleierMengde().finnBolig(gateadresse.getText(), Integer.parseInt(postnr.getText()), poststed.getText());
                Utleier u = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleierViaBolig(b);
                EneboligRekkehus e = null;
                Leilighet l = null;

                if(b instanceof EneboligRekkehus)
                    e = (EneboligRekkehus)b;
                else
                    l = (Leilighet)b;
                
                if(b != null){
                    boligtype.setSelectedItem(b.getType());
                    areal.setText(Integer.toString(b.getAreal()));
                    antRom.setSelectedItem(Integer.toString(b.getSoverom()));
                    byggeaar.setText(Integer.toString(b.getByggeaar()));
                    pris.setText(Integer.toString(b.getPris()));
                    avertertDato.setText(StartVindu.getENKELDATOFORMAT().format(b.getDato()));
                    beskrivelse.setText(b.getBeskrivelse());
                    if(b instanceof EneboligRekkehus){
                        etasjer.setSelectedItem(Integer.toString(e.getAntEtasjer()));
                        tomtestorrelse.setText(Integer.toString(e.getTomtAreal()));
                        kjeller.setSelected(e.getKjeller());
                    }
                    else{
                        etasje.setSelectedItem(Integer.toString(l.getEtasje()));
                        heis.setSelected(l.getHeis());
                        balkong.setSelected(l.getBalkong());
                    }
                    utleierFornavn.setText(u.getFornavn());
                    utleierEtternavn.setText(u.getEtternavn());
                }
            }
    }
}
