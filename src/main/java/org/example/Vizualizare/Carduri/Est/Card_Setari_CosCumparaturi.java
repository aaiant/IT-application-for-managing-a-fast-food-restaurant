package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.*;
import org.example.Model.ProdusCos;

@Getter @Setter
public class Card_Setari_CosCumparaturi extends JPanel implements ActionListener, I_Tematica{
    private Border borduraInterioara, borduraExterioara;
    private JButton modificareBtn, stergereBtn;
    private JLabel cantitateProdusLabel, titluLabel, idProdusStocatCos, pretTotalLabel;
    private JTextField idProdusStocatCamp, cantitateProdusCamp, pretTotalCamp;
    private I_Carduri interfataCard;
    private String codHexSelectat, culoareSelectata,fontSv;
    private ImageIcon inchidereButonIcon, ridicarePersonalaIcon, livrareDomiciuliuIcon;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private int idUtilizator;

    public Card_Setari_CosCumparaturi(I_Carduri interfataCard, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                     boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }
    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        setTitluLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 15px;\"><b>Modificare Coș</b></p></body></html>"));
        getTitluLabel().setPreferredSize(new Dimension(200, 20));
        getTitluLabel().setOpaque(true);
        getTitluLabel().setHorizontalAlignment(JLabel.CENTER);
        getTitluLabel().setHorizontalTextPosition(JLabel.CENTER);

        setCantitateProdusLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Cantitate: </b></p></body></html>"));
        getCantitateProdusLabel().setPreferredSize(new Dimension(150, 30));
        getCantitateProdusLabel().setOpaque(true);

        setIdProdusStocatCos(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>ID Produs: </b></p></body></html>"));
        getIdProdusStocatCos().setPreferredSize(new Dimension(150, 30));
        getIdProdusStocatCos().setOpaque(true);

        setPretTotalLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Preț total: </b></p></body></html>"));
        getPretTotalLabel().setPreferredSize(new Dimension(150, 30));
        getPretTotalLabel().setOpaque(true);

        setIdProdusStocatCamp(new JTextField(4));
        setCantitateProdusCamp(new JTextField(4));
        setPretTotalCamp(new JTextField(5));

        getIdProdusStocatCamp().setEditable(false);
        getPretTotalCamp().setEditable(false);

        setModificareBtn(new JButton("Modificare"));
        setStergereBtn(new JButton("Ștergere"));

        getModificareBtn().addActionListener(this);
        getStergereBtn().addActionListener(this);

        getModificareBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getStergereBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        getIdProdusStocatCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getCantitateProdusCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getPretTotalCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));

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

    private boolean verificareIdUtilizator(int id) {
        if(id <= 0) {
            return false;
        }
        return true;
    }

    private boolean verificareIdProdusStocat(int id) {
        boolean stareObiect = true;
        if(id <= 0) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "ID-ul produsului din coș nu respectă cerințele: trebuie să fie un număr mai mare decât 0.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
    }

    private boolean verificareCantitateProdus(int cantitate) {
        boolean stareObiect = true;
        if(cantitate <= 0 || cantitate > 200) {
            stareObiect = false;
            JOptionPane.showMessageDialog(null,
                    "Cantitatea asociată produsului din coș nu respectă cerințele: trebuie să fie un număr cuprins între 1 și 200.", "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        }
        return stareObiect;
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

    public void golireCampuri() {
        getIdProdusStocatCamp().setText("");
        getCantitateProdusCamp().setText("");
        //getPretTotalCamp().setText("");
    }

    private boolean verificareSimultanaCampuri(int idProdusStocat, int cantitate, double pretTotal, int idUtilizator) {
        boolean stare = true;

        stare = verificareIdProdusStocat(idProdusStocat);
        if(!stare) return false;

        stare = verificareCantitateProdus(cantitate);
        if(!stare) return false;

        stare = verificarePretTotal(pretTotal);
        if(!stare) return false;

        stare = verificareIdUtilizator(idUtilizator);
        if(!stare) return false;

        return stare;
    }

    public void actualizareIdProdusStocat(int id) {
        getIdProdusStocatCamp().setText("" + id);
    }

    public void actualizareCantitateProdus(int cantitate) {
        getCantitateProdusCamp().setText("" + cantitate);
    }

    public void actualizarePretTotal(double pretTotal) {
        getPretTotalCamp().setText("" + pretTotal);
    }

    public void actualizareIdUtilizatorVal(int idUtilizator) {
        this.idUtilizator = idUtilizator;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getModificareBtn()) {
                int idProdusCos = 0,
                cantitateProdusCos = 0;
                double pretTotal = 0.0;
                boolean stareObiect = true;

                try {
                    idProdusCos =  Integer.parseInt(getIdProdusStocatCamp().getText().trim());
                } catch (Exception eroare) {
                    stareObiect = false;
                    JOptionPane.showMessageDialog(null, "Există o eroare la preluarea ID-ului pentru produsul selectat.", "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                }

                try {
                   cantitateProdusCos =  Integer.parseInt(getCantitateProdusCamp().getText().trim());
                } catch (Exception eroare) {
                    stareObiect = false;
                    JOptionPane.showMessageDialog(null, "Trebuie să introduceți un număr pentru cantitate.", "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                }

                try {
                    pretTotal = Double.parseDouble(getPretTotalCamp().getText().trim());
                } catch (Exception eroare) {
                    stareObiect = false;
                    JOptionPane.showMessageDialog(null, "Există o eroare la determinarea prețului total.", "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                }

                stareObiect = verificareSimultanaCampuri(idProdusCos, cantitateProdusCos, pretTotal, getIdUtilizator());

                if(stareObiect) {
                    interfataFrontEnd.actualizareProdusCos(idProdusCos, cantitateProdusCos, getIdUtilizator());
                }
            } else if(selectat == getStergereBtn()) {
                int idProdusCos = Integer.parseInt(getIdProdusStocatCamp().getText().trim());
                double pretTotal = Double.parseDouble(getPretTotalCamp().getText().trim());
                boolean stare = true;

                stare = verificareIdUtilizator(getIdUtilizator()) && verificareIdProdusStocat(idProdusCos) && verificarePretTotal(pretTotal);

                if(stare) {
                    interfataFrontEnd.stergereProdusCos(getIdUtilizator(), idProdusCos);
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
        add(getIdProdusStocatCos(), gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.7;
        add(getIdProdusStocatCamp(), gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.3;
        add((getCantitateProdusLabel()), gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.7;
        add(getCantitateProdusCamp(), gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.3;
        add((getPretTotalLabel()), gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.7;
        add(getPretTotalCamp(), gc);

        gc.gridx = 0;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0.3;
        add((getModificareBtn()), gc);

        gc.gridx = 1;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.3;
        add(getStergereBtn(), gc);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
