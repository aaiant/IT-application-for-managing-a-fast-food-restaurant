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
import org.example.Model.Intrebare;

@Getter @Setter
public class Card_Setari_Intrebare extends JPanel implements ActionListener, I_Tematica{
    private Border borduraInterioara, borduraExterioara;
    private JButton adaugareBtn, modificareBtn, stergereBtn;
    private JLabel continutLabel ,codIntrebareLabel, titluLabel, codMax;
    private JTextField codIntrebareCamp, codMaxCamp;
    private JTextArea continutCamp;
    private JScrollPane scroll;
    private I_Carduri interfataCard;
    private String codHexSelectat, culoareSelectata,fontSv;
    private ImageIcon inchidereButonIcon;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;

    public Card_Setari_Intrebare(I_Carduri interfataCard, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                               boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        setTitluLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 15px;\"><b>Setări Întrebare</b></p></body></html>"));
        getTitluLabel().setPreferredSize(new Dimension(200, 20));
        getTitluLabel().setOpaque(true);
        getTitluLabel().setHorizontalAlignment(JLabel.CENTER);
        getTitluLabel().setHorizontalTextPosition(JLabel.CENTER);

        setContinutLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Conținut: </b></p></body></html>"));
        getContinutLabel().setPreferredSize(new Dimension(150, 30));
        getContinutLabel().setOpaque(true);

        setCodIntrebareLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Cod Întrebare: </b></p></body></html>"));
        getCodIntrebareLabel().setPreferredSize(new Dimension(150, 30));
        getCodIntrebareLabel().setOpaque(true);

        setCodMax(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Cod Maxim: </b></p></body></html>"));
        getCodMax().setPreferredSize(new Dimension(150, 30));
        getCodMax().setOpaque(true);

        setContinutCamp(new JTextArea());
        getContinutCamp().setLineWrap(true);
        getContinutCamp().setWrapStyleWord(true);

        setScroll(new JScrollPane(getContinutCamp()));
        getScroll().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setCodIntrebareCamp(new JTextField(3));
        setCodMaxCamp(new JTextField(3));
        getCodMaxCamp().setEditable(false);

        String[] culori = {"Negru", "Alb"};
        setAdaugareBtn(new JButton("Adăugare"));
        setStergereBtn(new JButton("Ștergere"));
        setModificareBtn(new JButton("Modificare"));

        getAdaugareBtn().addActionListener(this);
        getStergereBtn().addActionListener(this);
        getModificareBtn().addActionListener(this);

        getAdaugareBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getStergereBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getModificareBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        getCodIntrebareCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getContinutCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getCodMaxCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        getAdaugareBtn().setMargin(new Insets(1, 1, 1, 1));
        getModificareBtn().setMargin(new Insets(1, 1, 1, 1));
        getStergereBtn().setMargin(new Insets(1, 1, 1, 1));

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

    private boolean verificareCodIntrebare(String codIntrebare) {
        String regexCod = "^I[0-9]{2}$";
        boolean stareObiect = true;
        Pattern pattern = Pattern.compile(regexCod);
        Matcher matcher = pattern.matcher(codIntrebare);
        if(codIntrebare.length() != 3 || !matcher.matches()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Codul întrebării nu respectă cerințele:  începe cu majuscula I, urmat de 2 cifre.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareContinut(String continut) {
        String rexContinut = "^[A-ZĂÂÎȘȚ].*\\?$";
        boolean stareObiect = true;
        Pattern pattern = Pattern.compile(rexContinut);
        Matcher matcher = pattern.matcher(continut);
        if(continut.length() < 10 || continut.length() > 150 || !matcher.matches()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Conținutul nu respectă cerințele: între 10 și 150 caractere " +
                            "sau formatul majusculă + orice caracter, iar la final se termină cu \'?\'.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    public void golireCampuri() {
        getContinutCamp().setText("");
        getCodIntrebareCamp().setText("");
    }

    public void actualizareCodMax(String cod) {
        getCodMaxCamp().setText(cod);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getAdaugareBtn()) {
                boolean stareObiect = true;
                String continut = getContinutCamp().getText().trim(),
                        codIntrebare = getCodIntrebareCamp().getText().trim();

                stareObiect = verificareCodIntrebare(codIntrebare) && verificareContinut(continut);

                if(stareObiect) {
                    Intrebare intrebareNoua = new Intrebare(codIntrebare, continut);
                    interfataFrontEnd.trimitereIntrebare(intrebareNoua, 0);
                    interfataFrontEnd.actualizareCodMaxIntrebari();
                }
            } else if(selectat == getStergereBtn()) {
                String codIntrebare = getCodIntrebareCamp().getText().replaceAll(" ", "").trim();
                if(verificareCodIntrebare(codIntrebare)) {
                    interfataFrontEnd.stergereIntrebare(codIntrebare);
                    getCodIntrebareCamp().setText("");
                    interfataFrontEnd.actualizareCodMaxIntrebari();
                }
            } else if(selectat == getModificareBtn()) {
                boolean stareObiect = true;
                String codIntrebare = getCodIntrebareCamp().getText().trim(),
                        continut = getContinutCamp().getText().trim();

                stareObiect = verificareCodIntrebare(codIntrebare) && verificareContinut(continut);

                if(stareObiect) {
                    Intrebare intrebareNoua = new Intrebare(codIntrebare, continut);
                    interfataFrontEnd.trimitereIntrebare(intrebareNoua, 1);
                    interfataFrontEnd.actualizareCodMaxIntrebari();
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
        add(getCodIntrebareLabel(), gc);


        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = 1;
        add(getCodIntrebareCamp(), gc);


        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.2;
        add(getCodMax(), gc);


        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = 1;
        add(getCodMaxCamp(), gc);


        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 0.2;
        gc.gridwidth = 1;
        add(getContinutLabel(), gc);

        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 3;
        gc.weighty = 0.6;
        gc.fill = GridBagConstraints.BOTH;
        add(getScroll(), gc);

        gc.gridx = 0;
        gc.gridy = 5;
        gc.gridwidth = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.weighty = 0.1;
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
