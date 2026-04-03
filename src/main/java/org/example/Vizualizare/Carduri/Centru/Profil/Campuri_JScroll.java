package org.example.Vizualizare.Carduri.Centru.Profil;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.*;
import org.example.Interfete.I_FrontEnd;
import org.example.Interfete.I_Tematica;
import org.example.Model.Argon2_Alg;
import org.example.Model.UtilizatorComplet;

@Getter @Setter
public class Campuri_JScroll extends JPanel implements I_Tematica, ActionListener {
    //  Atribute
    private JLabel utilizatorEticheta, parolaEticheta, confirmareParolaEticheta, numeEticheta, prenumeEticheta, judetEticheta,
            emailEticheta, taraEticheta, localitateEticheta, stradaEticheta, numarEticheta, numarTelefonEticheta, rolEticheta;
    private JTextField utilizatorText, numeText, prenumeText, numarTelefonText, emailText, stradaText, numarText, rolText, taraText,
    judetText, localitateText;
    private JPasswordField parolaText, confirmareParolaText;
    private JComboBox<String> tariCombo, judeteCombo, localitateCombo;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv, stare;
    private I_FrontEnd interfataFrontEnd;
    private UtilizatorComplet utilizator;
    private int idLocuinta;

    // Constructor
    public Campuri_JScroll(I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                           boolean focusareButonArg, boolean focusareDesenButonArg) {
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        setLayout(new GridBagLayout());
        setInterfataFrontEnd(interfataFrontEnd);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), null);
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setRolEticheta(new JLabel("Rol: "));
        setUtilizatorEticheta(new JLabel("Nume de utilizator: "));
        setParolaEticheta(new JLabel("Parolă: "));
        setConfirmareParolaEticheta(new JLabel("Confirmare parolă: "));
        setNumeEticheta(new JLabel("Nume: "));
        setPrenumeEticheta(new JLabel("Prenume: "));
        setNumarTelefonEticheta(new JLabel("Număr de telefon: "));
        setEmailEticheta(new JLabel("Adresă de E-mail: "));
        setTaraEticheta(new JLabel("Țară: "));
        setJudetEticheta(new JLabel("Județ"));
        setLocalitateEticheta(new JLabel("Localitate: "));
        setStradaEticheta(new JLabel("Stradă: "));
        setNumarEticheta(new JLabel("Număr (locuință): "));

        setRolText(new JTextField(20));
        setUtilizatorText(new JTextField(20));
        setParolaText(new JPasswordField(20));
        setConfirmareParolaText( new JPasswordField(20));
        setNumeText(new JTextField(20));
        setPrenumeText(new JTextField(20));
        setNumarTelefonText(new JTextField(15));
        setEmailText(new JTextField(20));
        setStradaText(new JTextField(20));
        setNumarText(new JTextField(10));
        setTaraText(new JTextField(20));
        setJudetText(new JTextField(20));
        setLocalitateText(new JTextField(20));

        setTariCombo(new JComboBox<>());
        getTariCombo().setSelectedIndex(-1);
        getTariCombo().addActionListener(this);

        setJudeteCombo(new JComboBox<>());
        getJudeteCombo().setSelectedIndex(-1);
        getJudeteCombo().addActionListener(this);

        setLocalitateCombo(new JComboBox<>());
        getLocalitateCombo().setSelectedIndex(-1);
        getLocalitateCombo().addActionListener(this);


        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);

        getTariCombo().setFont(new Font(fontArg, tipTextArg, dimensiuneTextArg));
        getJudeteCombo().setFont(new Font(fontArg, tipTextArg, dimensiuneTextArg));
        getLocalitateCombo().setFont(new Font(fontArg, tipTextArg, dimensiuneTextArg));

        setStare(false);
        stareCampuri(getStare());
    }

    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();
        Insets insets = new Insets(5, 5, 5, 5);

        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = insets;


        gc.gridx = 0; gc.gridy = 0;
        add(rolEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(rolText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(utilizatorEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(utilizatorText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(parolaEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(parolaText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(confirmareParolaEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(confirmareParolaText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(numeEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(numeText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(prenumeEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(prenumeText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(numarTelefonEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(numarTelefonText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(emailEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(emailText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(taraEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(getTaraText(), gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(judetEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(getJudetText(), gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(localitateEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(getLocalitateText(), gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(stradaEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(stradaText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(numarEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(numarText, gc);
    }

    public void asezareElemente2() {
        GridBagConstraints gc = new GridBagConstraints();
        Insets insets = new Insets(5, 5, 5, 5);

        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = insets;


        gc.gridx = 0; gc.gridy = 0;
        add(rolEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(rolText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(utilizatorEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(utilizatorText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(parolaEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(parolaText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(confirmareParolaEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(confirmareParolaText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(numeEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(numeText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(prenumeEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(prenumeText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(numarTelefonEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(numarTelefonText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(emailEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(emailText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(taraEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(getTariCombo(), gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(judetEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(getJudeteCombo(), gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(localitateEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(getLocalitateCombo(), gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(stradaEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(stradaText, gc);

        gc.gridy++;
        gc.gridx = 0; gc.fill = GridBagConstraints.NONE;
        add(numarEticheta, gc);
        gc.gridx = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        add(numarText, gc);
    }

    public void primireDateUtilizator(UtilizatorComplet utilzator) {
        setUtilizator(utilzator);
        getRolText().setText(utilzator.getRol());
        getUtilizatorText().setText(utilzator.getNumeUtilizator());
        getParolaText().setText(utilzator.getParola());
        getConfirmareParolaText().setText(utilzator.getParola());
        getNumeText().setText(utilzator.getNumeFamilie());
        getPrenumeText().setText(utilzator.getPrenume());
        getNumarTelefonText().setText(utilzator.getNrTel());
        getEmailText().setText(utilzator.getAdresaEmail());
        getStradaText().setText(utilzator.getStrada());
        getNumarText().setText(Integer.toString(utilzator.getNrLocuinta()));
        getTaraText().setText(utilzator.getTara());
        getJudetText().setText(utilzator.getJudet());
        getLocalitateText().setText(utilzator.getLocalitate());
    }

    public void formatareTariCombo(ArrayList<String> setTari){
        getTariCombo().removeAllItems();
        for(String tara: setTari) {
            getTariCombo().addItem(tara);
        }
    }

    public void formatareJudeteCombo(ArrayList<String> setJudete) {
        getJudeteCombo().removeAllItems();
        for(String judet: setJudete)
            getJudeteCombo().addItem(judet);
    }

    public void formatareLocalitatiCombo(ArrayList<String> setLocalitati) {
        getLocalitateCombo().removeAllItems();
        for(String localitate: setLocalitati)
            getLocalitateCombo().addItem(localitate);
    }

    public void stareCampuri(boolean stare) {
        getRolText().setEditable(false);
        getRolText().setFocusable(false);
        getUtilizatorText().setEditable(stare);
        getParolaText().setEditable(stare);
        getConfirmareParolaText().setEditable(stare);
        getConfirmareParolaText().setVisible(stare);
        getConfirmareParolaEticheta().setVisible(stare);
        getNumeText().setEditable(stare);
        getPrenumeText().setEditable(stare);
        getNumarTelefonText().setEditable(stare);
        getEmailText().setEditable(stare);
        getStradaText().setEditable(stare);
        getNumarText().setEditable(stare);
        getTaraText().setEditable(stare);
        getJudetText().setEditable(stare);
        getLocalitateText().setEditable(stare);

        removeAll();
        if(stare) {
            asezareElemente2();
            if(interfataFrontEnd != null) {
                interfataFrontEnd.cerereTari();
            }
        } else {
            asezareElemente();
            if(utilizator != null)
                primireDateUtilizator(utilizator);
        }
        revalidate();
        repaint();
    }



    //  ZONA DE FUNCTII PT. VALIDARE DATE PROFIL

    private boolean verificareNume(String nume) {
        String regexNumeUtilizator = "^[A-Z][a-zA-Z]*";
        boolean stare = true;
        Pattern pattern = Pattern.compile(regexNumeUtilizator);
        Matcher matcher = pattern.matcher(nume);
        if(nume.length() < 4 || nume.length() > 15 || nume.equals("Nume utilizator") || !matcher.matches()) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Numele de utilizator nu respectă cerințele: între 4 și 15 caractere " +
                            "sau formatul majusculă + minuscule.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stare;
    }

    private boolean verificareAdresaEmail(String adresaEmail) {
        String regexAdresaEmail = "^[a-zA-Z0-9._+-]+@(gmail\\.com|yahoo\\.com|365\\.univ-ovidius\\.ro)$";
        boolean stare = true;
        Pattern pattern = Pattern.compile(regexAdresaEmail);
        Matcher matcher = pattern.matcher(adresaEmail);
        if(adresaEmail.length() < 4 || adresaEmail.length() > 50 || !matcher.matches()) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Adresa de E-mail nu respectă cerințele: între 3 și 50 caractere sau " +
                            "formulatul (majusculă/minusculă + " +
                            " cifre/cratimă/\'.\'/\'+\')@(gmail.com/yahoo.com/365.univ-ovidius.ro).", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stare;
    }

    private static boolean verificareDomeniu(String email) {
        String domeniu = email.substring(email.indexOf('@') + 1);
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext dirContext = new InitialDirContext(env);
            Attributes attributes = dirContext.getAttributes(domeniu, new String[] { "MX" });
            Attribute attribute = attributes.get("MX");
            return (attribute != null && attribute.size() > 0);
        } catch (NamingException e) {
            return false;
        }
    }

    private boolean verificareNrTel(String nrTel) {
        String regexNrTel = "^\\+40\\d{9}$";
        boolean stare = true;
        Pattern pattern = Pattern.compile(regexNrTel);
        Matcher matcher = pattern.matcher(nrTel);
        if(nrTel.length() != 12 || !matcher.matches()) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Numărul de telefon nu respectă cerințele: 12 caractere sau formatul " +
                            "+40(3 cifre)-(3 cifre)-(3 cifre).", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stare;
    }

    private boolean verificareParola(String parola, String confirmareParola) {
        String regexParola = "^[a-zA-Z0-9!@]+$";
        boolean stare = true;
        Pattern pattern = Pattern.compile(regexParola);
        Matcher matcher = pattern.matcher(parola);
        if(parola.length() < 3 || parola.length() > 10 || parola.equals("Parolă") || !matcher.matches()) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Parola nu respectă cerințele: între 3 și 10 caractere, inclusiv sau formatul " +
                            "(majusculă/minusculă + cifre + \'!\'/\'@\').", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (!parola.equals(confirmareParola)) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Cele două parole nu sunt identice.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    private boolean verificareNumeFamilie(String numeFamilie) {
        String regexNumeFamilie = "^[A-ZĂÂÎȘȚ][a-zA-ZăĂîÎâÂșȘțȚ\\s\\-]*$";
        boolean stare = true;
        Pattern pattern = Pattern.compile(regexNumeFamilie);
        Matcher matcher = pattern.matcher(numeFamilie);
        if(numeFamilie.length() < 4 || numeFamilie.length() > 30 || numeFamilie.equals("Nume") || !matcher.matches()) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Numele de familie nu respectă cerințele: între 4 și 40 caractere" +
                            " sau formatul majusculă + minusculă/spațiu/cratimă.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stare;
    }

    private boolean verificarePrenume(String prenume) {
        String regexPrenume = "^[A-ZĂÂÎȘȚ][a-zA-ZăĂîÎâÂșȘțȚ\\s\\-]*$";
        boolean stare = true;
        Pattern pattern = Pattern.compile(regexPrenume);
        Matcher matcher = pattern.matcher(prenume);
        if(prenume.length() < 4 || prenume.length() > 30 || prenume.equals("Prenume") || !matcher.matches()) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Prenumele nu respectă cerințele: între 4 și 30 caractere" +
                            " sau formatul majusculă + minusculă/spațiu/cratimă.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stare;
    }

    private boolean verificareNrLocuinta(int numar) {
        boolean stare = true;
        if (numar < 1 || numar > 100) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Numărul locuinței nu respectă cerințele: valoare între 1 și 100.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stare;
    }

    private boolean verificareStrada(String strada) {
        String regexStrada = "^[A-ZĂÂÎȘȚ][a-zA-ZăĂîÎâÂșȘțȚ\\s\\-]*$";
        boolean stare = true;
        Pattern pattern = Pattern.compile(regexStrada);
        Matcher matcher = pattern.matcher(strada);
        if(strada.length() < 3 || strada.length() > 50 || strada.equals("Stradă") || !matcher.matches()) {
            stare = false;
            JOptionPane.showMessageDialog(null,
                    "Strada nu respectă cerințele: între 3 și 50 caractere " +
                            "sau formatul majusculă + minusculă/spațiu/cratimă.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stare;
    }

    //  FINALUL ZONEI DE FUNCTII PT. VALIDARE DATE PROFIL



    //  ZONA DE FUNCTII PT. ACTUALIZARE PROFIL

    public void actualizareNume(String numeNou) {
        utilizator.setNumeUtilizator(numeNou);
        getNumeText().setText(utilizator.getNumeUtilizator());
    }

    public void actualizareParola(String parolaNoua) {
        utilizator.setParola(parolaNoua);
        getParolaText().setText(utilizator.getParola());
        getConfirmareParolaText().setText(utilizator.getParola());
    }

    public void actualizareAdresaEmail(String adresaEmailNoua) {
        utilizator.setAdresaEmail(adresaEmailNoua);
        getEmailText().setText(utilizator.getAdresaEmail());
    }

    public void actualizareNrTel(String nrTelNou) {
        utilizator.setNrTel(nrTelNou);
        getNumarTelefonText().setText(utilizator.getNrTel());
    }

    public void actualizareNumeFamilie(String numeFamilieNou) {
        utilizator.setNumeFamilie(numeFamilieNou);
        getNumeText().setText(utilizator.getNumeFamilie());
    }

    public void actualizarePrenume(String prenumeNou) {
        utilizator.setPrenume(prenumeNou);
        getPrenumeText().setText(utilizator.getPrenume());
    }

    public void actualizareNrLocuinta(int nrNou) {
        utilizator.setNrLocuinta(nrNou);
        getNumarText().setText(String.valueOf(utilizator.getNrLocuinta()));
    }

    public void actualizareStrada(String stradaNoua) {
        utilizator.setStrada(stradaNoua);
        getStradaText().setText(utilizator.getStrada());
    }

    public void actualizareLocalitate(String localitateNoua) {
        utilizator.setLocalitate(localitateNoua);
        getLocalitateText().setText(utilizator.getLocalitate());
    }

    public void actualizareJudet(String judetNou) {
        utilizator.setJudet(judetNou);
        getJudetText().setText(utilizator.getJudet());
    }

    public void actualizareIdLocuinta(int id) {
        this.idLocuinta = id;
    }

    public void actualizareDateProfil() {
        Argon2_Alg argon2Alg = new Argon2_Alg();
        String tara = (String) getTariCombo().getSelectedItem(),
                judet = (String) getJudeteCombo().getSelectedItem(),
                localitate = (String) getLocalitateCombo().getSelectedItem(),
                nume_utilizator =  getUtilizatorText().getText(),
                parola_utilizator = new String(getParolaText().getPassword()),
                confirmare_parola_utilizator = new String(getConfirmareParolaText().getPassword()),
                nume_familie =  getNumeText().getText(),
                prenume =  getPrenumeText().getText(),
                nr_tel = getNumarTelefonText().getText(),
                adresa_email = getEmailText().getText(),
                strada = getStradaText().getText();
        int idUtilizator = getUtilizator().getIdUtilizator(),
                numar_locuinta = 0;

        try {
            numar_locuinta = Integer.parseInt(getNumarText().getText());
        } catch (Exception eroare) {
            JOptionPane.showMessageDialog(null, "Trebuie să introduceți un număr în câmpul destinat numărului locuinței.",
                    "Eroare", JOptionPane.ERROR_MESSAGE);
        }

        if (!nume_utilizator.equals(utilizator.getNumeUtilizator()) && verificareNume(nume_utilizator)) {
            interfataFrontEnd.actualizareNume(idUtilizator, nume_utilizator);
        }

        if(!adresa_email.equals(utilizator.getAdresaEmail()) && verificareAdresaEmail(adresa_email) && verificareDomeniu(adresa_email)) {
            interfataFrontEnd.actualizareAdresaEmail(idUtilizator, adresa_email);
        }

        if(!nr_tel.equals(utilizator.getNrTel()) && verificareNrTel(nr_tel)) {
            interfataFrontEnd.actualizareNrTel(idUtilizator, nr_tel);
        }

        if(!parola_utilizator.equals(utilizator.getParola()) || !confirmare_parola_utilizator.equals(utilizator.getParola())) {
            if(verificareParola(parola_utilizator, confirmare_parola_utilizator)) {
                if(!argon2Alg.verificareParola(parola_utilizator, utilizator.getParola())
                        && !argon2Alg.verificareParola(confirmare_parola_utilizator, utilizator.getParola())) {
                    interfataFrontEnd.actualizareParola(idUtilizator, parola_utilizator);
                }
            }
        }

        if(!nume_familie.equals(utilizator.getNumeFamilie()) || !prenume.equals(utilizator.getPrenume())) {
            if(verificareNumeFamilie(nume_familie) && verificarePrenume(prenume)) {
                interfataFrontEnd.actualizareNumeFamilie_Prenume(idUtilizator, nume_familie, prenume);
            }
        }

        if(numar_locuinta != utilizator.getNrLocuinta() && verificareNrLocuinta(numar_locuinta) && numar_locuinta != 0){
            interfataFrontEnd.actualizareNrLocuinta(idUtilizator, numar_locuinta);
        }

        if(!strada.equals(utilizator.getStrada()) && verificareStrada(strada)) {
            interfataFrontEnd.actualizareStrada(idUtilizator, strada);
        }

        if(!localitate.equals(utilizator.getLocalitate())) {
            interfataFrontEnd.actualizareLocalitate(idUtilizator, localitate);
        }

        if(!judet.equals(utilizator.getJudet())) {
            interfataFrontEnd.actualizareJudet(idUtilizator, judet);
        }
    }

    //  FINALUL ZONEI DE FUNCTII PT UPDATE PROFIL

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        this.setBackground(culoare);
        Component[] componente = this.getComponents();
        for(Component componenta : componente) {
            if(componenta instanceof JLabel eticheta)
                eticheta.setOpaque(true);
            if(!(componenta instanceof JTextField))
                componenta.setBackground(culoare);
        }
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.decode(codHex);
        setCuloareComponentaSv(culoare);
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), null);
    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        this.setBorder(null);
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }

    public boolean getStare() {return stare;}


    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JComboBox<?> selectat) {
            if (selectat == getTariCombo()) {
                String tara = (String) tariCombo.getSelectedItem();
                if (interfataFrontEnd != null)
                    interfataFrontEnd.taraSelectata(tara);
            }
            if (selectat == getJudeteCombo()) {
                String judet = (String) judeteCombo.getSelectedItem();
                if (interfataFrontEnd != null)
                    interfataFrontEnd.judetSelectat(judet);
            }
        }
    }
}
