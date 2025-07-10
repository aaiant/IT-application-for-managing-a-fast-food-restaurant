package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdesktop.swingx.JXDatePicker;
import lombok.*;
import org.example.Interfete.*;

@Getter @Setter
public class Card_Setari_CardBancar extends JPanel implements ActionListener, I_Tematica{
    private Border borduraInterioara, borduraExterioara;
    private JButton adaugareBtn, modificareBtn, stergereBtn;
    private JLabel continutLabel, numePosesorLabel, nrCardLabel, dataExpirareLabel, bancaLabel, tipCardLabel, tipMonedaLabel, monedaLabel;
    private JTextField nrCardCamp, numePosesorCamp;
    private JComboBox<String> bancaCombo, tipCardCombo, tipMonedaCombo, monedaCombo;
    private JXDatePicker dataExpirarePicker;
    private I_Carduri interfataCard;
    private String codHexSelectat, culoareSelectata,fontSv;
    private ImageIcon inchidereButonIcon;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private String link_poza;
    private int idUtilizator;

    public Card_Setari_CardBancar(I_Carduri interfataCard, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                  boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        setContinutLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Conținut: </b></p></body></html>"));

        getContinutLabel().setOpaque(true);

        setNrCardLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Număr card: </b></p></body></html>"));
        getNrCardLabel().setOpaque(true);

        setNumePosesorLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Nume posesor: </b></p></body></html>"));
        getNumePosesorLabel().setOpaque(true);

        setDataExpirareLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Dată expirare: </b></p></body></html>"));
        getDataExpirareLabel().setOpaque(true);

        setBancaLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Bancă: </b></p></body></html>"));
        getBancaLabel().setOpaque(true);

        setTipCardLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Tip card: </b></p></body></html>"));
        getTipCardLabel().setOpaque(true);

        setTipMonedaLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Tip monedă: </b></p></body></html>"));
        getTipMonedaLabel().setOpaque(true);

        setMonedaLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 12px;\"><b>Monedă: </b></p></body></html>"));
        getMonedaLabel().setOpaque(true);

        setNrCardCamp(new JTextField(18));
        setNumePosesorCamp(new JTextField(15));

        setBancaCombo(new JComboBox<>());
        setTipCardCombo(new JComboBox<>());
        setTipMonedaCombo(new JComboBox<>());
        setMonedaCombo(new JComboBox<>());

        String[] culori = {"Negru", "Alb"};
        setAdaugareBtn(new JButton("Adăugare"));
        setStergereBtn(new JButton("Ștergere"));
        setModificareBtn(new JButton("Modificare"));

        getAdaugareBtn().addActionListener(this);
        getStergereBtn().addActionListener(this);
        getModificareBtn().addActionListener(this);

        int dimensiuneFont = 16; String tipFont = "Times New Roman";

        getAdaugareBtn().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getStergereBtn().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getModificareBtn().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));

        getNrCardCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getNumePosesorCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));

        getBancaCombo().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getTipCardCombo().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getTipMonedaCombo().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getMonedaCombo().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));

        setDataExpirarePicker(new JXDatePicker());
        getDataExpirarePicker().setFormats(new SimpleDateFormat("MM/yyyy"));
        Calendar calendarMin = Calendar.getInstance();
        calendarMin.set(Calendar.DAY_OF_MONTH, 1);
        Calendar calendarMax = Calendar.getInstance();
        calendarMax.add(Calendar.YEAR, 5);
        getDataExpirarePicker().getMonthView().setLowerBound(calendarMin.getTime());
        getDataExpirarePicker().getMonthView().setUpperBound(calendarMax.getTime());

        getDataExpirarePicker().setPreferredSize(new Dimension(100, 25));
        getDataExpirarePicker().setFont(new Font("Times New Roman", Font.PLAIN, dimensiuneFont));

        setInchidereButonIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/Butoane/InchidereButon2.png"))));

        getNrCardCamp().setPreferredSize(new Dimension(180, 25));
        getNumePosesorCamp().setPreferredSize(new Dimension(180, 25));
        getDataExpirarePicker().setPreferredSize(new Dimension(100, 25));


        getBancaCombo().setPreferredSize(new Dimension(180, 25));
        getTipCardCombo().setPreferredSize(new Dimension(180, 25));
        getTipMonedaCombo().setPreferredSize(new Dimension(180, 25));
        getMonedaCombo().setPreferredSize(new Dimension(180, 25));

        getBancaCombo().addActionListener(this);
        getTipMonedaCombo().addActionListener(this);

        getNrCardLabel().setPreferredSize(new Dimension(120, 25));
        getNumePosesorLabel().setPreferredSize(new Dimension(120, 25));
        getDataExpirareLabel().setPreferredSize(new Dimension(120, 25));
        getBancaLabel().setPreferredSize(new Dimension(120, 25));
        getTipCardLabel().setPreferredSize(new Dimension(120, 25));


        getAdaugareBtn().setMargin(new Insets(2, 8, 2, 8));
        getModificareBtn().setMargin(new Insets(2, 8, 2, 8));
        getStergereBtn().setMargin(new Insets(2, 8, 2, 8));

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);

        asezareElemente();
    }

    private boolean verificareNumePosesor(String nume) {
        String regexNumePosesor = "^[a-zA-ZăĂâÂîÎșȘțȚ\\s]+$";
        boolean stareObiect = true;
        Pattern pattern = null;
        Matcher matcher = null;
            pattern = Pattern.compile(regexNumePosesor);
            matcher = pattern.matcher(nume);
            if(nume.length() < 3 || nume.length() > 20 || !matcher.matches()) {
                stareObiect = false;
                JOptionPane.showMessageDialog(null,
                        "Numele posesorului nu respectă cerințele: începe cu majusculă & trebuie să aibă între 5 & 20 de litere.", "Eroare",
                        JOptionPane.ERROR_MESSAGE);
            }
        return stareObiect;
    }

    private boolean verificareNrCard(String nrCard) {
        String regexNrCard = "^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$";
        boolean stareObiect = true;
        Pattern pattern = null;
        Matcher matcher = null;
        pattern = Pattern.compile(regexNrCard);
        matcher = pattern.matcher(nrCard);
        if(nrCard.length() != 19 || !matcher.matches()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Numărul cardului nu respectă cerințele: conține 4 grupe de câte 4 cifre, despărțite de cratime.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }


    public void golireCampuri() {
        getDataExpirarePicker().setDate(null);
        getNrCardCamp().setText("");
        getNumePosesorCamp().setText("");
        getBancaCombo().setSelectedIndex(0);
        getTipCardCombo().setSelectedIndex(0);
        getTipMonedaCombo().setSelectedIndex(0);
        getTipMonedaCombo().setSelectedIndex(0);
    }

    public void actualizareBanci(ArrayList<String> banci) {
        if(getBancaCombo() != null) {
            if(getBancaCombo().getItemCount() == 0) {
                for (String banca : banci) {
                    getBancaCombo().addItem(banca);
                }
            }
        }
    }

    public void actualizareTipuriCarduri(ArrayList<String> tipuriCarduri) {
        if(getTipCardCombo() != null) {
            for(String card : tipuriCarduri) {
                getTipCardCombo().addItem(card);
            }
        }
    }

    public void actualizareTipuriMonede(ArrayList<String> tipuriMonede) {
        if(getTipMonedaCombo() != null) {
            if (getTipMonedaCombo().getItemCount() == 0) {
                for (String moneda : tipuriMonede) {
                    getTipMonedaCombo().addItem(moneda);
                }
            }
        }
    }

    public void actualizareMoneda(ArrayList<String> monede) {
        if(getMonedaCombo() != null) {
            for(String moneda : monede) {
                getMonedaCombo().addItem(moneda);
            }
        }
    }

    public void golireTipCarduriCombo() {
        if(getTipCardCombo() != null) {
            getTipCardCombo().removeAllItems();
        }
    }

    public void golireMonedaCombo() {
        if(getMonedaCombo() != null) {
            getMonedaCombo().removeAllItems();
        }
    }


    public void actualizareLinkPoza(String link) {
        this.link_poza = link;
    }

    public void actualizareIdUtilizator(int id) {
        this.idUtilizator = id;
    }

    public void golireComboBoxuri() {
        if(getBancaCombo().getItemCount() > 0) {
            getBancaCombo().removeAllItems();
        }

        if(getTipMonedaCombo().getItemCount() > 0) {
            getTipMonedaCombo().removeAllItems();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getAdaugareBtn()) {
                String numarCard = getNrCardCamp().getText().trim(),
                        posesor = getNumePosesorCamp().getText().trim().toUpperCase(),
                        banca = getBancaCombo().getSelectedItem().toString(),
                        tipCard = getTipCardCombo().getSelectedItem().toString(),
                        tipMoneda = getTipMonedaCombo().getSelectedItem().toString(),
                        moneda = getMonedaCombo().getSelectedItem().toString();

                Date dataExpirare = getDataExpirarePicker().getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
                String dataFormatata = dateFormat.format(dataExpirare);

                boolean stare = true;
                try {

                    stare = verificareNumePosesor(posesor);
                    if(!stare) throw new Exception();

                    stare = verificareNrCard(numarCard);
                    if(!stare) throw new Exception();

                    interfataFrontEnd.actualizareCarduriBancare(getIdUtilizator(), numarCard, posesor,
                            dataFormatata, banca, tipCard, moneda, 0);
                    interfataBtnActiuni.refreshCarduriBancare(getIdUtilizator());
                } catch(Exception eroare) {
                    System.out.println(eroare.getMessage());
                }
            } else if(selectat == getStergereBtn()) {
                String numarCard = getNrCardCamp().getText().trim();
                boolean stare = true;
                try {
                    stare = verificareNrCard(numarCard);
                    if (!stare) throw new Exception();

                    interfataFrontEnd.stergereCardBancar(getIdUtilizator(), numarCard);
                    interfataBtnActiuni.refreshCarduriBancare(getIdUtilizator());
                } catch(Exception eroare) {
                    System.out.println(eroare.getMessage());
                }
            } else if(selectat == getModificareBtn()) {
                String numarCard = getNrCardCamp().getText().trim(),
                        posesor = getNumePosesorCamp().getText().trim().toUpperCase(),
                        banca = getBancaCombo().getSelectedItem().toString(),
                        tipCard = getTipCardCombo().getSelectedItem().toString(),
                        tipMoneda = getTipMonedaCombo().getSelectedItem().toString(),
                        moneda = getMonedaCombo().getSelectedItem().toString();

                Date dataExpirare = getDataExpirarePicker().getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
                String dataFormatata = dateFormat.format(dataExpirare);

                boolean stare = true;
                try {

                    stare = verificareNumePosesor(posesor);
                    if(!stare) throw new Exception();

                    stare = verificareNrCard(numarCard);
                    if(!stare) throw new Exception();
                    interfataFrontEnd.actualizareCarduriBancare(getIdUtilizator(), numarCard, posesor, dataFormatata, banca,
                            tipCard, moneda, 1);
                    interfataBtnActiuni.refreshCarduriBancare(getIdUtilizator());
                } catch (Exception eroare) {
                    System.out.println(eroare.getMessage());
                }
            }
        } else if(sursa instanceof JComboBox<?> selectat) {
            if(selectat == getBancaCombo()) {
                interfataFrontEnd.actualizareTipuriCarduriSetariCard(getBancaCombo().getSelectedItem().toString());
            } else if(selectat == getTipMonedaCombo()) {
                interfataFrontEnd.actualizareMonedeSetariCard(getTipMonedaCombo().getSelectedItem().toString());
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

        gc.insets = new Insets(3, 5, 3, 5);

        gc.ipadx = 0;
        gc.ipady = 0;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.1;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        add(getNrCardLabel(), gc);

        gc.gridy = 1;
        add(getNumePosesorLabel(), gc);

        gc.gridy = 2;
        add(getDataExpirareLabel(), gc);

        gc.gridy = 3;
        add(getBancaLabel(), gc);

        gc.gridy = 4;
        add(getTipCardLabel(), gc);

        gc.gridy = 5;
        add(getTipMonedaLabel(), gc);

        gc.gridy = 6;
        add(getMonedaLabel(), gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.9;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 2;
        add(getNrCardCamp(), gc);

        gc.gridy = 1;
        add(getNumePosesorCamp(), gc);

        gc.gridy = 2;
        add(getDataExpirarePicker(), gc);

        gc.gridy = 3;
        gc.gridwidth = 2;
        add(getBancaCombo(), gc);

        gc.gridy = 4;
        add(getTipCardCombo(), gc);

        gc.gridy = 5;
        add(getTipMonedaCombo(), gc);

        gc.gridy = 6;
        add(getMonedaCombo(), gc);

        gc.gridy = 7;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(8, 3, 3, 3);
        add(getAdaugareBtn(), gc);

        gc.gridx = 1;
        add(getModificareBtn(), gc);

        gc.gridx = 2;
        add(getStergereBtn(), gc);
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
