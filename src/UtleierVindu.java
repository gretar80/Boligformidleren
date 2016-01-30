/*
 * Innhold: Vindu som brukes for registrering, slettning og endring av utleiere
 * Sist oppdatert: 16.05.2014, 15:00.
 * Programmert av: Gretar
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class UtleierVindu extends JFrame implements ActionListener, FocusListener {

    private JTextField RegPersFornavn, RegPersEtternavn, RegPersGateadr, RegPersPostnr, RegPersPoststed, RegEpost, RegTlf, RegFirma;
    private JTextArea output;
    private JButton regUtleier, slettUtleier, endreGateadresse, endrePostnr, endrePoststed, endreEpost, endreTelefonnr, endreFirma, blankFelter;
    private JPanel masterPanel, grid, under;
    private final int ANTRAD = 10, ANTKOL = 3, GAP = 5, BREDDE = 400, HOYDE = 450, FELTLENGDE = 10;

    private UtleierMengde utleierMengde;

    // konstruktør
    public UtleierVindu() {
        super("Utleier");

        utleierMengde = new UtleierMengde();

        masterPanel = new JPanel(new BorderLayout());
        grid = new JPanel(new GridLayout(ANTRAD, ANTKOL, GAP, GAP));
        under = new JPanel(new BorderLayout());
        masterPanel.add(grid, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        this.getContentPane().add(masterPanel);
        setSize(BREDDE, HOYDE);

        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);
        under.add(scroll, BorderLayout.CENTER);

        // felt og knapper
        grid.add(new JLabel("Fornavn: "));
        RegPersFornavn = new JTextField(FELTLENGDE);
        RegPersFornavn.addFocusListener(this);
        grid.add(RegPersFornavn);
        grid.add(new JLabel(""));   // tom felt, ingen knapp for endring av fornavn

        grid.add(new JLabel("Etternavn: "));
        RegPersEtternavn = new JTextField(FELTLENGDE);
        RegPersEtternavn.addFocusListener(this);
        grid.add(RegPersEtternavn);
        grid.add(new JLabel(""));   // tom felt, ingen knapp for endring av etternavn

        grid.add(new JLabel("Gateadresse: "));
        RegPersGateadr = new JTextField(FELTLENGDE);
        grid.add(RegPersGateadr);
        
        endreGateadresse = new JButton("Endre");
        endreGateadresse.addActionListener(this);
        grid.add(endreGateadresse);
        
        grid.add(new JLabel("Postnummer: "));
        RegPersPostnr = new JTextField(FELTLENGDE);
        grid.add(RegPersPostnr);
        
        endrePostnr = new JButton("Endre");
        endrePostnr.addActionListener(this);
        grid.add(endrePostnr);

        grid.add(new JLabel("Poststed: "));
        RegPersPoststed = new JTextField(FELTLENGDE);
        grid.add(RegPersPoststed);
        
        endrePoststed = new JButton("Endre");
        endrePoststed.addActionListener(this);
        grid.add(endrePoststed);

        grid.add(new JLabel("E-post: "));
        RegEpost = new JTextField(FELTLENGDE);
        grid.add(RegEpost);
        
        endreEpost = new JButton("Endre");
        endreEpost.addActionListener(this);
        grid.add(endreEpost);

        grid.add(new JLabel("Telefonnummer: "));
        RegTlf = new JTextField(FELTLENGDE);
        grid.add(RegTlf);
        
        endreTelefonnr = new JButton("Endre");
        endreTelefonnr.addActionListener(this);
        grid.add(endreTelefonnr);

        grid.add(new JLabel("Firma: "));
        RegFirma = new JTextField(FELTLENGDE);
        grid.add(RegFirma);
        
        endreFirma = new JButton("Endre");
        endreFirma.addActionListener(this);
        grid.add(endreFirma);

        // tom rad
        grid.add(new JLabel(""));
        grid.add(new JLabel(""));
        grid.add(new JLabel(""));
        
        regUtleier = new JButton("Register utleier");
        regUtleier.addActionListener(this);
        grid.add(regUtleier);

        slettUtleier = new JButton("Slett utleier");
        slettUtleier.addActionListener(this);
        grid.add(slettUtleier);

        blankFelter = new JButton("Blank felter");
        blankFelter.addActionListener(this);
        grid.add(blankFelter);

        lesUtleierFraFil();
    }

    // get metode
    public UtleierMengde getUtleierMengde() {
        return utleierMengde;
    }

    //registrer utleier
    public void regUtleier() {

        if(!regexOK()){
            return;
        }

        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();

        if (utleierMengde.finnUtleier(fornavn, etternavn) != null) {
            output.setText("Feil - Utleier allerede registrert!");
            return;
        }

        Utleier u = new Utleier(fornavn, etternavn, RegPersGateadr.getText(), 
                Integer.parseInt(RegPersPostnr.getText()), RegPersPoststed.getText(),
                RegEpost.getText(), Integer.parseInt(RegTlf.getText()), RegFirma.getText());

        utleierMengde.settInn(u);
        blankFelter();
        output.setText("Utleier " + fornavn + " " + etternavn + " registrert");
        
    }
    
    // sletter utleireren hvis brukeren har svart bekreftende på et kontrollspørsmål og utleieren har ikke noen boliger til utleie
    public void slettUtleier() {
        
        String fornavn = RegPersFornavn.getText();
        String etternavn = RegPersEtternavn.getText();
        Utleier ul = utleierMengde.finnUtleier(fornavn, etternavn);
        
        if (ul == null) {
            output.setText("Utleier " + fornavn + " " + etternavn + " ble ikke funnet");
            return;
        }
        
        if(StartVindu.visJaNeiMelding("Vil du slette utleieren?", "Slett utleier").equals("Nei")){
            return;
        }
        
        if (!utleierMengde.fjern(ul)){
            output.setText("Feil - utleier har boliger til utleie");
            return;
        }

        blankFelter();
        output.setText("Utleier " + fornavn + " " + etternavn + " slettet");
    }

    // blanker alle feltene
    public void blankFelter() {
        RegPersFornavn.setText("");
        RegPersEtternavn.setText("");
        RegPersGateadr.setText("");
        RegPersPostnr.setText("");
        RegPersPoststed.setText("");
        RegEpost.setText("");
        RegTlf.setText("");
        RegFirma.setText("");
        output.setText("");
    }
    
    // sjekker regular expression på alle feltene
    public boolean regexOK(){
        // Kontrollerer alle felt ved RegEx for å unngå exceptions (f.eks parseException, NullPointerException, osv.)
        // fornavn
        if(RegPersFornavn.getText().equals("")){
            output.setText("Feil - du må fylle inn fornavn");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNBOKSTAV(), RegPersFornavn.getText()))){
                output.setText("Feil - du må kun bruke norske bokstaver (min. 2 tegn) i fornavn");
                return false;
            }
        }
        // etternavn
        if(RegPersEtternavn.getText().equals("")){
            output.setText("Feil - du må fylle inn etternavn");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNBOKSTAV(), RegPersEtternavn.getText()))){
                output.setText("Feil - du må kun bruke norske bokstaver (min. 2 tegn) i etternavn");
                return false;
            }
        }
        // gateadresse
        if(RegPersGateadr.getText().equals("")){
            output.setText("Feil - du må fylle inn gateadresse");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNTALLBOKSTAV(), RegPersGateadr.getText()))){
                output.setText("Feil - du må kun bruke bokstaver (min. 4 tegn)\nog heltall (1-3 sifre) i gateadresse");
                return false;
            }
        }
        // postnummer
        if(RegPersPostnr.getText().equals("")){
            output.setText("Feil - du må fylle inn postnummer");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNPOSTNUMMER(), RegPersPostnr.getText()))){
                output.setText("Feil - du må kun bruke heltall (4 sifre) i postnummer");
                return false;
            }
        }
        // poststed
        if(RegPersPoststed.getText().equals("")){
            output.setText("Feil - du må fylle inn poststed");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNBOKSTAV(), RegPersPoststed.getText()))){
                output.setText("Feil - du må kun bruke bokstaver (min. 2 tegn) i poststed");
                return false;
            }
        }
        // epost
        if(RegEpost.getText().equals("")){
            output.setText("Feil - du må fylle inn epost");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNEPOST(), RegEpost.getText()))){
                output.setText("Feil - epost må være på format\n'aaa@bbb.ccc' (ikke norsk bokstaver)");
                return false;
            }
        }
        // telefonnummer
        if(RegTlf.getText().equals("")){
            output.setText("Feil - du må fylle inn telefonnummer");
            return false;
        }
        else{
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNTELEFONNUMMER(), RegTlf.getText()))){
                output.setText("Feil - telefonnummer må inneholde 8 sifre");
                return false;
            }
        }
        // firma
        if(!RegFirma.getText().equals("")){
            if(!(StartVindu.kontrollerRegEx(StartVindu.getPATTERNTALLELLERBOKSTAV(), RegFirma.getText()))){
                output.setText("Feil - firma må kun inneholde norkse bokstaver og/eller heltall");
                return false;
            }
        }
        return true;
    }

    // metoden endrer en felt for utleieren
    public void endreFelt(String felt, String ny){
        if(ny.equals("")){
            output.setText("Feil - du må skrive noe i feltet");
            return;
        }
        
        Utleier ul = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(RegPersFornavn.getText(), RegPersEtternavn.getText());
        
        if(ul == null){
            output.setText("Finner ikke utleier");
            return;
        }
        
        String gammel = "";
        
        switch(felt){
            case "Gateadresse":{ 
                if(ny.equals(ul.getGateadresse())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNTALLBOKSTAV(), ny)){
                    output.setText("Feil - du må kun bruke bokstaver (min. 4 tegn)\nog heltall (1-3 sifre) i gateadresse");
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre gateadressen?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = ul.getGateadresse();
                ul.setGateadresse(ny);

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getGateadresse());
                
                break;
            }
            case "Postnummer":{
                if(ny.equals(Integer.toString(ul.getPostnr()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNPOSTNUMMER(), ny)){
                    output.setText("Feil - du må kun bruke heltall (4 sifre) i postnummer");
                    return;
                }
                
                // ja-nei 
                String svar = StartVindu.visJaNeiMelding( "Vil du endre postnummeret?", "Endring av data");
                if ( svar.equals("Nei")){
                    output.setText(felt + " ikke endret");
                    return;
                }
                
                gammel = Integer.toString(ul.getPostnr());
                ul.setPostnr(Integer.parseInt(ny));
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getPostnr());
                
                break;
            }
            case "Poststed":{
                if(ny.equals(ul.getPoststed())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNBOKSTAV(), ny)){
                    output.setText("Feil - du må kun bruke bokstaver (min. 2 tegn) i poststed");
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre poststedet?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = ul.getPoststed();
                ul.setPoststed(ny);
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getPoststed());
                
                break;
            }
            case "Epost":{
                if(ny.equals(ul.getEpost())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNEPOST(), ny)){
                    output.setText("Feil - epost må være på format\n'aaa@bbb.ccc' (ikke norsk bokstaver)");
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre eposten?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = ul.getEpost();
                ul.setEpost(ny);
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getEpost());
                
                break;
            }
            case "Telefonnummer":{
                if(ny.equals(Integer.toString(ul.getTelefonnr()))){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNTELEFONNUMMER(), ny)){
                    output.setText("Feil - telefonnummer må inneholde 8 sifre");
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre telefonnummeret?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = Integer.toString(ul.getTelefonnr());
                ul.setTelefonnr(Integer.parseInt(ny));
                
                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getTelefonnr());
                
                break;
            }
            case "Firma":{
                if(ny.equals(ul.getFirma())){
                    output.setText(felt + " allerede lik: " + ny);
                    return;
                }
                
                // regex
                if(!StartVindu.kontrollerRegEx(StartVindu.getPATTERNTALLELLERBOKSTAV(), ny)){
                    output.setText("Feil - firma må kun inneholde bokstaver og/eller heltall");
                    return;
                }
                
                // ja-nei 
                if(StartVindu.visJaNeiMelding( "Vil du endre firmaet?", "Endring av data").equals("Nei"))
                    return;
                
                gammel = ul.getFirma();
                ul.setFirma(ny);

                output.setText(felt + " endret"
                + "\nGammel:\t" + gammel 
                + "\nNy:\t" + ul.getFirma());
                
                break;
            }
        }
    }

    public void skrivUtleierTilFil() {
        try (ObjectOutputStream utfil = new ObjectOutputStream(
                new FileOutputStream("utleiermengde.data"))) {
            utfil.writeObject(utleierMengde.kopierMengdeUsortert());
        } catch (NotSerializableException nse) {
            StartVindu.visFeilmelding(nse);
        } catch (IOException e) {
            StartVindu.visFeilmelding(e);
        }
    }

    public void lesUtleierFraFil() {
        Set<Utleier> innlestUtleiere = new TreeSet<>();
        try (ObjectInputStream innfil = new ObjectInputStream(
                new FileInputStream("utleiermengde.data"))) {
            innlestUtleiere = (TreeSet<Utleier>) innfil.readObject();
            Iterator<Utleier> iter = innlestUtleiere.iterator();
            while (iter.hasNext()) {
                utleierMengde.settInn(iter.next());
            }
        } catch (ClassNotFoundException cnfe) {
            StartVindu.visFeilmelding(cnfe);
        } catch (FileNotFoundException fnfe) {
            StartVindu.visFeilmelding(fnfe);
        } catch (IOException e) {
            StartVindu.visFeilmelding(e);
        }
    }

    // Lyttemetode
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regUtleier) {
            regUtleier();
        } else if (e.getSource() == blankFelter) {
            blankFelter();
        } else if (e.getSource() == slettUtleier) {
            slettUtleier();
        } else if (e.getSource() == endreGateadresse){
            endreFelt("Gateadresse", RegPersGateadr.getText());
        } else if (e.getSource() == endrePostnr){
            endreFelt("Postnummer", RegPersPostnr.getText());
        } else if (e.getSource() == endrePoststed){
            endreFelt("Poststed", RegPersPoststed.getText());
        } else if (e.getSource() == endreEpost){
            endreFelt("Epost", RegEpost.getText());
        } else if (e.getSource() == endreTelefonnr){
            endreFelt("Telefonnummer", RegTlf.getText());
        } else if (e.getSource() == endreFirma){
            endreFelt("Firma", RegFirma.getText());
        }
    }
    
    public void focusGained(FocusEvent fe){
        
        // fungerer ikke hvis vi bruker "else-if" for flere felter
        
    }
    
    public void focusLost(FocusEvent fe) {
        
        if(fe.getSource() == RegPersFornavn || fe.getSource() == RegPersEtternavn)
            if(!RegPersFornavn.getText().equals("") && !RegPersEtternavn.getText().equals("")){
                Utleier ul = StartVindu.getUtleierVindu().getUtleierMengde().finnUtleier(RegPersFornavn.getText(), RegPersEtternavn.getText());
                
                if(ul != null){
                    RegPersGateadr.setText(ul.getGateadresse());
                    RegPersPostnr.setText(Integer.toString(ul.getPostnr()));
                    RegPersPoststed.setText(ul.getPoststed());
                    RegEpost.setText(ul.getEpost());
                    RegTlf.setText(Integer.toString(ul.getTelefonnr()));
                    RegFirma.setText(ul.getFirma());
                }
            }
        // fungerer ikke hvis vi bruker "else-if" for flere felter
    }

}
