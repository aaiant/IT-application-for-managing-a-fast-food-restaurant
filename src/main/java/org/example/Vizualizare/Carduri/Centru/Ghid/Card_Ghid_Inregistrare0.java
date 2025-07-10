package org.example.Vizualizare.Carduri.Centru.Ghid;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;

@Getter @Setter
public class Card_Ghid_Inregistrare0 extends JPanel implements ActionListener, I_Tematica {
    //  Atribute
    private Border borduraExterioara;
    private I_Tematica interfataTematica;
    private I_Carduri interfataCarduri;
    private JButton inapoiButon, inainteButon;
    private JLabel reguliLabel, detaliiReguliLabel;
    private ImageIcon inapoiButonIcon, inainteButonIcon;
    private JTable tabelPlati;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;
    private int index;
    private String[] titluri, continuturi;

    //  Constructor
    public Card_Ghid_Inregistrare0(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni, Color culoareComponentaArg, String fontArg, int tipTextArg, int
            dimensiuneTextArg, Color culoareButonArg, boolean focusareButonArg, boolean focusareDesenButonArg,
                                   String titlu) {
        setInterfataCarduri(interfataCarduri);
        this.interfataBtnActiuni = interfataBtnActiuni;
        setInterfataTematica(interfataTematica);
        configurari("Ajutor", titlu);
        initializari(culoareComponentaArg,  fontArg,  tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        asezareElemente();
        index = 0;
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    //  Metode
    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/Cont/" + urlTitluIcon + ".png")).append("\">");
        sb.append(String.format("&nbsp;%s", textIcon));
        sb.append("</html>");
        setLayout(new GridBagLayout());
        Dimension dim = getPreferredSize();
        dim.width = 150;
        setPreferredSize(dim);
        setBorduraExterioara(BorderFactory.createTitledBorder(sb.toString()));
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        setBorder(BorderFactory.createCompoundBorder(borduraExterioara, BorderFactory.createRaisedBevelBorder()));
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        titluri = new String[8];
        continuturi = new String[8];
        titluri[0] = "<html><body><b>Reguli pentru numele de utilizator:</b></body></html>";
        titluri[1] = "<html><body><b>Reguli pentru numele de familie:</b></body></html>";
        titluri[2] = "<html><body><b>Reguli pentru prenume:</b></body></html>";
        titluri[3] = "<html><body><b>Reguli pentru adresa de E-mail:</b></body></html>";
        titluri[4] = "<html><body><b>Reguli pentru numărul de telefon:</b></body></html>";
        titluri[5] = "<html><body><b>Reguli pentru țară, județe & localități:</b></body></html>";
        titluri[6] = "<html><body><b>Reguli pentru numărul locuinței:</b></body></html>";
        titluri[7] = "<html><body><b>Reguli pentru străzi:</b></body></html>";

        continuturi[0] = "<html><body><p><b>1. </b>Numele de utilizator trebuie să înceapă cu majusculă.<br>" +
                "<b>2. </b>Numele de utilizator poate conține atât majuscule, cât și minuscule.<br>" +
                "<b>3. </b>Acesta poate avea o dimensiune cuprinsă între 4 și 15 caractere.<br>" +
                "<b>4. </b>Nu puteți folosi numele de utilizator care aparține altei persoane.</p></body></html>";
        continuturi[1] = "<html><body><p><b>1. </b>Numele de familie trebuie să înceapă cu o majusculă și poate conține diacritice.<br>" +
                "<b>2. </b>Numele de familie poate conține spații sau cratime.<br>" +
                "<b>3. </b>Acesta trebuie să aibă o lungime de minim 4 caractere și maxim de 30, inclusiv.<br>" +
                "<b>4. </b>Trebuie să rețineți că nu puteți folosi același nume și prenume pe care îl are un alt utilizator.</p></body></html>";
        continuturi[2] = "<html><body><p><b>1. </b>Prenumele trebuie să înceapă cu o majusculă.<br>" +
                "<b>2. </b>Prenumele poate conține, atât diacritice, cât și spații și cratime.<br>" +
                "<b>3. </b>Poate avea dimensiuni între 4 și 30 de caractere.<br>" +
                "<b>4. </b>Trebuie să rețineți că nu puteți folosi același nume și prenume pe care îl are un alt utilizator.</p></body></html>";
        continuturi[3] = "<html><body><p><b>1. </b>Adresa de E-mail poate să înceapă cu o majusculă sau cu o minusculă.<br>" +
                "<b>2. </b>Poate conține caractere speciale, precum: \".\", \"_\", \"+\", \"-\".<br>" +
                "<b>3. </b>Trebuie să conțină \"@\".<br>" +
                "<b>4. </b>Se poate termina în: \"gmail.com\", \"yahoo.com\", \"365.univ-ovidius.ro\".<br>" +
                "<b>5. </b>Poate avea o lungime cuprinsă între 4 și 50 de caractere.<br>" +
                "<b>6. </b>Nu puteți folosi adresa de email a altei persoane.</p></body></html>";
        continuturi[4] = "<html><body><p><b>1. </b>Numerele de telefon trebuie să înceapă cu prefixul +40.<br>" +
                "<b>2. </b>Poate conține încă 9 cifre după prefixul menționat anterior.<br>" +
                "<b>3. </b>Lungimea minimă și maximă este de 12 caractere.<br>" +
                "<b>4. </b>Trebuie să rețineți că nu puteți folosi numărul de telefon al altui utilizator.</p></body></html>";
        continuturi[5] = "<html><body><p><b>1. </b>Trebuie să alegeți țara din meniul disponibil în cadrul paginii.<br>" +
                "<b>2. </b>În continuare, veți avea acces la meniurile special concepute pentru județele și localitățile respective.<br>" +
                "<b>3. </b>Odată ce ați ales o țară, următorul pas va fi să selectați un județ, pentru ca, în cele din urmă, să identificați localitatea dorită.</p></body></html>";
        continuturi[6] = "<html><body><p><b>1. </b>Numărul unei locuințe poate include doar cifre.<br>" +
                "<b>2. </b>Acest număr poate lua valori între 1 și 100, inclusiv.<br>" +
                "<b>3. </b>Puteți folosi un număr de locuință care a mai fost ales și de către un alt utilizator. În acest sens, nu există restricții.</p></body></html>";
        continuturi[7] = "<html><body><p><b>1. </b>Denumirea străzilor trebuie să înceapă cu o majusculă.<br>" +
                "<b>2. </b>Poate conține diacritice, spații și cratime.<br>" +
                "<b>3. </b>Lungimea sa poate fi între 3 și 30 de caractere.<br>" +
                "<b>4. </b>Nu poate conține cifre.<br>" +
                "<b>5. </b>Puteți folosi o stradă care a mai fost aleasă de către un alt utilizator.</p></body></html";

        setInapoiButonIcon(creareImagineBtn("InapoiButon"));
        setInainteButonIcon(creareImagineBtn("InainteButon"));

        setReguliLabel(new JLabel(titluri[0]));
        setDetaliiReguliLabel(new JLabel(continuturi[0]));

        setInapoiButon(new JButton("Înapoi"));
        getInapoiButon().setFocusPainted(false);
        getInapoiButon().addActionListener(this);
        getInapoiButon().setIcon(getInapoiButonIcon());
        getInapoiButon().setEnabled(false);

        setInainteButon(new JButton("Înainte"));
        getInainteButon().setFocusPainted(false);
        getInainteButon().addActionListener(this);
        getInainteButon().setIcon(getInainteButonIcon());

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);
    }

    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets ins0 = new Insets(0, 0, 0, 0);
        Insets ins2 = new Insets(1, 10, 1, 0);

        int orizontal = GridBagConstraints.HORIZONTAL;
        int gol = GridBagConstraints.NONE;
        int sfarsit = GridBagConstraints.LINE_END;
        int ambele = GridBagConstraints.BOTH;
        int inceput = GridBagConstraints.LINE_START;
        int inceputM = GridBagConstraints.FIRST_LINE_START;

        gc.fill = orizontal;
        gc.gridy++;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0.5;
        gc.anchor = inceput;
        gc.insets = ins2;
        add(getReguliLabel(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.weightx = 1; gc.weighty = 0.5;
        gc.anchor = inceput;
        gc.insets = ins2;
        add(getDetaliiReguliLabel(), gc);

        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0;
        gc.anchor = inceput;
        gc.insets = ins0;
        add(getInapoiButon(), gc);


        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0;
        gc.anchor = sfarsit;
        gc.insets = ins0;
        add(getInainteButon(), gc);
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s.png", numeFisier))));
    }

    private ImageIcon creareImagineBtn(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Butoane/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obiect = e.getSource();
        if(obiect instanceof JButton buton) {
            if(buton == getInapoiButon()) {
                if (index > 0) {
                    if (!getInainteButon().isEnabled())
                        getInainteButon().setEnabled(true);
                    index--;
                    getReguliLabel().setText(titluri[index]);
                    getDetaliiReguliLabel().setText(continuturi[index]);
                    if (index == 0) {
                        getInapoiButon().setEnabled(false);
                    }
                }
            } else if(buton == getInainteButon()) {
                if(index < 7) {
                    if(!getInapoiButon().isEnabled())
                        getInapoiButon().setEnabled(true);
                    index++;
                    getReguliLabel().setText(titluri[index]);
                    getDetaliiReguliLabel().setText(continuturi[index]);
                    if(index == 7) {getInainteButon().setEnabled(false);}
                }
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        this.setBackground(culoare);
        Component[] componente = this.getComponents();
        for(Component componenta : componente) {
            if(componenta instanceof JLabel eticheta) {
                eticheta.setOpaque(true);
                eticheta.setBackground(culoare);
            }
        }
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
}

