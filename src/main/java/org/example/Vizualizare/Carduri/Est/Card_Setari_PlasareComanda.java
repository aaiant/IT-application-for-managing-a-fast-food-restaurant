package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.*;
import org.example.Interfete.*;
import org.example.Model.ProdusCos;

@Getter @Setter
public class Card_Setari_PlasareComanda extends JPanel implements ActionListener, I_Tematica{
    private Border borduraInterioara, borduraExterioara;
    private JButton plasareComandaBtn;
    private JLabel titluLabel, pretTotalLabel, transportTitluLabel, cardBancarLabel, titluPlataLabel, monedaLabel;
    private JTextField pretTotalCamp, monedaCamp;
    private ButtonGroup grupTransport;
    private JRadioButton ridicarePersonalaRadioBtn, livrareDomiciliuRadioBtn;
    private JComboBox<String> carduriBancareCombo;
    private I_Carduri interfataCard;
    private String codHexSelectat, culoareSelectata,fontSv;
    private ImageIcon inchidereButonIcon, ridicarePersonalaIcon, livrareDomiciuliuIcon;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private int idUtilizator;
    private ArrayList<ProdusCos> listaProduseCos;

    public Card_Setari_PlasareComanda(I_Carduri interfataCard, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                      boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        setLivrareDomiciuliuIcon(creareImagine("MeniuriProgram", "LivrareDomiciliu"));
        setRidicarePersonalaIcon(creareImagine("MeniuriProgram", "RidicarePersonala"));

        setGrupTransport(new ButtonGroup());

        setRidicarePersonalaRadioBtn(new JRadioButton("Ridicare Personală"));
        getRidicarePersonalaRadioBtn().setIcon(getRidicarePersonalaIcon());

        setLivrareDomiciliuRadioBtn(new JRadioButton("Livrare la domiciliu"));
        getLivrareDomiciliuRadioBtn().setIcon(getLivrareDomiciuliuIcon());

        getGrupTransport().add(getRidicarePersonalaRadioBtn());
        getGrupTransport().add(getLivrareDomiciliuRadioBtn());

        getRidicarePersonalaRadioBtn().setSelected(true);

        setTitluLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 15px;\"><b>Plasare Comandă</b></p></body></html>"));
        getTitluLabel().setPreferredSize(new Dimension(200, 20));
        getTitluLabel().setOpaque(true);
        getTitluLabel().setHorizontalAlignment(JLabel.CENTER);
        getTitluLabel().setHorizontalTextPosition(JLabel.CENTER);

        setTransportTitluLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 15px;\"><b>Transport</b></p></body></html>"));
        getTransportTitluLabel().setPreferredSize(new Dimension(200, 20));
        getTransportTitluLabel().setOpaque(true);
        getTransportTitluLabel().setHorizontalAlignment(JLabel.CENTER);
        getTransportTitluLabel().setHorizontalTextPosition(JLabel.CENTER);

        setPretTotalLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Preț total: </b></p></body></html>"));
        getPretTotalLabel().setPreferredSize(new Dimension(150, 30));
        getPretTotalLabel().setOpaque(true);

        setCardBancarLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Card bancar: </b></p></body></html>"));
        getCardBancarLabel().setPreferredSize(new Dimension(150, 30));
        getCardBancarLabel().setOpaque(true);

        setMonedaLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Monedă: </b></p></body></html>"));
        getMonedaLabel().setPreferredSize(new Dimension(150, 30));
        getMonedaLabel().setOpaque(true);

        setTitluPlataLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px; padding-left: 42px; text-align: center;\"><b>Plată</b></p></body></html>"));
        getTitluPlataLabel().setPreferredSize(new Dimension(150, 30));
        getTitluPlataLabel().setOpaque(true);

        setPretTotalCamp(new JTextField(5));
        setMonedaCamp(new JTextField(3));

        setCarduriBancareCombo(new JComboBox<>());

        setListaProduseCos(new ArrayList<>());

        getPretTotalCamp().setEditable(false);
        getMonedaCamp().setEditable(false);

        setPlasareComandaBtn(new JButton("Comandă"));

        getPlasareComandaBtn().addActionListener(this);
        getRidicarePersonalaRadioBtn().addActionListener(this);
        getLivrareDomiciliuRadioBtn().addActionListener(this);
        getCarduriBancareCombo().addActionListener(this);

        int dimensiuneFont = 18; String tipFont = "Times New Roman";
        getCarduriBancareCombo().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));

        getPlasareComandaBtn().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));

        getPretTotalCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getRidicarePersonalaRadioBtn().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getLivrareDomiciliuRadioBtn().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));
        getMonedaCamp().setFont(new Font(tipFont, Font.PLAIN, dimensiuneFont));

        getPlasareComandaBtn().setMargin(new Insets(1, 1, 1, 1));

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

    private boolean verificareIdUtilizator(int id) {
        if(id <= 0) {
            return false;
        }
        return true;
    }

    private boolean verificarePretTotal(double pretTotal) {
        boolean stareObiect = true;
        if(pretTotal < 0.5 || pretTotal > 10000.00) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Pretul total pentru produsele din coș nu respectă cerințele: trebuie să fie un număr cuprins între 0.5 și 10000.00.", "Eroare",
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
                    "Numărul cardului nu respectă cerințele: conține 4 grupe de câte 4 cifre, despărțite de cratime câte (-).", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareAbreviereMoneda(String abreviere) {
        String regexNrCard = "^[A-Z]{2,5}$";
        boolean stareObiect = true;
        Pattern pattern = null;
        Matcher matcher = null;
        pattern = Pattern.compile(regexNrCard);
        matcher = pattern.matcher(abreviere);
        if(abreviere.length() < 2 || abreviere.length() > 5 || !matcher.matches()) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Abrevierea pe care o deține moneda care a fost asociată cardului nu respectă cerințele: conține între 2 și 5 majuscule.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    public void golireCampuri() {
        getCarduriBancareCombo().removeAllItems();
        getPretTotalCamp().setText("");
    }

    private boolean verificareSimultanaCampuri(double pretTotal, int idUtilizator) {
        boolean stare = true;

        stare = verificarePretTotal(pretTotal);
        if(!stare) return false;

        stare = verificareIdUtilizator(idUtilizator);
        if(!stare) return false;

        return stare;
    }

    public void actualizarePretTotal(double pretTotal) {
        getPretTotalCamp().setText("" + pretTotal);
    }

    public void actualizareIdUtilizatorVal(int idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public void actualizareCarduriBancareCombo(ArrayList<String> listaCarduri) {
        if(getCarduriBancareCombo() != null) {
            getCarduriBancareCombo().removeAllItems();
            for(String card : listaCarduri) {
                getCarduriBancareCombo().addItem(card);
            }
        }
    }

    public void actualizareAbreviereMoneda(String abreviere) {
        getMonedaCamp().setText(abreviere);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getPlasareComandaBtn()) {
                interfataFrontEnd.trimitereProduseCos();
                double pretTotal = Double.parseDouble(getPretTotalCamp().getText().trim());
                int indexJRadioButton = 0;
                String nrCard = Objects.requireNonNull(getCarduriBancareCombo().getSelectedItem()).toString(),
                abreviereMoneda = getMonedaCamp().getText().trim();
                boolean stareObiect = true;


                stareObiect = verificareSimultanaCampuri(pretTotal, getIdUtilizator());

                if(getRidicarePersonalaRadioBtn().isSelected()) {
                    indexJRadioButton = 1;
                } else if(getLivrareDomiciliuRadioBtn().isSelected()) {
                    indexJRadioButton = 2;
                }

                if(stareObiect && indexJRadioButton > 0 && !getListaProduseCos().isEmpty() && verificareAbreviereMoneda(abreviereMoneda)) {
                    interfataFrontEnd.trimitereComanda(getIdUtilizator(), indexJRadioButton, pretTotal, nrCard, getListaProduseCos(), abreviereMoneda);
                }
            }
        } else if(sursa instanceof JRadioButton selectat) {
            if(selectat == getRidicarePersonalaRadioBtn()) {
                setarePret(15.00, 0);
            } else if(selectat == getLivrareDomiciliuRadioBtn()) {
                setarePret(15.00, 1);
            }
        } else if(sursa instanceof JComboBox<?> selectat) {
            if(selectat == getCarduriBancareCombo()) {
                String nrCard = "";
                try {
                    nrCard = Objects.requireNonNull(getCarduriBancareCombo().getSelectedItem()).toString();

                    boolean stare = verificareIdUtilizator(getIdUtilizator()) && verificareNrCard(nrCard);

                    if(stare) {
                        interfataFrontEnd.actualizareAbreviereMoneda(idUtilizator, nrCard);
                    }
                } catch(Exception eroare) {
                    System.out.println(eroare.getMessage());
                }
            }
        }
    }

    private void setarePret(double pretAdaugat, int stadiu) {
        double pret = Double.parseDouble(getPretTotalCamp().getText().trim());
        switch (stadiu) {
            case 0:
                if(pret - pretAdaugat >= 0)
                    getPretTotalCamp().setText("" + (pret - pretAdaugat));
                break;
            case 1:
                getPretTotalCamp().setText("" + (pret + pretAdaugat));
                break;
        }
    }

    private ImageIcon creareImagine(String locatie, String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s/%s.png", locatie, numeFisier))));
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

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        add(getTitluLabel(), gc);

        gc.gridwidth = 1;

        gc.gridx = 0;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.3;
        add(getPretTotalLabel(), gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.7;
        add(getPretTotalCamp(), gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        add(getTransportTitluLabel(), gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        add(getRidicarePersonalaRadioBtn(), gc);

        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        add(getLivrareDomiciliuRadioBtn(), gc);

        gc.gridx = 0;
        gc.gridy = 5;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        add(getTitluPlataLabel(), gc);

        gc.gridwidth = 1;

        gc.gridx = 0;
        gc.gridy = 6;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.3;
        add((getCardBancarLabel()), gc);

        gc.gridx = 1;
        gc.gridy = 6;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.3;
        add((getCarduriBancareCombo()), gc);

        gc.gridx = 0;
        gc.gridy = 7;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.3;
        add((getMonedaLabel()), gc);

        gc.gridx = 1;
        gc.gridy = 7;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.3;
        add((getMonedaCamp()), gc);


        gc.gridx = 0;
        gc.gridy = 8;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 1.0;
        add((getPlasareComandaBtn()), gc);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }


    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
