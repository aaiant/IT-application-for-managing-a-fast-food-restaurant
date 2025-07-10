package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.*;

@Getter @Setter
public class Card_Detalii_Produs extends JPanel implements ActionListener, I_Tematica{
    private Border borduraInterioara, borduraExterioara;
    private JButton inchidereButon, adaugareProdusBtn, gestionareBtn;
    private JLabel cantitateLabel;
    private JTextField cantitateCamp;
    private JTextArea continutCamp;
    private JScrollPane scroll;
    private I_Carduri interfataCard;
    private String fontSv;
    private ImageIcon inchidereButonIcon, imagineAdaugareProdusBtn;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv, idProdus, idUtilizator;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private String rolUtilizator;

    public Card_Detalii_Produs(I_Carduri interfataCard, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        configurari("DetaliiProdus", "Detalii produs");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInchidereButon(new JButton("Închidere"));
        getInchidereButon().addActionListener(this);
        getInchidereButon().setFont(new Font("Times New Romans", Font.PLAIN, 18));

        setGestionareBtn(new JButton("Gestionare"));
        getGestionareBtn().addActionListener(this);
        getGestionareBtn().setFont(new Font("Times New Romans", Font.PLAIN, 18));

        setInchidereButonIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/Butoane/InchidereButon2.png"))));
        getInchidereButon().setIcon(getInchidereButonIcon());

        setCantitateLabel(new JLabel("<html><body><p style=\"font-family: 'Times New Roman'; font-size: 14px;\"><b>Cantitate: </b></p></body></html>"));
        getCantitateLabel().setPreferredSize(new Dimension(150, 30));
        getCantitateLabel().setOpaque(true);

        setContinutCamp(new JTextArea());
        getContinutCamp().setLineWrap(true);
        getContinutCamp().setWrapStyleWord(true);
        getContinutCamp().setEditable(false);

        setScroll(new JScrollPane(getContinutCamp()));
        getScroll().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        cantitateCamp = new JTextField(2);

        setAdaugareProdusBtn(new JButton());
        getAdaugareProdusBtn().addActionListener(this);
        setImagineAdaugareProdusBtn(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/Butoane/CosCumparaturi.png"))));
        getAdaugareProdusBtn().setIcon(getImagineAdaugareProdusBtn());

        getAdaugareProdusBtn().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getContinutCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        getCantitateCamp().setFont(new Font("Times New Roman", Font.PLAIN, 18));

        getAdaugareProdusBtn().setMargin(new Insets(1, 1, 1, 1));

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);

        asezareElemente();
    }

    public void actualizareRolUtilizator(String rol) {
        this.rolUtilizator = rol;
        if(rol.equals("Admin")) {
            getGestionareBtn().setEnabled(true);
        } else {
            getGestionareBtn().setEnabled(false);
        }
    }

    private String formatareTextProdus(String denumireProdus, String ingrediente, String pret) {
        return String.format("Denumire produs: %s\n\nIngrediente: %s\n\nPreț: %s lei.", denumireProdus, ingrediente, pret);
    }

    public void golireCampuri() {
        getContinutCamp().setText("");
        getCantitateCamp().setText("");
    }

    public void adaugareText(int idProdus, String denumireProdus, String ingrediente, String pret) {
        getContinutCamp().setText(formatareTextProdus(denumireProdus, ingrediente, pret));
        setIdProdus(idProdus);
    }

    public void actualizareIdUtilizator(int id) {
        setIdUtilizator(id);
    }

    private void aplicareStiluri2(Component container, Color culoare) {
        container.setBackground(culoare);

        if (container instanceof Container subContainer) {
            Component[] componente = subContainer.getComponents();
            for (Component subComponenta : componente) {
                if(!(subComponenta instanceof JTextField) && !(subComponenta instanceof JButton) && !(subComponenta instanceof JComboBox<?>)
                        && !(subComponenta instanceof JTextArea))
                    aplicareStiluri2(subComponenta, culoare);
            }
        }
    }

    private void aplicareStiluri3(Component container, Color culoare) {
        container.setForeground(culoare);

        if (container instanceof Container subContainer) {
            Component[] componente = subContainer.getComponents();
            for (Component subComponenta : componente) {
                if(!(subComponenta instanceof JTextField) && !(subComponenta instanceof JButton) && !(subComponenta instanceof JTextArea))
                    aplicareStiluri3(subComponenta, culoare);
            }
        }
    }

    private boolean verificareCantitate(String cantitateText) {
        try {
            int cantitate = Integer.parseInt(cantitateText);
            if(cantitate >= 1 && cantitate <= 99) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cantitatea trebuie să fie între 1 și 99 de produse.", "Atenție", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch(Exception eroare) {
            JOptionPane.showMessageDialog(null, "Vă rugăm să completați cantitatea dorită!", "Atenție", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getInchidereButon()) {
                interfataBtnActiuni.activeazaCardAdminIntrebari(false);
                interfataBtnActiuni.activeazaCardDetaliiProdus(false);
            } else if(selectat == getAdaugareProdusBtn()) {
                if(verificareCantitate(getCantitateCamp().getText())) {
                    int cantitate = Integer.parseInt(getCantitateCamp().getText());
                    interfataFrontEnd.adaugareProduseCos(getIdUtilizator(), getIdProdus(), cantitate);
                    getCantitateCamp().setText("");
                }
            } else if(selectat == getGestionareBtn()) {
                interfataBtnActiuni.activeazaCardAdminProduse(true);
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        aplicareStiluri2(this, culoare);
        this.setBackground(culoare);
        Component[] componente = this.getComponents();
        for(Component componenta : componente) {
            if(componenta instanceof JLabel eticheta)
                eticheta.setOpaque(true);
            if(!(componenta instanceof JTextField) && !(componenta instanceof JButton) && !(componenta instanceof JTextArea))
                componenta.setBackground(culoare);
        }
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.decode(codHex);
        aplicareStiluri3(this, culoare);
        setCuloareComponentaSv(culoare);
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/Meniu/" + urlTitluIcon + ".png")).append("\">");
        sb.append(String.format("&nbsp;<span style=\"font-size: 14px;\">%s</span>", textIcon));
        sb.append("</html>");
        Dimension dimensiune = getPreferredSize();
        dimensiune.width = 420;
        setPreferredSize(dimensiune);
        setLayout(new GridBagLayout());
        setBorduraInterioara(BorderFactory.createRaisedBevelBorder());
        setBorduraExterioara(BorderFactory.createTitledBorder(sb.toString()));
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        borduraExterioara.setTitleFont(new Font("Times New Roman", Font.PLAIN, 12));
        setBorder(BorderFactory.createCompoundBorder(borduraExterioara, borduraInterioara));
    }

    @Override
    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 3;
        gc.weightx = 1.0;
        gc.weighty = 2.0;
        gc.fill = GridBagConstraints.BOTH;
        add(getScroll(), gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 3;
        gc.weightx = 1.0;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.WEST;
        add(getAdaugareProdusBtn(), gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 1;
        gc.weightx = 0.2;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        add(getCantitateLabel(), gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.gridwidth = 1;
        gc.weightx = 0.4;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;
        add(getCantitateCamp(), gc);

        gc.gridx = 2;
        gc.gridy = 2;
        gc.gridwidth = 1;
        gc.weightx = 0.4;
        gc.fill = GridBagConstraints.NONE;
        add(new JPanel(), gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 3;
        gc.weightx = 1.0;
        gc.weighty = 0.3;
        gc.fill = GridBagConstraints.BOTH;
        add(new JPanel(), gc);

        gc.gridx = 0;
        gc.gridy = 4;
        gc.gridwidth = 1;
        gc.weightx = 0.5;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = new Insets(5, 5, 10, 10);
        add(getInchidereButon(), gc);

        gc.gridx = 1;
        gc.gridy = 4;
        gc.gridwidth = 1;
        gc.weightx = 0.5;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(5, 10, 10, 5);
        add(getGestionareBtn(), gc);

        gc.gridx = 2;
        gc.gridy = 4;
        gc.gridwidth = 1;
        gc.weightx = 0.5;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(5, 10, 10, 5);
        add(getAdaugareProdusBtn(), gc);
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
