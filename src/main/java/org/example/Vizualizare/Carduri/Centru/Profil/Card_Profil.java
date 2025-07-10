package org.example.Vizualizare.Carduri.Centru.Profil;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_FrontEnd;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;

@Getter @Setter
public class Card_Profil extends JPanel implements ActionListener, I_Tematica {
    //  Atribute
    private JScroll_Profil scrollProfil;
    private ImageIcon avatar;
    private JLabel titluEticheta, utilizatorEticheta, parolaEticheta, confirmareParolaEticheta, numeEticheta, prenumeEticheta,
            emailEticheta, taraEticheta, localitateEticheta, stradaEticheta, numarEticheta, numarTelefonEticheta, titluTextEticheta;
    private JTextField utilizatorText, numeText, prenumeText, numarTelefonText, emailText, numarText;
    private JPasswordField parolaText, confirmareParolaText;
    private JButton modificareButon, renuntareButon, confirmareButon, inapoiButon;
    private Border borduraInterioara, borduraExterioara;
    private I_Carduri interfata;
    private boolean stare, stareBtnInapoi;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;
    private I_FrontEnd interfataFrontEnd;

    //  Constructor
    public Card_Profil(I_Carduri intefataArg, I_FrontEnd interfataFrontEnd, I_BtnActiuni interfataBtnActiuni , JScroll_Profil scrollProfilArg, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                       boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfata(intefataArg);
        setInterfataFrontEnd(interfataFrontEnd);
        this.interfataBtnActiuni = interfataBtnActiuni;
        configurari("Profil", "Profil");
        initializari(scrollProfilArg, culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,
                focusareDesenButonArg);
        asezareElemente();
        setStare(false);
        stareCampuri(false);
        aplicareStiluri();
    }


    //  Metode
    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/Cont/" + urlTitluIcon + ".png")).append("\">");
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

    private void initializari(JScroll_Profil scrollProfilArg, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setScrollProfil(scrollProfilArg);

        setTitluEticheta(new JLabel("MOD: "));

        setTitluTextEticheta(new JLabel("VIZUALIZARE"));

        setModificareButon(new JButton("Modificare"));
        setInapoiButon(new JButton("Înapoi"));
        setRenuntareButon(new JButton("Renunțare "));
        setConfirmareButon(new JButton("Confirmare"));

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);


        stareButoane(false);
    }

    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets standardInsets = new Insets(10, 10, 10, 10);
        Insets smallInsets = new Insets(5, 5, 5, 5);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = standardInsets;
        add(getTitluEticheta(), gc);

        gc.gridx = 1;
        gc.gridwidth = 3;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;
        add(getTitluTextEticheta(), gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 4;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = smallInsets;
        add(getScrollProfil(), gc);

        gc.gridy = 2;
        gc.gridwidth = 1;
        gc.weightx = 0.25;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.insets = standardInsets;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.CENTER;
        add(getInapoiButon(), gc);

        gc.gridx = 1;
        add(getModificareButon(), gc);

        gc.gridx = 2;
        add(getRenuntareButon(), gc);

        gc.gridx = 3;
        add(getConfirmareButon(), gc);
    }

    private void asezareElemente2() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets standardInsets = new Insets(10, 10, 10, 10);
        Insets smallInsets = new Insets(5, 5, 5, 5);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = standardInsets;
        add(getTitluEticheta(), gc);

        gc.gridx = 2;
        gc.gridwidth = 3;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;
        add(getTitluTextEticheta(), gc);
    }

    private void aplicareStiluri() {
        Component[] componente = this.getComponents();
        for(Component componenta : componente) {
            componenta.setForeground(getCuloareComponentaSv());
            componenta.setFont(new Font(getFontSv(), getTipTextSv(), getDimensiuneTextSv()));
            if(componenta instanceof JButton buton) {
                buton.setForeground(getCuloareBtnSv());
                buton.setFocusable(getFocusareBtnSv());
                buton.setFocusPainted(getFocusareDesenBtnSv());
                buton.addActionListener(this);
            } else if(componenta instanceof JLabel eticheta) {
                if(eticheta == getTitluEticheta())
                    eticheta.setFont(new Font("Times New Roman", Font.BOLD, 18));
            }
            TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
            if (borduraExterioara != null) {
                borduraExterioara.setTitleColor(getCuloareComponentaSv());
                borduraExterioara.setTitleFont(new Font(getFontSv(), getTipTextSv(), getDimensiuneTextSv()));
                this.revalidate();
                this.repaint();
            }
        }
    }

    private void stareCampuri(boolean stare) {
        Component[] componente = this.getComponents();
        for(Component componenta : componente) {
            if(componenta instanceof JTextField camp) {
                camp.setEditable(stare);
            }
        }
    }

    private void stareButoane(boolean stare) {
        getModificareButon().setEnabled(!stare);
        getInapoiButon().setEnabled(!stare);
        getRenuntareButon().setEnabled(stare);
        getConfirmareButon().setEnabled(stare);
        getRenuntareButon().setVisible(stare);
        getConfirmareButon().setVisible(stare);
        if(stare) {
            getTitluTextEticheta().setText("MODIFICARE");
        } else {
            getTitluTextEticheta().setText("VIZUALIZARE");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obiect = e.getSource();
        if(obiect instanceof JButton buton) {
            if(buton == getModificareButon()) {
                stareButoane(true);
                interfataFrontEnd.stareCampuri(true);
                asezareElemente2();
            } else if(buton == getRenuntareButon()) {
                stareButoane(false);
                interfataFrontEnd.stareCampuri(false);
                asezareElemente();
            } else if(buton == getInapoiButon()) {
                interfataBtnActiuni.dezactivareContainerEst();
                interfataBtnActiuni.meniuIntroducere(true);
            } else if (buton == getConfirmareButon()) {
                interfataBtnActiuni.actualizareDateProfil(true);
                stareButoane(false);
                interfataFrontEnd.stareCampuri(false);
                asezareElemente();
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        this.setBackground(culoare);
        Component[] componente = this.getComponents();
        for(Component componenta : componente) {
            if(! (componenta instanceof JButton))
                componenta.setBackground(culoare);
            if(componenta instanceof JLabel eticheta)
                eticheta.setOpaque(true);
        }
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.decode(codHex);
        setCuloareComponentaSv(culoare);
        aplicareStiluri();
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }

    private boolean getStareBtnInapoi() {
        return stareBtnInapoi;
    }
}
