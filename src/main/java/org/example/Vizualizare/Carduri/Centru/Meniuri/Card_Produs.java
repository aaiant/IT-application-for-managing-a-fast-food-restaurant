package org.example.Vizualizare.Carduri.Centru.Meniuri;

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
public class Card_Produs extends JPanel implements ActionListener, I_Tematica{
    //  Atribute
    private Border borduraExterioara;
    private I_Tematica interfataTematica;
    private I_Carduri interfataCarduri;
    private JLabel produsLabel, pretProdusLabel, etichetaProdusLabel, etichetaLabel;
    private JTextArea descriereProdusText;
    private JButton adaugareCosBtn;
    private ImageIcon adaugareCosBtnIcon, detaliiBtnIcon, produsIcon;
    private String fontSv;
    private Color culoareComponentaSv, culoareBtnSv;
    private int tipTextSv, dimensiuneTextSv;
    private boolean focusareBtnSv, focusareDesenBtnSv;
    private I_BtnActiuni interfataBtnActiuni;
    private double pretProdusStocat;
    private ArrayList<String> ingrediente;
    private int idProdus;

    public Card_Produs(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni , Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                       boolean focusareButonArg, boolean focusareDesenButonArg, String descriereProdus, double pretProdus, String urlPozaProdus, String eticheta) {
        setInterfataCarduri(interfataCarduri);
        this.interfataBtnActiuni = interfataBtnActiuni;
        setInterfataTematica(interfataTematica);
        configurari("", "");
        initializari(culoareComponentaArg, fontArg, tipTextArg, dimensiuneTextArg, culoareButonArg, focusareButonArg,
                focusareDesenButonArg, descriereProdus, pretProdus, urlPozaProdus, eticheta);
        asezareElemente();
        aplicareStiluri(this, getCuloareComponentaSv(), getFontSv(), getTipTextSv(),  getDimensiuneTextSv(), getCuloareBtnSv(),
                getFocusareBtnSv(),  getFocusareDesenBtnSv(), getBorduraExterioara());
    }

    private void initializari(Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                              boolean focusareButonArg, boolean focusareDesenButonArg, String descriereProdus, double pretProdus, String urlPozaProdus,
                              String eticheta) {

        setDescriereProdusText(new JTextArea(descriereProdus));
        getDescriereProdusText().setLineWrap(true);
        getDescriereProdusText().setWrapStyleWord(true);
        getDescriereProdusText().setEditable(false);
        getDescriereProdusText().setFocusable(false);
        getDescriereProdusText().setBackground(new Color(214, 217, 223));
        getDescriereProdusText().setBorder(null);

        setPretProdusLabel(new JLabel(String.format("%s: %.2f", "Preț", pretProdus)));

        setEtichetaProdusLabel(new JLabel(eticheta));

        setEtichetaLabel(new JLabel("Etichetă produs: "));

        setProdusLabel(new JLabel(""));
        getProdusLabel().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));

        setAdaugareCosBtnIcon(creareImagine("Butoane", "CosCumparaturi"));
        setAdaugareCosBtn(new JButton(""));
        getAdaugareCosBtn().setIcon(getAdaugareCosBtnIcon());


        setCuloareComponentaSv(culoareComponentaArg);
        setFontSv(fontArg);
        setTipTextSv(tipTextArg);
        setDimensiuneTextSv(dimensiuneTextArg);
        setCuloareBtnSv(culoareButonArg);
        setFocusareBtnSv(focusareButonArg);
        setFocusareDesenBtnSv(focusareDesenButonArg);
    }

    private final Map<String, ImageIcon> imageCache = new HashMap<>();

    public ImageIcon creareImagineNet(String urlNet) {
        if (imageCache.containsKey(urlNet)) {
            return imageCache.get(urlNet);
        }

        try {
            URL url = new URL(urlNet);
            BufferedImage image = ImageIO.read(url);

            if (image != null) {
                BufferedImage scaledImage = new BufferedImage(200, 150, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = scaledImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g2d.drawImage(image, 0, 0, 200, 150, null);
                g2d.dispose();

                ImageIcon imageIcon = new ImageIcon(scaledImage);
                imageCache.put(urlNet, imageIcon);

                return imageIcon;
            } else {
                return creareImagine("Erori", "Eroare404");
            }
        } catch (Exception e) {
            System.err.println("Eroare la incarcarea imaginii: " + e.getMessage());
            return creareImagine("Erori", "Eroare404");
        }
    }

    private ImageIcon creareImagine(String cale1, String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s/%s.png", cale1, numeFisier))));
    }

    public ImageIcon creareImagine2(String cale) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("%s", cale))));
    }

    public ImageIcon creareImagineProdus(String url) {
        if (imageCache.containsKey(url)) {
            return imageCache.get(url);
        }

        try {
            URL resourceUrl = getClass().getResource(url);
            if (resourceUrl == null) {
                System.err.println("Resursa nu a fost găsită: " + url);
                return creareImagine("Erori", "Eroare404");
            }

            BufferedImage image = ImageIO.read(resourceUrl);

            if (image != null) {
                BufferedImage scaledImage = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = scaledImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g2d.drawImage(image, 0, 0, 400, 300, null);
                g2d.dispose();

                ImageIcon imageIcon = new ImageIcon(scaledImage);
                imageCache.put(url, imageIcon);

                return imageIcon;
            } else {
                return creareImagine("Erori", "Eroare404");
            }
        } catch (Exception e) {
            System.err.println("Eroare la încărcarea imaginii: " + e.getMessage());
            return creareImagine("Erori", "Eroare404");
        }
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
        Dimension dim = getPreferredSize();
        dim.width = 150;
        setPreferredSize(dim);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false));
    }

    @Override
    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets ins0 = new Insets(0, 0, 0, 0);
        Insets ins4 = new Insets(0, 4, 0, 4);

        int centru = GridBagConstraints.CENTER;
        int gol = GridBagConstraints.NONE;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int ambele = GridBagConstraints.BOTH;
        int inceput = GridBagConstraints.LINE_START;
        int sfarsitM = GridBagConstraints.FIRST_LINE_END;

        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.2;
        gc.anchor = centru;
        gc.insets = ins4;
        add(getProdusLabel(), gc);

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.2;
        gc.anchor = inceput;
        gc.insets = new Insets(4, 4, 4, 4);
        add(getDescriereProdusText(), gc);

        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getEtichetaLabel(), gc);

        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getEtichetaProdusLabel(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 1;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getPretProdusLabel(), gc);

        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.anchor = centru;
        gc.insets = ins0;
        add(getAdaugareCosBtn(), gc);
    }

    private boolean getFocusareDesenBtnSv() {
        return focusareDesenBtnSv;
    }

    private boolean getFocusareBtnSv() {
        return focusareBtnSv;
    }
}
