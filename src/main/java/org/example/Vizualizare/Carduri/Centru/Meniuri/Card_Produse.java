package org.example.Vizualizare.Carduri.Centru.Meniuri;

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
public class Card_Produse extends JPanel implements ActionListener, I_Tematica {
    //  Atribute
    private Border borduraExterioara;
    private I_Tematica interfataTematica;
    private I_Carduri interfataCarduri;
    private Card_Lista_Produse cardListaSucuriCarbogazoase;
    private JButton inapoiBtn, inainteBtn;
    private ImageIcon inapoiBtnIcon, inainteBtnIcon;
    private String fontSv, meniu, categorie, subcategorie, um;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;
    private int indexLista, nrMaxPagini;
    private String urlTitluIcon, titlu, culoareText;

    public Card_Produse(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni, Card_Lista_Produse cardSucuriCarbogazoase, Color culoareComponentaArg,
                        String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                        boolean focusareButonArg, boolean focusareDesenButonArg, String meniu, String categorie,
                        String subcategorie, String um, String urlTitluIconArg, String titluArg) {
        setInterfataCarduri(interfataCarduri);
        this.interfataBtnActiuni = interfataBtnActiuni;
        this.cardListaSucuriCarbogazoase = cardSucuriCarbogazoase;
        setInterfataTematica(interfataTematica);
        setUrlTitluIcon(urlTitluIconArg);
        setTitlu(titluArg);
        setCuloareText("black");
        configurari(urlTitluIcon, titlu);
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg, focusareDesenButonArg,
                meniu, categorie, subcategorie, um);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg,
                              String meniu, String categorie, String subcategorie, String um) {

        setIndexLista(0);

        setMeniu(meniu);
        setCategorie(categorie);
        setSubcategorie(subcategorie);
        setUm(um);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JButton buton) {
            if(buton == getInapoiBtn()) {
                if (getIndexLista() > 0) {
                    if (!getInainteBtn().isEnabled())
                        getInainteBtn().setEnabled(true);
                    setIndexLista(getIndexLista() - 2);
                    interfataBtnActiuni.semnalCardMeniu(getMeniu(), getCategorie(), getSubcategorie(), getUm(), getIndexLista());
                    if (getIndexLista() == 0) {
                        getInainteBtn().setEnabled(false);
                    }
                    if(getIndexLista() == 0) {getInapoiBtn().setEnabled(false);getInainteBtn().setEnabled(true);}
                }
            } else if(buton == getInainteBtn()) {
                if(getIndexLista() < getNrMaxPagini() ) {
                    if(!getInapoiBtn().isEnabled())
                        getInapoiBtn().setEnabled(true);
                    setIndexLista(getIndexLista() + 2);
                    interfataBtnActiuni.semnalCardMeniu(getMeniu(), getCategorie(), getSubcategorie(), getUm(), getIndexLista());
                    if(getIndexLista() == getNrMaxPagini() - 2) {getInainteBtn().setEnabled(false);}
                }
            }
        }
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
        Color culoare = Color.decode(codHex);
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

    private ImageIcon creareImagine(String cale1, String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s/%s.png", cale1, numeFisier))));
    }

    @Override
    public void asezareElemente() {
        add(getInapoiBtn(), BorderLayout.WEST);
        add(getInainteBtn(), BorderLayout.EAST);
        add(getCardListaSucuriCarbogazoase(), BorderLayout.CENTER);
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
