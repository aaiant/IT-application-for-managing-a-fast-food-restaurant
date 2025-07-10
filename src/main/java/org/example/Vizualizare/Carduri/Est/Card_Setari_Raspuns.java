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
import org.example.Model.Raspuns;

@Getter @Setter
public class Card_Setari_Raspuns extends JPanel implements ActionListener, I_Tematica{
    private Border borduraInterioara, borduraExterioara;
    private JButton adaugareBtn, modificareBtn, stergereBtn;
    private JLabel continutLabel ,codRaspunsLabel, titluLabel, codIntrebareLabel, codMaxLabel;
    private JTextField codRaspunsCamp, codIntrebareCamp, codMaxCamp;
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
    private String codIntrebareSelectata;

    public Card_Setari_Raspuns(I_Carduri interfataCard, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                               boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        setTitluLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 15px;\"><b>Setări Răspuns</b></p></body></html>"));
        getTitluLabel().setPreferredSize(new Dimension(200, 20));
        getTitluLabel().setOpaque(true);
        getTitluLabel().setHorizontalAlignment(JLabel.CENTER);
        getTitluLabel().setHorizontalTextPosition(JLabel.CENTER);

        setContinutLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Conținut: </b></p></body></html>"));
        getContinutLabel().setPreferredSize(new Dimension(150, 30));
        getContinutLabel().setOpaque(true);

        setCodRaspunsLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Cod Răspuns: </b></p></body></html>"));
        getCodRaspunsLabel().setPreferredSize(new Dimension(150, 30));
        getCodRaspunsLabel().setOpaque(true);

        setCodIntrebareLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Cod Întrebare: </b></p></body></html>"));
        getCodIntrebareLabel().setPreferredSize(new Dimension(150, 30));
        getCodIntrebareLabel().setOpaque(true);

        setCodMaxLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Cod Maxim: </b></p></body></html>"));
        getCodMaxLabel().setPreferredSize(new Dimension(150, 30));
        getCodMaxLabel().setOpaque(true);

        setContinutCamp(new JTextArea());
        getContinutCamp().setLineWrap(true);
        getContinutCamp().setWrapStyleWord(true);

        setScroll(new JScrollPane(getContinutCamp()));
        getScroll().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setCodRaspunsCamp(new JTextField(3));
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

        getCodRaspunsCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));
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

    private boolean verificareCod(String cod, int stadiu) {
        String regexCodRaspuns = "^R[0-9]{2}$", regexCodIntrebare = "^I[0-9]{2}$";
        boolean stareObiect = true;
        Pattern pattern = null;
        Matcher matcher = null;
        if(stadiu == 0) {
            pattern = Pattern.compile(regexCodRaspuns);
            matcher = pattern.matcher(cod);
            if(cod.length() != 3 || !matcher.matches()) {
                stareObiect = false;
                JOptionPane.showMessageDialog(null,
                        "Codul răspunsului nu respectă cerințele:  începe cu I, urmat de 2 cifre.", "Eroare",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if(stadiu == 1) {
            pattern = Pattern.compile(regexCodIntrebare);
            matcher = pattern.matcher(cod);
            if(cod.length() != 3 || !matcher.matches()) {
                stareObiect = false;
                JOptionPane.showMessageDialog(null,
                        "Codul întrebării nu respectă cerințele:  începe cu I, urmat de 2 cifre.", "Eroare",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        return stareObiect;
    }

    private boolean verificareContinut(String continut) {
        boolean stareObiect = true;
        if(continut.length() < 10 || continut.length() > 10000) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Conținutul nu respectă cerințele: între 10 și 5000 caractere.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    public void golireCampuri() {
        getContinutCamp().setText("");
        getCodRaspunsCamp().setText("");
        getCodIntrebareCamp().setText("");
    }

    public void setareIntrebareSelectata(String codIntrebare) {
        setCodIntrebareSelectata(codIntrebare);
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
                String continut = getContinutCamp().getText().stripTrailing(),
                        codRaspuns = getCodRaspunsCamp().getText().trim(),
                        codIntrebare = getCodIntrebareCamp().getText().trim();

                stareObiect = verificareCod(codRaspuns,0) && verificareCod(codIntrebare, 1) && verificareContinut(continut);

                if(stareObiect) {
                    Raspuns raspuns = new Raspuns(codRaspuns, continut);
                    interfataFrontEnd.trimitereDateRaspuns(raspuns, codIntrebare, getCodIntrebareSelectata(), 0);
                    interfataFrontEnd.actualizareCodMaxRaspunsuri();
                }
            } else if(selectat == getStergereBtn()) {
                String codIntrebare = getCodIntrebareCamp().getText().replaceAll(" ", "").trim(),
                        codRaspuns = getCodRaspunsCamp().getText().replaceAll(" ", "").trim();
                if(verificareCod(codIntrebare, 1)) {
                    Raspuns raspuns = new Raspuns(codRaspuns, "");
                    interfataFrontEnd.trimitereDateRaspuns(raspuns, codIntrebare, getCodIntrebareSelectata() ,2);
                    golireCampuri();
                    
                    interfataFrontEnd.actualizareCodMaxRaspunsuri();
                }
            } else if(selectat == getModificareBtn()) {
                boolean stareObiect = true;
                String codIntrebare = getCodIntrebareCamp().getText().trim(),
                        codRaspuns = getCodRaspunsCamp().getText().trim(),
                        continut = getContinutCamp().getText().stripTrailing();

                stareObiect = verificareCod(codIntrebare, 1) && verificareCod(codRaspuns, 0) && verificareContinut(continut);

                if(stareObiect) {
                    Raspuns raspuns = new Raspuns(codRaspuns, continut);
                    interfataFrontEnd.trimitereDateRaspuns(raspuns, codIntrebare, getCodIntrebareSelectata() ,1);
                    golireCampuri();
                    interfataFrontEnd.actualizareCodMaxRaspunsuri();
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
        add(getCodRaspunsLabel(), gc);


        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = 1;
        add(getCodRaspunsCamp(), gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.2;
        add(getCodIntrebareLabel(), gc);


        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = 1;
        add(getCodIntrebareCamp(), gc);


        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.2;
        add(getCodMaxLabel(), gc);


        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = 1;
        add(getCodMaxCamp(), gc);


        gc.gridx = 0;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 0.2;
        gc.gridwidth = 1;
        add(getContinutLabel(), gc);

        gc.gridx = 0;
        gc.gridy = 5;
        gc.gridwidth = 3;
        gc.weighty = 0.6;
        gc.fill = GridBagConstraints.BOTH;
        add(getScroll(), gc);

        gc.gridx = 0;
        gc.gridy = 6;
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
