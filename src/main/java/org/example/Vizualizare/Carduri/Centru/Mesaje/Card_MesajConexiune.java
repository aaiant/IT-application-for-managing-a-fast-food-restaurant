package org.example.Vizualizare.Carduri.Centru.Mesaje;

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
public class Card_MesajConexiune extends JPanel implements ActionListener, I_Tematica {
    //  Atribute
    private Border borduraExterioara;
    private I_Carduri interfataCarduri;
    private JLabel titluEticheta, mesajDreptunghiEticheta;
    private JButton accButon;
    private ImageIcon succesInfoIcon, chiefIcon;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;

    //  Constructor
    public Card_MesajConexiune(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni ,String urlTitluIcon, String textIcon, String titlu, String urlFigIcon,
                               String textPrincipal, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                               boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfataCarduri(interfataCarduri);
        this.interfataBtnActiuni = interfataBtnActiuni;
        configurari(urlTitluIcon, textIcon);
        initializari(urlTitluIcon, titlu, urlFigIcon, textPrincipal, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg,
                focusareButonArg, focusareDesenButonArg);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    //  Metode
    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/" + urlTitluIcon + ".png")).append("\">");
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

    private void initializari(String urlTitluIcon, String titlu, String urlFigIcon, String textPrincipal, Color culoareComponentaArg,
                              String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setSuccesInfoIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagini/" + urlTitluIcon + ".png"))));
        setChiefIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(urlFigIcon))));

        setTitluEticheta(new JLabel(titlu));
        getTitluEticheta().setIcon(getSuccesInfoIcon());

        setMesajDreptunghiEticheta(new JLabel(textPrincipal));
        getMesajDreptunghiEticheta().setIcon(getChiefIcon());

        setAccButon(new JButton("Am înțeles"));
        getAccButon().setFocusPainted(false);
        Border borduraExterioara = BorderFactory.createLineBorder(Color.BLACK, 1);
        Border borduraInterioara = BorderFactory.createEmptyBorder(3, 3, 3,3);
        getAccButon().addActionListener(this);

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
        Insets ins10 = new Insets(5, 10, 0, 10);

        int centru = GridBagConstraints.CENTER;
        int gol = GridBagConstraints.NONE;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int ambele = GridBagConstraints.BOTH;

        gc.fill = centru;
        gc.gridy = 0;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0.5;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getTitluEticheta(), gc);

        gc.fill = ambele;
        gc.gridy++;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0.5;
        gc.anchor = centru;
        gc.insets = ins10;
        add(getMesajDreptunghiEticheta(), gc);

        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0.5;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getAccButon(), gc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obiect = e.getSource();
        if(obiect instanceof JButton buton) {
            if (buton == getAccButon()) {
                interfataBtnActiuni.meniuIntroducere(true);
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

