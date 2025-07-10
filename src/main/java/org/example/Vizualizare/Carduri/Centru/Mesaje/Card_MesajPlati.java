package org.example.Vizualizare.Carduri.Centru.Mesaje;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;

@Getter @Setter
public class Card_MesajPlati extends JPanel implements ActionListener, I_Tematica {
    //  Atribute
    private Border borduraExterioara;
    private I_Tematica interfataTematica;
    private I_Carduri interfataCarduri;
    private JButton inapoiButon, inainteButon;
    private JLabel masterCardEticheta,visaEticheta,skrillEticheta, paysafecardEticheta, dolarEticheta, titluEticheta, managerEticheta;
    private ImageIcon masterCardIcon, visaIcon, skrillIcon, paysafecardIcon, dolarIcon, inapoiButonIcon, inainteButonIcon, managerIcon;
    private JTable tabelPlati;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;

    //  Constructor
    public Card_MesajPlati(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni,Color culoareComponentaArg, String fontArg, int tipTextArg, int
            dimensiuneTextArg, Color culoareButonArg, boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfataCarduri(interfataCarduri);
        this.interfataBtnActiuni = interfataBtnActiuni;
        setInterfataTematica(interfataTematica);
        configurari("Plata", "Plati");
        initializari(culoareComponentaArg,  fontArg,  tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
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
        setMasterCardIcon(creareImagine("MasterCard"));
        setVisaIcon(creareImagine("Visa"));
        setSkrillIcon(creareImagine("Skrill"));
        setPaysafecardIcon(creareImagine("PaySafeCard"));
        setDolarIcon(creareImagine("Dolar"));
        setInapoiButonIcon(creareImagineBtn("InapoiButon"));
        setInainteButonIcon(creareImagineBtn("InainteButon"));
        setManagerIcon(creareImagine("Manager"));

        setTitluEticheta(new JLabel("Metode de plată"));

        setManagerEticheta(new JLabel("<html>&emsp;Exact cum am menționat anterior, " +
                "avem diverse metode de plată în cadrul programului realizat; printre acestea se numără.</html>"));
        getManagerEticheta().setIcon(getManagerIcon());

        datePlata[][] informatii = {
                {new datePlata("MasterCard", new JLabel(getMasterCardIcon()))},
                {new datePlata("VISA", new JLabel(getVisaIcon()))},
                {new datePlata("Transfer (Skrill)", new JLabel(getSkrillIcon()))},
                {new datePlata("Cod Bon (PaySafeCard)", new JLabel(getPaysafecardIcon()))},
                {new datePlata("Ramburs", new JLabel(getDolarIcon()))}
        };
        String[] coloane = {"Tip Plata", "Simbol"};

        DefaultTableModel model = new DefaultTableModel(coloane, 0);
        for(datePlata[] linie : informatii) {
            for(datePlata metoda : linie) {
                model.addRow(new Object[]{metoda.getTipPlata(), metoda.getEtichetaIcon()});
            }
        }

        setTabelPlati(new JTable(model));
        getTabelPlati().getColumnModel().getColumn(1).setCellRenderer(new RedareImagine());
        Dimension dimTabel = getTabelPlati().getPreferredSize();
        dimTabel.height += 50;
        getTabelPlati().setPreferredSize(dimTabel);

        setMasterCardEticheta(new JLabel());
        getMasterCardEticheta().setIcon(getMasterCardIcon());

        setVisaEticheta(new JLabel());
        getVisaEticheta().setIcon(getVisaIcon());

        setSkrillEticheta(new JLabel());
        getSkrillEticheta().setIcon(getSkrillIcon());

        setPaysafecardEticheta(new JLabel());
        getPaysafecardEticheta().setIcon(getPaysafecardIcon());

        setDolarEticheta(new JLabel());
        getDolarEticheta().setIcon(getDolarIcon());

        setInapoiButon(new JButton("Inapoi"));
        getInapoiButon().setFocusPainted(false);
        getInapoiButon().addActionListener(this);
        getInapoiButon().setIcon(getInapoiButonIcon());

        setInainteButon(new JButton("Inainte"));
        getInainteButon().setFocusPainted(false);
        getInainteButon().addActionListener(this);
        getInainteButon().setIcon(getInainteButonIcon());

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
        Insets ins4 = new Insets(1, 4, 1, 4);

        int centru = GridBagConstraints.CENTER;
        int gol = GridBagConstraints.NONE;
        int sfarsit = GridBagConstraints.LINE_END;
        int ambele = GridBagConstraints.BOTH;
        int inceput = GridBagConstraints.LINE_START;
        int inceputM = GridBagConstraints.FIRST_LINE_START;

        gc.fill = gol;
        gc.gridy = 0;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0.1;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getTitluEticheta(), gc);

        gc.fill = ambele;
        gc.gridy++;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0.05;
        gc.anchor = inceputM;
        gc.insets = ins4;
        add(getManagerEticheta(), gc);

        gc.gridy++;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0.5;
        gc.anchor = centru;
        gc.insets = ins4;
        add(new JScrollPane(getTabelPlati()), gc);

        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0;
        gc.anchor = inceput;
        gc.insets = ins0;
        add(getInapoiButon(), gc);


        gc.gridx = 0; gc.gridwidth = 2;
        gc.weightx = 1; gc.weighty = 0;
        gc.anchor = sfarsit;
        gc.insets = ins0;
        add(getInainteButon(), gc);
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s.png", numeFisier))));
    }

    private ImageIcon creareImagineBtn(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Butoane/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obiect = e.getSource();
        if(obiect instanceof JButton buton) {
            if(buton == getInapoiButon()) {
                interfataBtnActiuni.meniuIntroducere(true);
            } else if(buton == getInainteButon()) {
                System.out.println("TEST");
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

