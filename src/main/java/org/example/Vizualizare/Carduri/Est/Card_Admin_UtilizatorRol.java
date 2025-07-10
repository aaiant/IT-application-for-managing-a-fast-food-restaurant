package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.*;

@Getter @Setter
public class Card_Admin_UtilizatorRol extends JPanel implements ActionListener, I_Tematica{
    private Card_Setari_UtilizatorRol cardSetariUtilizatorRol;
    private Border borduraInterioara, borduraExterioara;
    private JButton inchidereButon;
    private I_Carduri interfataCard;
    private String fontSv;
    private ImageIcon inchidereButonIcon;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;

    public Card_Admin_UtilizatorRol(I_Carduri interfataCard, Card_Setari_UtilizatorRol cardSetariUtilizatorRol, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.cardSetariUtilizatorRol = cardSetariUtilizatorRol;
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        configurari("Setari", "Setări Roluri");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInchidereButon(new JButton("Închidere"));

        getInchidereButon().addActionListener(this);

        getInchidereButon().setFont(new Font("Times New Romans", Font.PLAIN, 18));


        setInchidereButonIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/Butoane/InchidereButon2.png"))));

        getInchidereButon().setIcon(getInchidereButonIcon());

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);

        asezareElemente();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getInchidereButon()) {
                interfataBtnActiuni.activeazaCardAdminUtilizatorRol(false);
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
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/Butoane/" + urlTitluIcon + ".png")).append("\">");
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
        Insets ins0 = new Insets(0, 5, 0, 5);

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 5;
        add(getCardSetariUtilizatorRol(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 5;
        gc.weighty = 0.1;
        add(new JPanel(), gc);


        gc.gridy++;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 2;
        gc.anchor = GridBagConstraints.CENTER;
        add(getInchidereButon(), gc);
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}

