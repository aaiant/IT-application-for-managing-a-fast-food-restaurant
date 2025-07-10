package org.example.Vizualizare.Carduri.Centru.Portofel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_FrontEnd;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_BtnActiuni;
import org.example.Model.CardBancar;
import org.example.Vizualizare.Carduri.Centru.Meniuri.Card_Lista_Produse;

@Getter @Setter
public class Card_Portofel extends JPanel implements I_Tematica, ActionListener{
    private Card_Bancar cardBancar;
    private Border borduraExterioara;
    private I_Tematica interfataTematica;
    private I_Carduri interfataCarduri;
    private JButton inapoiBtn, inainteBtn;
    private ImageIcon inapoiBtnIcon, inainteBtnIcon;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;
    private int indexLista, indexListaMax;
    private String urlTitluIcon, titlu, culoareText;
    private I_FrontEnd interfataFrontEnd;
    private CardBancar[] carduriBancareUtilizator;


    public Card_Portofel(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni, I_FrontEnd interfataFrontEnd, Card_Bancar cardBancar,
                         Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                         boolean focusareButonArg, boolean focusareDesenButonArg, String urlTitluIconArg, String titluArg) {
        this.interfataCarduri = interfataCarduri;
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.interfataFrontEnd = interfataFrontEnd;
        this.cardBancar = cardBancar;
        setUrlTitluIcon(urlTitluIconArg);
        setTitlu(titluArg);
        setCuloareText("black");
        configurari(urlTitluIcon, titlu);
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
        schimbaCuloareText("#000000");
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg) {
        setIndexLista(0);

        setInapoiBtnIcon(creareImagine("Butoane", "InapoiMeniuBtn2"));
        setInainteBtnIcon(creareImagine("Butoane", "InainteMeniuBtn"));

        setInapoiBtn(new JButton(""));
        getInapoiBtn().addActionListener(this);
        getInapoiBtn().setIcon(getInapoiBtnIcon());
        getInapoiBtn().setEnabled(false);

        setInainteBtn(new JButton(""));
        getInainteBtn().addActionListener(this);
        getInainteBtn().setIcon(getInainteBtnIcon());

        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);
    }

    public void primireDateCarduri(ArrayList<CardBancar> listaCarduri) {
        if(!listaCarduri.isEmpty()) {
            setCarduriBancareUtilizator(new CardBancar[listaCarduri.size()]);
            setIndexListaMax(listaCarduri.size());
            setIndexLista(0);
            int index = 0;
            for(CardBancar card : listaCarduri) {
                getCarduriBancareUtilizator()[index] = card;
                index++;
            }
            actualizeazaDateCard(0);
            getInapoiBtn().setEnabled(false);
            getInainteBtn().setEnabled(getIndexListaMax() > 1);
            getCardBancar().asezareElemente();
        } else {
            setCarduriBancareUtilizator(null);
            if(getCardBancar() != null) {
                getInapoiBtn().setEnabled(false);
                getInainteBtn().setEnabled(false);
                getCardBancar().asezareElemente2();
                getCardBancar().setImagineFundal(creareImagineCard("/Imagini_Produse/Generale/Logo-0.png"));
            }
        }
    }

    private void actualizeazaDateCard(int index) {
        if(getCardBancar() != null && getCarduriBancareUtilizator() != null) {
            getCardBancar().getBancaCardValLabel().setText(getCarduriBancareUtilizator()[index].getNumeBanca());
            getCardBancar().getMonedaCardValLabel().setText(String.format("%s %s", getCarduriBancareUtilizator()[index].getTipMoneda(),
                    getCarduriBancareUtilizator()[index].getMoneda()));
            getCardBancar().getTipCardValLabel().setText(getCarduriBancareUtilizator()[index].getTipCard());
            getCardBancar().getNumarCardValLabel().setText(getCarduriBancareUtilizator()[index].getNumarCard());
            getCardBancar().getPosesorCardValLabel().setText(getCarduriBancareUtilizator()[index].getNume_posesor());
            getCardBancar().getDataExpirareCardValLabel().setText(getCarduriBancareUtilizator()[index].getDataExpirareCard());
            getCardBancar().setImagineFundal(creareImagineCard(getCarduriBancareUtilizator()[index].getLink_poza()));
            getCardBancar().repaint();
        }
    }

    public void resetareVizualizareCarduri() {
        if (getCarduriBancareUtilizator() != null && getIndexListaMax() > 0) {
            setIndexLista(0);
            actualizeazaDateCard(0);
            getInapoiBtn().setEnabled(false);
            getInainteBtn().setEnabled(getIndexListaMax() > 1);
        } else {
            getInapoiBtn().setEnabled(false);
            getInainteBtn().setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if (sursa instanceof JButton buton) {
            if (buton == getInapoiBtn()) {
                navigheazaInapoi();
            } else if (buton == getInainteBtn()) {
                navigheazaInainte();
            }
        }
    }

    private void navigheazaInapoi() {
        if (getIndexLista() <= 0) return;

        setIndexLista(getIndexLista() - 1);
        actualizeazaDateCard(getIndexLista());
        actualizeazaStareButoaneNavigare();
    }

    private void navigheazaInainte() {
        if (getIndexLista() >= getIndexListaMax() - 1) return;

        setIndexLista(getIndexLista() + 1);
        actualizeazaDateCard(getIndexLista());
        actualizeazaStareButoaneNavigare();
    }

    private void actualizeazaStareButoaneNavigare() {
        getInapoiBtn().setEnabled(getIndexLista() > 0);
        getInainteBtn().setEnabled(getIndexLista() < getIndexListaMax() - 1);
    }

    private void aplicareStiluri2(Component container, Color culoare) {
        container.setBackground(culoare);

        if (container instanceof Container subContainer) {
            Component[] componente = subContainer.getComponents();
            for (Component subComponenta : componente) {
                aplicareStiluri2(subComponenta, culoare);
            }
        }
    }

    private void aplicareStiluri3(Component container, Color culoare) {
        container.setForeground(culoare);

        if (container instanceof Container subContainer) {
            Component[] componente = subContainer.getComponents();
            for (Component subComponenta : componente) {
                aplicareStiluri3(subComponenta, culoare);
            }
        }
        if (container instanceof JPanel card) {
            card.setBorder(BorderFactory.createLineBorder(culoare, 2));
            card.revalidate();
            card.repaint();
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
            if(!(componenta instanceof JTextField))
                componenta.setBackground(culoare);
        }
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.WHITE;
        aplicareStiluri3(this, culoare);
        setCuloareComponentaSv(culoare);
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
        setCuloareText(codHex);
        configurari(getUrlTitluIcon(), getTitlu());
    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource(urlTitluIcon + ".png")).append("\">");
        sb.append(String.format("<span style=\"font-size: 15px; color:%s\">&nbsp;%s</span>", getCuloareText(), textIcon));
        sb.append("</html>");
        setLayout(new BorderLayout());
        setBorduraExterioara(BorderFactory.createTitledBorder(sb.toString()));
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        setBorder(BorderFactory.createCompoundBorder(borduraExterioara, BorderFactory.createRaisedBevelBorder()));
        revalidate();
        repaint();
    }

    @Override
    public void asezareElemente() {
        add(getInapoiBtn(), BorderLayout.WEST);
        add(getInainteBtn(), BorderLayout.EAST);
        add(getCardBancar(), BorderLayout.CENTER);
    }

    private ImageIcon creareImagine(String cale1, String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s/%s.png", cale1, numeFisier))));
    }

    private ImageIcon creareImagineCard(String link_poza) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(link_poza)));
    }

    private boolean getFocusareDesenBtnSv() {return focusareDesenBtnSv;}
    private boolean getFocusareBtnSv() {return focusareBtnSv;}
}
