package org.example.Vizualizare.BaraMeniu.Bauturi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_Carduri;

import lombok.*;

@Getter @Setter
public class MeniuSucuri extends JMenu implements ActionListener {
    //  Atribute
    private I_Carduri interfataCarduri;
    private I_BtnActiuni interfataBtnActiuni;
    private JMenuItem sucuriCarbogazoase, sucuriNaturale;
    private ImageIcon meniuSucuriIcon, sucuriCarbogazoaseIcon, sucuriNaturaleIcon;

    //  Constructor
    public MeniuSucuri(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni, String meniuSucuri, String urlMeniuSucuri,
                       String sucuriCarbogazoase, String urlSucuriCarbogazoase, String sucuriNaturale, String urlSucuriNaturale) {
        initializari(interfataCarduri, meniuSucuri, urlMeniuSucuri, sucuriCarbogazoase, urlSucuriCarbogazoase, sucuriNaturale, urlSucuriNaturale);
        this.interfataBtnActiuni = interfataBtnActiuni;
    }

    //  Metode
    private void initializari(I_Carduri interfataCarduri, String meniuSucuri, String urlMeniuSucuri, String sucuriCarbogazoase,
                              String urlSucuriCarbogazoase, String sucuriNaturale, String urlSucuriNaturale) {
        setInterfataCarduri(interfataCarduri);
        setSucuriCarbogazoase(new JMenuItem(sucuriCarbogazoase));
        setSucuriNaturale(new JMenuItem(sucuriNaturale));

        getSucuriCarbogazoase().addActionListener(this);
        getSucuriNaturale().addActionListener(this);

        setSucuriCarbogazoaseIcon(creareImagine(urlSucuriCarbogazoase));
        setSucuriNaturaleIcon(creareImagine(urlSucuriNaturale));
        setMeniuSucuriIcon(creareImagine(urlMeniuSucuri));

        getSucuriCarbogazoase().setIcon(getSucuriCarbogazoaseIcon());
        getSucuriNaturale().setIcon(getSucuriNaturaleIcon());

        add(getSucuriCarbogazoase());
        add(getSucuriNaturale());

        setIcon(getMeniuSucuriIcon());
        setText(meniuSucuri);
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Meniu/Bauturi/Sucuri/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JMenuItem meniu) {
            if(meniu == getSucuriCarbogazoase()) {
                interfataBtnActiuni.activeazaCardSucuriCarbogazoase(true);
            } else if(meniu == getSucuriNaturale()) {
                interfataBtnActiuni.activeazaCardSucuriNeCarbogazoase(true);
            }
            interfataBtnActiuni.trecereContMeniu(true, 2);
        }
    }
}
