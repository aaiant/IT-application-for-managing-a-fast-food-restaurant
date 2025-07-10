package org.example.Vizualizare.BaraMeniu.Bauturi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_Carduri;

import lombok.*;

@Getter @Setter
public class MeniuCafele extends JMenu implements ActionListener {
    //  Atribute
    private I_Carduri interfataCarduri;
    private I_BtnActiuni interfataBtnActiuni;
    private JMenuItem cofeina, nonCofeina;
    private ImageIcon meniuCafeleIcon, cofeinaIcon, nonCofeinaIcon;

    //  Constructor
    public MeniuCafele(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni, String meniuCafele, String urlMeniuCafele, String cofeina,
                       String urlCofeina, String nonCofeina, String urlNonCofeina) {
        initializari(interfataCarduri, meniuCafele, urlMeniuCafele, cofeina, urlCofeina, nonCofeina, urlNonCofeina);
        this.interfataBtnActiuni = interfataBtnActiuni;
    }

    //  Metode
    private void initializari(I_Carduri interfataCarduri, String meniuCafele, String urlMeniuCafele, String cofeina, String urlCofeina,
                              String nonCofeina, String urlNonCofeina) {
        setInterfataCarduri(interfataCarduri);
        setCofeina(new JMenuItem(cofeina));
        setNonCofeina(new JMenuItem(nonCofeina));

        getCofeina().addActionListener(this);
        getNonCofeina().addActionListener(this);

        setCofeinaIcon(creareImagine(urlCofeina));
        setNonCofeinaIcon(creareImagine(urlNonCofeina));
        setMeniuCafeleIcon(creareImagine(urlMeniuCafele));

        getCofeina().setIcon(getCofeinaIcon());
        getNonCofeina().setIcon(getNonCofeinaIcon());

        add(getCofeina());
        add(getNonCofeina());

        setIcon(getMeniuCafeleIcon());
        setText(meniuCafele);
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Meniu/Bauturi/Cafele/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JMenuItem itemBtn) {
            if(itemBtn == getCofeina()) {
                interfataBtnActiuni.activeazaCardCafeaCofeina(true);
            } else if(itemBtn == getNonCofeina()) {
                interfataBtnActiuni.activeazaCardCafeaFaraCofeina(true);
            }
            interfataBtnActiuni.trecereContMeniu(true, 2);
        }
    }
}
