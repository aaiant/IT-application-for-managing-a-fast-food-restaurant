package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.*;
import org.example.Interfete.*;

@Getter @Setter
public class Card_Setari_Tematica extends JPanel implements ActionListener, I_Tematica{
    private Border borduraInterioara, borduraExterioara;
    private JButton adaugareBtn, modificareBtn, stergereBtn;
    private JLabel denumireNuantaLabel ,hexFundalLabel ,hexTextLabel, titluLabel;
    private JTextField denumireNuanteCamp ,hexFundalCamp;
    private JComboBox<String> hexCuloareTextAdaugare;
    private I_Carduri interfataCard;
    private String codHexSelectat, culoareSelectata,fontSv;
    private ImageIcon inchidereButonIcon;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;

    public Card_Setari_Tematica(I_Carduri interfataCard, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        setTitluLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 15px;\"><b>Setări Nuanță</b></p></body></html>"));
        getTitluLabel().setPreferredSize(new Dimension(200, 20));
        getTitluLabel().setOpaque(true);

        setDenumireNuantaLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Denumire nuanță: </b></p></body></html>"));
        getDenumireNuantaLabel().setPreferredSize(new Dimension(150, 30));
        getDenumireNuantaLabel().setOpaque(true);

        setHexFundalLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Cod hex culoare fundal: </b></p></body></html>"));
        getHexFundalLabel().setPreferredSize(new Dimension(150, 30));
        getHexFundalLabel().setOpaque(true);

        setHexTextLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Cod hex culoare text: </b></p></body></html>"));
        getHexTextLabel().setPreferredSize(new Dimension(150, 30));
        getHexTextLabel().setOpaque(true);


        setDenumireNuanteCamp(new JTextField(30));
        setHexFundalCamp(new JTextField(7));

        String[] culori = {"Negru", "Alb"};
        setHexCuloareTextAdaugare(new JComboBox<>(culori));

        setAdaugareBtn(new JButton("Adăugare"));
        setStergereBtn(new JButton("Ștergere"));
        setModificareBtn(new JButton("Modificare"));

        getAdaugareBtn().addActionListener(this);
        getStergereBtn().addActionListener(this);
        getModificareBtn().addActionListener(this);

        getAdaugareBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getStergereBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getModificareBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        getHexFundalCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getDenumireNuanteCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        getAdaugareBtn().setMargin(new Insets(1, 1, 1, 1));
        getModificareBtn().setMargin(new Insets(1, 1, 1, 1));
        getStergereBtn().setMargin(new Insets(1, 1, 1, 1));

        getHexCuloareTextAdaugare().setFont(new Font(fontArg, tipTextArg, dimensiuneTextArg));

        setInchidereButonIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/Butoane/InchidereButon2.png"))));

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);

        asezareElemente();
    }

    private boolean verificareCodHex(String culoareFundal) {
        String regexCuloare = "^#[0-9a-fA-F]{6}$";
        boolean stareObiect = true;
        Pattern pattern = Pattern.compile(regexCuloare);
        Matcher matcher = pattern.matcher(culoareFundal);
        if(culoareFundal.length() != 7 || !matcher.matches()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Codul hex al culorii pentru fundal nu respectă cerințele:  începe cu #, " +
                            "urmat de 6 cifre sau litere de la A-F.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareDenumire(String denumireCuloare) {
        String rexDenumireCuloare = "[A-ZĂÂÎȘȚ][a-zA-ZăĂîÎâÂșȘțȚ\\s\\-]+$";
        boolean stareObiect = true;
        Pattern pattern = Pattern.compile(rexDenumireCuloare);
        Matcher matcher = pattern.matcher(denumireCuloare);
        if(denumireCuloare.length() < 3 || denumireCuloare.length() > 30 || denumireCuloare.equals("Culoare") || !matcher.matches()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Denumirea culorii nu respectă cerințele: între 3 și 30 caractere sau formatul " +
                            "majusculă + minusculă/spațiu/cratimă.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private void golireCampuriAdaugare() {
        getDenumireNuanteCamp().setText("");
        getHexFundalCamp().setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getAdaugareBtn()) {
                boolean stareObiect = true;
                String denumireCuloare = getDenumireNuanteCamp().getText().trim(),
                    culoareFundal = getHexFundalCamp().getText().replaceAll(" ", "").trim();
                int culoareTextAdaugare = getHexCuloareTextAdaugare().getSelectedIndex();

                stareObiect = verificareDenumire(denumireCuloare);
                stareObiect = verificareCodHex(culoareFundal);

                if(stareObiect) {
                    Culori culoareNoua = null;
                    if(culoareTextAdaugare == 0) {
                        culoareNoua = new Culori(this, denumireCuloare, culoareFundal, "#000000");
                    } else if(culoareTextAdaugare == 1) {
                        culoareNoua = new Culori(this, denumireCuloare, culoareFundal, "#FFFFFF");
                    }
                    interfataFrontEnd.trimitereCuloare(culoareNoua, 0);
                    golireCampuriAdaugare();
                }
            } else if(selectat == getStergereBtn()) {
                String culoareFundal = getHexFundalCamp().getText().replaceAll(" ", "").trim();
                if(verificareCodHex(culoareFundal)) {
                    interfataFrontEnd.stergereNuanta(culoareFundal);
                    getHexFundalCamp().setText("");
                }
            } else if(selectat == getModificareBtn()) {
                boolean stareObiect = true;
                String denumireCuloare = getDenumireNuanteCamp().getText().trim(),
                        culoareFundal = getHexFundalCamp().getText().replaceAll(" ", "").trim();
                int culoareTextModificare = getHexCuloareTextAdaugare().getSelectedIndex();

                stareObiect = verificareDenumire(denumireCuloare);
                stareObiect = verificareCodHex(culoareFundal);

                if(stareObiect) {
                    Culori culoareNoua = null;
                    if(culoareTextModificare == 0) {
                        culoareNoua = new Culori(this, denumireCuloare, culoareFundal, "#000000");
                    } else if(culoareTextModificare == 1) {
                        culoareNoua = new Culori(this, denumireCuloare, culoareFundal, "#FFFFFF");
                    }
                    interfataFrontEnd.trimitereCuloare(culoareNoua, 1);
                    golireCampuriAdaugare();
                }
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {

    }

    @Override
    public void schimbaCuloareText(String codHex) {

    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {

    }

    @Override
    public void asezareElemente() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.insets = new Insets(5, 5, 5, 5);
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 3;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        add(getTitluLabel(), gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.2;
        add(getDenumireNuantaLabel(), gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        add(getDenumireNuanteCamp(), gc);


        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.2;
        add(getHexFundalLabel(), gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        add(getHexFundalCamp(), gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.2;
        add(getHexTextLabel(), gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        add(getHexCuloareTextAdaugare(), gc);

        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;
        add(getAdaugareBtn(), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.WEST;
        add(getModificareBtn(), gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.CENTER;
        add(getStergereBtn(), gc);
    }


    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
