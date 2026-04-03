package org.example.Vizualizare.Carduri.Centru.Meniuri;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;
import org.example.Model.Produs;
import org.example.Model.Produse;

@Getter @Setter
public class Card_Lista_Produse extends JPanel implements ActionListener, I_Tematica{
    //  Atribute
    private Border borduraExterioara;
    private I_Tematica interfataTematica;
    private I_Carduri interfataCarduri;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;
    private Card_Produs cardProdus1, cardProdus2;
    private Produs[] produse;

    public Card_Lista_Produse(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni , Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setInterfataCarduri(interfataCarduri);
        this.interfataBtnActiuni = interfataBtnActiuni;
        setInterfataTematica(interfataTematica);
        setLayout(new GridLayout(1, 2, 1, 0));
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {

        setProduse(new Produs[2]);

        setCardProdus1(new Card_Produs(interfataCarduri, interfataBtnActiuni, culoareComponentaArg,
                fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg,
                "", 0.00, "", ""));
        setCardProdus2(new Card_Produs(interfataCarduri, interfataBtnActiuni, culoareComponentaArg,
                fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg,
                "", 0.00, "", ""));

        getCardProdus1().getAdaugareCosBtn().addActionListener(this);
        getCardProdus2().getAdaugareCosBtn().addActionListener(this);

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);
    }

    private ImageIcon creareImagine(String cale1, String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s/%s.png", cale1, numeFisier))));
    }

    public void primireProduse(Produse produse) {
        if (getProduse() == null || getProduse().length < produse.getProduse().size()) {
            setProduse(new Produs[produse.getProduse().size()]);
        }

        int index = 0;
        for (Produs produs : produse.getProduse()) {
            getProduse()[index] = produs;
            index++;
        }

        if (index > 0) {
            completareProdus(0, getProduse()[0].getIdProdus() , getProduse()[0].getDenProducator(), getProduse()[0].getDenProdus(),
                    getProduse()[0].getContinutProdus(), getProduse()[0].getAbvUM(),
                    getProduse()[0].getPretProdus(), getProduse()[0].getLinkPozaProdus(),
                    getProduse()[0].getEticheta(),
                    getProduse()[0].getIngrediente());

            if (index > 1) {
                completareProdus(1, getProduse()[1].getIdProdus() ,getProduse()[1].getDenProducator(), getProduse()[1].getDenProdus(),
                        getProduse()[1].getContinutProdus(), getProduse()[1].getAbvUM(),
                        getProduse()[1].getPretProdus(), getProduse()[1].getLinkPozaProdus(),
                        getProduse()[1].getEticheta(),
                        getProduse()[1].getIngrediente());
            }
        }
    }

    private void completareProdus(int index, int idProdus, String denProducator, String denProdus, int continutProdus,
                                  String abvUM, double pretProdus, String linkPozaProdus, String eticheta, ArrayList<String> ingrediente) {
        if(index == 0) {
            getCardProdus1().setIdProdus(idProdus);
            getCardProdus1().getDescriereProdusText().setText(String.format("%s %s %d %s", denProducator, denProdus, continutProdus, abvUM));
            getCardProdus1().getPretProdusLabel().setText(String.format("%s: %.2f lei", "Pret", pretProdus));
            getCardProdus1().getProdusLabel().setIcon(getCardProdus1().creareImagineProdus(linkPozaProdus));
            getCardProdus1().setPretProdusStocat(pretProdus);
            getCardProdus1().getEtichetaProdusLabel().setText(eticheta);
            getCardProdus1().setIngrediente(ingrediente);
        } else if(index == 1) {
            getCardProdus2().setIdProdus(idProdus);
            getCardProdus2().getDescriereProdusText().setText(String.format("%s %s %d %s", denProducator, denProdus, continutProdus, abvUM));
            getCardProdus2().getPretProdusLabel().setText(String.format("%s: %.2f lei", "Pret", pretProdus));
            getCardProdus2().getProdusLabel().setIcon(getCardProdus2().creareImagineProdus(linkPozaProdus));
            getCardProdus2().setPretProdusStocat(pretProdus);
            getCardProdus2().getEtichetaProdusLabel().setText(eticheta);
            getCardProdus2().setIngrediente(ingrediente);
        }
    }

    //  Functie pt. preluarea ingredientelor in string

    private String preluareIngrediente(ArrayList<String> ingrediente) {
        StringBuilder sb = new StringBuilder();
        for(String ingredient : ingrediente) {
            sb.append(ingredient).append(", ");
        }

        if(sb.length() > 0 && ingrediente.size() > 0) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton selectat) {
            if(selectat == getCardProdus1().getAdaugareCosBtn()) {
                interfataBtnActiuni.activeazaCardDetaliiProdus(true);
                interfataBtnActiuni.actualizareDetaliiProdus(getCardProdus1().getIdProdus(), getCardProdus1().getDescriereProdusText().getText(),
                        preluareIngrediente(getCardProdus1().getIngrediente()),
                        String.format("%.2f", getCardProdus1().getPretProdusStocat()));
            } else if(selectat == getCardProdus2().getAdaugareCosBtn()) {
                interfataBtnActiuni.activeazaCardDetaliiProdus(true);
                interfataBtnActiuni.actualizareDetaliiProdus(getCardProdus2().getIdProdus(), getCardProdus2().getDescriereProdusText().getText(),
                        preluareIngrediente(getCardProdus2().getIngrediente()),
                        String.format("%.2f", getCardProdus2().getPretProdusStocat()));
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
        add(getCardProdus1());
        add(getCardProdus2());
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
