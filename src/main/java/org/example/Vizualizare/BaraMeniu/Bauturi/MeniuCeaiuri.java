package org.example.Vizualizare.BaraMeniu.Bauturi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_Carduri;

import lombok.*;

@Getter @Setter
public class MeniuCeaiuri extends JMenu implements ActionListener {
    //  Atribute
    private I_Carduri interfataCarduri;
    private I_BtnActiuni interfataBtnActiuni;
    private JMenuItem ceaiuriNaturale;
    private ImageIcon meniuCeaiuriIcon, ceaiuriNaturaleIcon;

    //  Constructor
    public MeniuCeaiuri(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni, String meniuCeaiuri, String urlMeniuCeaiuri, String ceaiuriNaturale,
                       String urlCeaiuriNaturale) {
        initializari(meniuCeaiuri, urlMeniuCeaiuri, ceaiuriNaturale, urlCeaiuriNaturale);
        this.interfataCarduri = interfataCarduri;
        this.interfataBtnActiuni = interfataBtnActiuni;
    }

    //  Metode
    private void initializari(String meniuCeaiuri, String urlMeniuCeaiuri, String ceaiuriNaturale,
                              String urlCeaiuriNaturale) {
        setCeaiuriNaturale(new JMenuItem(ceaiuriNaturale));

        getCeaiuriNaturale().addActionListener(this);

        setCeaiuriNaturaleIcon(creareImagine(urlCeaiuriNaturale));
        setMeniuCeaiuriIcon(creareImagine(urlMeniuCeaiuri));

        getCeaiuriNaturale().setIcon(getCeaiuriNaturaleIcon());

        add(getCeaiuriNaturale());

        setIcon(getMeniuCeaiuriIcon());
        setText(meniuCeaiuri);
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Meniu/Bauturi/Ceaiuri/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JMenuItem itemBtn) {
            if(itemBtn == getCeaiuriNaturale()) {
                interfataBtnActiuni.activeazaCardCeaiNatural(true);
            }
            interfataBtnActiuni.trecereContMeniu(true, 2);
        }
    }
}
