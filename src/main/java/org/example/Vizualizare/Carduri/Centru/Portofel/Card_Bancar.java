package org.example.Vizualizare.Carduri.Centru.Portofel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;

import java.net.*;
import javax.imageio.ImageIO;

@Getter @Setter
public class Card_Bancar extends JPanel implements ActionListener, I_Tematica{
    //  Atribute
    private JLabel tipCardLabel, bancaCardLabel, monedaCardLabel, numarCardLabel, dataExpirareCardLabel, posesorCardLabel,
            tipCardValLabel, bancaCardValLabel, monedaCardValLabel, numarCardValLabel, dataExpirareCardValLabel, posesorCardValLabel,
        mesajExistentaCarduriLabel;
    private Border borduraExterioara;
    private I_Tematica interfataTematica;
    private I_Carduri interfataCarduri;
    private JLabel descriereProdusLabel, produsLabel, pretProdusLabel;
    private JButton adaugareCosBtn;
    private ImageIcon adaugareCosBtnIcon, detaliiBtnIcon, produsIcon;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv, existentaCarduri;
    private I_BtnActiuni interfataBtnActiuni;
    private ImageIcon imagineFundal;

    public Card_Bancar(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni , Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                       boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfataCarduri(interfataCarduri);
        this.interfataBtnActiuni = interfataBtnActiuni;
        setInterfataTematica(interfataTematica);
        configurari("", "");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,
                focusareDesenButonArg);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(), getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(), getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setBancaCardLabel(new JLabel("Denumire bancă: "));
        setMonedaCardLabel(new JLabel("Monedă: "));
        setTipCardLabel(new JLabel("Tip card: "));
        setPosesorCardLabel(new JLabel("Posesorul cardului: "));
        setNumarCardLabel(new JLabel("Numărul cardului: "));
        setDataExpirareCardLabel(new JLabel("Dată expirare: "));

        setBancaCardValLabel(new JLabel("Val"));
        setMonedaCardValLabel(new JLabel("Val"));
        setTipCardValLabel(new JLabel("Val"));
        setPosesorCardValLabel(new JLabel("Val"));
        setNumarCardValLabel(new JLabel("Val"));
        setDataExpirareCardValLabel(new JLabel("Val"));

        setMesajExistentaCarduriLabel(new JLabel("<html><body><p style=\"color: 'white'; font-family:'Times New Roman'; font-size: 15px;\"><b>NU DEȚINETI CARDURI MOMENTAN</b></p></body></html>"));

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void schimbaTematica(String codHex) {

    }

    @Override
    public void schimbaCuloareText(String codHex) {

    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
    }

    @Override
    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets ins0 = new Insets(0, 0, 0, 0);
        Insets ins5B = new Insets(0, 5, 0, 5);
        Insets ins5A = new Insets(5, 5, 5, 5);
        Insets insCamp = new Insets(0, 0, 0, 100);

        int gol = GridBagConstraints.NONE;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int centru = GridBagConstraints.CENTER;
        int sfarsit = GridBagConstraints.LINE_END;
        int inceput = GridBagConstraints.LINE_START;

        removeAll();

        gc.fill = gol;
        gc.gridy = 0;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.anchor = sfarsit;
        gc.insets = ins0;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getBancaCardLabel(), gc);

        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.anchor = inceput;
        gc.insets = ins5B;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getBancaCardValLabel(), gc);


        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.anchor = sfarsit;
        gc.insets = ins0;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getMonedaCardLabel(), gc);

        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.anchor = inceput;
        gc.insets = ins5B;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getMonedaCardValLabel(), gc);


        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.anchor = sfarsit;
        gc.insets = ins0;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getTipCardLabel(), gc);

        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.anchor = inceput;
        gc.insets = ins5B;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getTipCardValLabel(), gc);


        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.anchor = sfarsit;
        gc.insets = ins0;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getPosesorCardLabel(), gc);

        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.anchor = inceput;
        gc.insets = ins5B;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getPosesorCardValLabel(), gc);


        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.anchor = sfarsit;
        gc.insets = ins0;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getNumarCardLabel(), gc);

        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.anchor = inceput;
        gc.insets = ins5B;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getNumarCardValLabel(), gc);


        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.anchor = sfarsit;
        gc.insets = ins0;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getDataExpirareCardLabel(), gc);

        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.anchor = inceput;
        gc.insets = ins5B;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getDataExpirareCardValLabel(), gc);

        repaint();
        revalidate();
    }

    public void asezareElemente2() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets ins0 = new Insets(0, 0, 0, 0);
        Insets ins5B = new Insets(0, 5, 0, 5);
        Insets ins5A = new Insets(5, 5, 5, 5);
        Insets insCamp = new Insets(0, 0, 0, 100);

        int gol = GridBagConstraints.NONE;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int centru = GridBagConstraints.CENTER;
        int sfarsit = GridBagConstraints.LINE_END;
        int inceput = GridBagConstraints.LINE_START;

        removeAll();

        gc.fill = gol;
        gc.gridy = 0;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.anchor = centru;
        gc.insets = ins0;
        gc.weightx = 0;
        gc.weighty = 0.3;
        add(getMesajExistentaCarduriLabel(), gc);

        repaint();
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getImagineFundal() != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(getImagineFundal().getImage(), 0, 0, getWidth(), getHeight(), this);
            //g2d.setColor(new Color(255, 255, 255, 180));
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }

}
