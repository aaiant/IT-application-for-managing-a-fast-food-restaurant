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
public class Card_MesajIntroducere extends JPanel implements ActionListener, I_Tematica {
    //  Atribute
    private Border borduraExterioara;
    private I_Tematica interfataTematica;
    private I_Carduri interfataCarduri;
    private JLabel meniu, mesaj;
    private ImageIcon meniuIcon;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;

    //  Constructor
    public Card_MesajIntroducere(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni ,Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                 boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfataCarduri(interfataCarduri);
        this.interfataBtnActiuni = interfataBtnActiuni;
        setInterfataTematica(interfataTematica);
        configurari("Acasa", "Pagina principală");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
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

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setMeniuIcon(creareImagine("Burger_Logo"));
        setMeniu(new JLabel(""));
        getMeniu().setIcon(getMeniuIcon());

        setMesaj(new JLabel("<html><b>Bun venit în aplicația oficială a localului nostru de tip fast-food!</b></html>"));

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
        Insets ins4 = new Insets(0, 4, 0, 4);

        gc.gridy = 0;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.05;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = ins4;
        add(getMeniu(), gc);

        gc.gridy = 1;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.05;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = ins4;
        add(getMesaj(), gc);
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Meniu/%s.png", numeFisier))));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
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

