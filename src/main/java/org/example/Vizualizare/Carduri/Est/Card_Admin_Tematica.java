package org.example.Vizualizare.Carduri.Est;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.*;

@Getter @Setter
public class Card_Admin_Tematica extends JPanel implements ActionListener, I_Tematica{
    private Card_Setari_Tematica cardAdaugareTematica;
    private Border borduraInterioara, borduraExterioara;
    private JButton inchidereButon, meniuNuanteBtn;
    private I_Carduri interfataCard;
    private String fontSv;
    private ImageIcon inchidereButonIcon;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;

    public Card_Admin_Tematica(I_Carduri interfataCard, Card_Setari_Tematica cardAdaugareTematica, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                               boolean focusareButonArg, boolean focusareDesenButonArg) {
        this.cardAdaugareTematica = cardAdaugareTematica;
        this.interfataCard = interfataCard;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        configurari("Tematica", "Setări Aspect");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setMeniuNuanteBtn(new JButton("Înapoi"));
        setInchidereButon(new JButton("Închidere"));

        getMeniuNuanteBtn().addActionListener(this);
        getInchidereButon().addActionListener(this);

        getMeniuNuanteBtn().setFont(new Font("Times New Romans", Font.PLAIN, 18));
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
                if(!(subComponenta instanceof JTextField) && !(subComponenta instanceof JButton))
                    aplicareStiluri3(subComponenta, culoare);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getInchidereButon()) {
                interfataBtnActiuni.activeazaCardAdminTematica(false, true);
                interfataBtnActiuni.resetareCardProduse(true);
                interfataBtnActiuni.resetareCardPortofel(true);
            } else if(selectat == getMeniuNuanteBtn()) {
                interfataBtnActiuni.activeazaCardAdminTematica(false, false);

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
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/CardEst/" + urlTitluIcon + ".png")).append("\">");
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

        Insets ins0 = new Insets(0, 10, 0, 0);
        Insets insBtn = new Insets(0, 0, 0, 0);

        int centru = GridBagConstraints.CENTER;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int gol = GridBagConstraints.NONE;
        int ambele = GridBagConstraints.BOTH;

        gc.fill = ambele;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridwidth = 5;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getCardAdaugareTematica(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 3;
        gc.weightx = 1.0;
        gc.weighty = 500.0;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.PAGE_END;
        add(new JPanel(), gc);

        gc.fill = orizontal;
        gc.gridy++;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = insBtn;
        gc.gridwidth = 1;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        add(getMeniuNuanteBtn(), gc);


        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = insBtn;
        gc.gridwidth = 1;
        gc.weightx = 1.0;
        add(getInchidereButon(), gc);
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
