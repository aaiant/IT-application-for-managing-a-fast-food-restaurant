package org.example.Vizualizare.Carduri.Vest.Inregistrare;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_FrontEnd;

@Getter @Setter
public class Card_Inregistrare extends JPanel implements ActionListener, I_Tematica {
    private JLabel titluEticheta, conectareMeniuEticheta, autorEticheta, taraEticheta, utilizatorEticheta,
            parolaEticheta, judetEticheta, localitateEticheta, numarTelefonEticheta, adresaEmailEticheta,
            stradaEticheta, numarEticheta, numeFamilieEticheta, prenumeEticheta, confirmareParolaEticheta;
    private JTextField utilizatorText, numarTelefonText, adresaEmailText, stradaText, numarText,
    numeFamilieText, prenumeText;
    private JPasswordField parolaCamp, confirmareParolaCamp;
    private JButton inregistrareButon, conectareButon;
    private JComboBox<String> tariCombo, judeteCombo, localiatetCombo;
    private Border borduraInterioara, borduraExterioara;
    private I_Carduri interfata;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;

    //  Constructor
    public Card_Inregistrare(I_Carduri arg, I_FrontEnd interfataFrontEnd,
                             I_BtnActiuni interfataBtnActiuni, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg,
                             Color culoareButonArg, boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfata(arg);
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        configurari("Inregistrare", "Meniul de înregistrare");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    //  Metode
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        setTitluEticheta(new JLabel("Înregistrare"));
        setUtilizatorEticheta(new JLabel("Nume de utilizator"));
        setParolaEticheta(new JLabel("Parolă"));
        setConfirmareParolaEticheta(new JLabel("Confirmare parolă"));
        setNumeFamilieEticheta(new JLabel("Nume de familie"));
        setPrenumeEticheta(new JLabel("Prenume"));
        setNumarTelefonEticheta(new JLabel("Număr de telefon"));
        setAdresaEmailEticheta(new JLabel("Adresă de E-mail"));
        setTaraEticheta(new JLabel("Țară"));
        setJudetEticheta(new JLabel("Județ: "));
        setLocalitateEticheta(new JLabel("Localitate"));
        setStradaEticheta(new JLabel("Stradă"));
        setNumarEticheta(new JLabel("Număr (locuință)"));
        setConectareMeniuEticheta(new JLabel("Meniu conectare"));
        setAutorEticheta(new JLabel("#Autor: Antohi Andi-Ionel"));

        setTariCombo(new JComboBox<>());
        getTariCombo().setSelectedIndex(-1);
        getTariCombo().addActionListener(this);

        setJudeteCombo(new JComboBox<>());
        getJudeteCombo().setSelectedIndex(-1);
        getJudeteCombo().addActionListener(this);

        setLocaliatetCombo(new JComboBox<>());
        getLocaliatetCombo().setSelectedIndex(-1);
        getLocaliatetCombo().addActionListener(this);

        setUtilizatorText(new JTextField(20));
        String phNumeUtilizator = "Nume utilizator";
        getUtilizatorText().setText(phNumeUtilizator);
        getUtilizatorText().setForeground(Color.GRAY);

        setParolaCamp(new JPasswordField(20));

        setConfirmareParolaCamp(new JPasswordField(20));

        setNumeFamilieText(new JTextField(20));
        String phNumeFamilie = "Nume";
        getNumeFamilieText().setText(phNumeFamilie);
        getNumeFamilieText().setForeground(Color.GRAY);

        setPrenumeText(new JTextField(20));
        String phPrenume = "Prenume";
        getPrenumeText().setText(phPrenume);
        getPrenumeText().setForeground(Color.GRAY);

        setNumarTelefonText(new JTextField(20));
        String phNrTel = "Nr. Tel";
        getNumarTelefonText().setText(phNrTel);
        getNumarTelefonText().setForeground(Color.GRAY);

        setAdresaEmailText(new JTextField(20));
        String phEmail = "E-mail";
        getAdresaEmailText().setText(phEmail);
        getAdresaEmailText().setForeground(Color.GRAY);

        setStradaText(new JTextField(15));
        String phStrada = "Stradă";
        getStradaText().setText(phStrada);
        getStradaText().setForeground(Color.GRAY);

        setNumarText(new JTextField(20));
        String phNrLoc = "Nr. locuință";
        getNumarText().setText(phNrLoc);
        getNumarText().setForeground(Color.GRAY);

        setConectareButon(new JButton("Meniu Conectare")); getConectareButon().addActionListener(this);
        setInregistrareButon(new JButton("Înregistrare")); getInregistrareButon().addActionListener(this);

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);
    }

    public void golireCampuri() {
        getUtilizatorText().setText("Nume utilizator");
        getNumarTelefonText().setText("Nr. Tel");
        getAdresaEmailText().setText("E-mail");
        getStradaText().setText("Stradă");
        getNumarText().setText("Nr. locuință");
        getNumeFamilieText().setText("Nume");
        getPrenumeText().setText("Prenume");
        getParolaCamp().setText("");
        getConfirmareParolaCamp().setText("");
    }

    public static boolean verificareDomeniu(String email) {
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

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/CardVest/" + urlTitluIcon + ".png")).append("\">");
        sb.append(String.format("&nbsp;%s", textIcon));
        sb.append("</html>");
        Dimension dimensiune = getPreferredSize();
        dimensiune.width = 600;
        setPreferredSize(dimensiune);
        setLayout(new GridBagLayout());
        setBorduraInterioara(BorderFactory.createBevelBorder(1, null, Color.WHITE, null, null));
        setBorduraExterioara(BorderFactory.createTitledBorder(sb.toString()));
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        setBorder(BorderFactory.createCompoundBorder(borduraExterioara, borduraInterioara));
    }

    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        int est = GridBagConstraints.EAST;
        int sfarsit = GridBagConstraints.LINE_END;
        int gol = GridBagConstraints.NONE;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int centru = GridBagConstraints.CENTER;

        Insets insets = new Insets(5, 5, 5, 5);

        gc.fill = gol;
        gc.gridy = 0;
        gc.gridx = 0;
        add(getUtilizatorEticheta(), gc);

        gc.gridx = 1;
        gc.fill = orizontal;
        gc.weightx = 1;
        add(getUtilizatorText(), gc);

        gc.fill = orizontal;
        gc.gridx = 2;
        gc.weightx = 1;
        add(getParolaCamp(), gc);

        gc.fill = gol;
        gc.gridx = 3;
        add(getParolaEticheta(), gc);


        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0;
        add(getNumeFamilieEticheta(), gc);

        gc.fill = orizontal;
        gc.gridx = 1;
        gc.weightx = 1;
        add(getNumeFamilieText(), gc);

        gc.fill = orizontal;
        gc.gridx = 2;
        gc.weightx = 1;
        add(getConfirmareParolaCamp(), gc);

        gc.fill = gol;
        gc.gridx = 3;
        add(getConfirmareParolaEticheta(), gc);


        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0;
        add(getPrenumeEticheta(), gc);

        gc.fill = orizontal;
        gc.gridx = 1;
        gc.weightx = 1;
        add(getPrenumeText(), gc);

        gc.fill = orizontal;
        gc.gridx = 2;
        gc.weightx = 1;
        add(getNumarTelefonText(), gc);

        gc.fill = gol;
        gc.gridx = 3;
        add(getNumarTelefonEticheta(), gc);


        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0;
        add(getTaraEticheta(), gc);

        gc.fill = orizontal;
        gc.gridx = 1;
        gc.weightx = 1;
        add(getTariCombo(), gc);

        gc.fill = orizontal;
        gc.gridx = 2;
        gc.weightx = 1;
        add(getAdresaEmailText(), gc);

        gc.fill = gol;
        gc.gridx = 3;
        add(getAdresaEmailEticheta(), gc);


        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0;
        add(getJudetEticheta(), gc);

        gc.fill = orizontal;
        gc.gridx = 1;
        add(getJudeteCombo(), gc);

        gc.fill = orizontal;
        gc.gridx = 2;
        gc.weightx = 1;
        add(getNumarText(), gc);

        gc.fill = gol;
        gc.gridx = 3;
        add(getNumarEticheta(), gc);

        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 1;
        add(getLocalitateEticheta(), gc);

        gc.fill = orizontal;
        gc.gridx = 1;
        gc.weightx = 1;
        add(getLocaliatetCombo(), gc);

        gc.fill = orizontal;
        gc.gridx = 2;
        gc.weightx = 1;
        add(getStradaText(), gc);

        gc.fill = gol;
        gc.gridx = 3;
        add(getStradaEticheta(), gc);

        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 1;
        gc.weightx = 1;
        gc.insets = new Insets(2, 0, 2, 0);
        add(getConectareButon(), gc);

        gc.gridx = 2;
        gc.weightx = 1;
        gc.insets = new Insets(2, 0, 2, 0);
        add(getInregistrareButon(), gc);

        gc.gridy++;
        gc.gridx = 1;
        gc.gridwidth = 2;
        gc.anchor = centru;
        gc.weightx = 1;
        add(getAutorEticheta(), gc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == conectareButon) {
                interfataBtnActiuni.meniuConectare(true);
                interfataBtnActiuni.meniuIntroducere(true);
                interfataBtnActiuni.semnalCardInregistrare(false);
                interfataBtnActiuni.semnalCardLogare(true);
            } else if(selectat == getInregistrareButon()) {
                boolean stareObiect = true;
                try {
                    String tara = (String) getTariCombo().getSelectedItem(),
                            judet = (String) getJudeteCombo().getSelectedItem(),
                            localitate = (String) getLocaliatetCombo().getSelectedItem(),
                            numeUtilizator = (String) getUtilizatorText().getText(),
                            parola = new String(getParolaCamp().getPassword()),
                            confirmareParola = new String(getConfirmareParolaCamp().getPassword()),
                            numeFamilie = (String) getNumeFamilieText().getText(),
                            prenume = (String) getPrenumeText().getText(),
                            nrTel = (String) getNumarTelefonText().getText(),
                            adresaEmail = (String) getAdresaEmailText().getText(),
                            strada = (String) getStradaText().getText(),
                            nrLocuintaTxt = (String) getNumarText().getText(),
                            regexStrada = "^[A-ZĂÂÎȘȚ][a-zA-ZăĂîÎâÂșȘțȚ\\s\\-]*$",
                            regexAdresaEmail = "^[a-zA-Z0-9._+-]+@(gmail\\.com|yahoo.com|365\\.univ-ovidius\\.ro)$",
                            regexNrTel = "^\\+40\\d{9}$",
                            regexParola = "^[a-zA-Z0-9!@]+$",
                            regexNumeFamilie = "^[A-ZĂÂÎȘȚ][a-zA-ZăĂîÎâÂșȘțȚ\\s\\-]*$",
                            regexPrenume = "^[A-ZĂÂÎȘȚ][a-zA-ZăĂîÎâÂșȘțȚ\\s\\-]*$",
                            regexNumeUtilizator = "^[A-Z][a-zA-Z]*";

                    int nrLocuinta = 0;
                    if(nrLocuintaTxt == null) {
                        JOptionPane.showMessageDialog(null,
                                "Trebuie să completați numărul locuinței!", "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        nrLocuinta = Integer.parseInt(nrLocuintaTxt);
                        if (nrLocuinta < 1 || nrLocuinta > 100) {
                            stareObiect = false;
                            JOptionPane.showMessageDialog(null,
                                    "Numărul locuinței nu respectă cerințele: valoare între 1 și 100.", "Eroare",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    Pattern pattern = Pattern.compile(regexStrada);
                    Matcher matcher = pattern.matcher(strada);
                    if(strada.length() < 3 || strada.length() > 50 || strada.equals("Stradă") || !matcher.matches()) {
                        stareObiect = false;
                        JOptionPane.showMessageDialog(null,
                                "Strada nu respectă cerințele: între 3 și 50 caractere / format.", "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    pattern = Pattern.compile(regexAdresaEmail);
                    matcher = pattern.matcher(adresaEmail);
                    if(adresaEmail.length() < 4 || adresaEmail.length() > 50 || !matcher.matches() || !verificareDomeniu(adresaEmail)) {
                        stareObiect = false;
                        JOptionPane.showMessageDialog(null,
                                "Adresa de E-mail nu respectă cerințele: între 3 și 50 caractere / format.", "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    pattern = Pattern.compile(regexNrTel);
                    matcher = pattern.matcher(nrTel);
                    if(nrTel.length() != 12 || !matcher.matches()) {
                        stareObiect = false;
                        JOptionPane.showMessageDialog(null,
                                "Numărul de telefon nu respectă cerințele: 12 caractere / format.", "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    pattern = Pattern.compile(regexParola);
                    matcher = pattern.matcher(parola);
                    if(parola.length() < 3 || parola.length() > 10 || parola.equals("Parolă") || !matcher.matches()) {
                        stareObiect = false;
                        JOptionPane.showMessageDialog(null,
                                "Parola nu respectă cerințele: între 3 și 10 caractere, inclusiv / format.", "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    if (!parola.equals(confirmareParola)) {
                        stareObiect = false;
                        JOptionPane.showMessageDialog(null,
                                "Cele două parole nu sunt identice.", "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    pattern = Pattern.compile(regexNumeFamilie);
                    matcher = pattern.matcher(numeFamilie);
                    if(numeFamilie.length() < 4 || numeFamilie.length() > 30 || numeFamilie.equals("Nume") || !matcher.matches()) {
                        stareObiect = false;
                        JOptionPane.showMessageDialog(null,
                                "Numele de familie nu respectă cerințele: între 4 și 40 caractere / format.", "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    pattern = Pattern.compile(regexPrenume);
                    matcher = pattern.matcher(prenume);
                    if(prenume.length() < 4 || prenume.length() > 30 || prenume.equals("Prenume") || !matcher.matches()) {
                        stareObiect = false;
                        JOptionPane.showMessageDialog(null,
                                "Prenumele nu respectă cerințele: între 4 și 30 caractere, inclusiv / format.", "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    pattern = Pattern.compile(regexNumeUtilizator);
                    matcher = pattern.matcher(numeUtilizator);
                    if(numeUtilizator.length() < 4 || numeUtilizator.length() > 15 || numeUtilizator.equals("Nume utilizator") || !matcher.matches()) {
                        stareObiect = false;
                        JOptionPane.showMessageDialog(null,
                                "Numele de utilizator nu respectă cerințele: între 4 și 15 caractere / format.", "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    if(tara.isEmpty()) stareObiect = false;
                    if(judet.isEmpty()) stareObiect = false;
                    if(localitate.isEmpty()) stareObiect = false;

                    if (stareObiect) {
                        Utilizator_Inregistrare utilizatorInregistrare = new Utilizator_Inregistrare(
                                this, numeUtilizator, parola, numeFamilie, prenume, nrTel, adresaEmail, tara,
                                judet, localitate, strada, nrLocuinta
                        );
                        interfataFrontEnd.dateUtilizatorInregistrare(utilizatorInregistrare);
                    }
                } catch (NullPointerException eroare_nullPointer) {
                    JOptionPane.showMessageDialog(null, "Nu ați selectat o țară, un județ sau o localitate.");
                } catch (NumberFormatException eroare_numFormat) {
                    JOptionPane.showMessageDialog(null, "Vă rugăm să introduceți un număr format din cifre la numărul (locuinței).");
                } catch(Exception eroare_generala) {
                    JOptionPane.showMessageDialog(null, "Vă rugăm să verificați cu atenție datele introduse!");
                }
            }
        } else if(sursa instanceof JComboBox<?> selectat) {
            if(selectat == getTariCombo()) {
                String tara = (String) tariCombo.getSelectedItem();
                if(interfataFrontEnd != null)
                    interfataFrontEnd.taraSelectata(tara);
            } else if(selectat == getJudeteCombo()) {
                String judet = (String) judeteCombo.getSelectedItem();
                if(interfataFrontEnd != null)
                    interfataFrontEnd.judetSelectat(judet);
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        this.setBackground(culoare);
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.decode(codHex);
        setCuloareComponentaSv(culoare);
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }

    public void formatareTariCombo(ArrayList<String> setTari){
        getTariCombo().removeAllItems();
        for(String tara: setTari)
            getTariCombo().addItem(tara);
    }

    public void formatareJudeteCombo(ArrayList<String> setJudete) {
        getJudeteCombo().removeAllItems();
        for(String judet: setJudete)
            getJudeteCombo().addItem(judet);
    }

    public void formatareLocalitatiCombo(ArrayList<String> setLocalitati) {
        getLocaliatetCombo().removeAllItems();
        for(String localitate: setLocalitati)
            getLocaliatetCombo().addItem(localitate);
    }

    private ImageIcon creareImagine2(String locatie, String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s/%s.png", locatie, numeFisier))));
    }
}
