package org.example.Vizualizare.BaraMeniu.Bauturi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_Carduri;

import lombok.*;

@Getter @Setter
public class MeniuBauturi extends JMenu implements ActionListener {
    //  Atribute
    private I_Carduri interfataCarduri;
    private I_BtnActiuni interfataBtnActiuni;
    private MeniuSucuri meniuSucuri;
    private MeniuCafele meniuCafele;
    private MeniuCeaiuri meniuCeaiuri;
    private MeniuApe meniuApe;
    private ImageIcon meniuBauturiIcon;

    //  Constructor
    public MeniuBauturi(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni, String meniuBauturi, String urlMeniuBauturi) {
        initializari(interfataCarduri, interfataBtnActiuni, meniuBauturi, urlMeniuBauturi);
        this.interfataBtnActiuni = interfataBtnActiuni;
    }

    //  Metode
    private void initializari(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni, String meniuBauturi, String urlMeniuBauturi) {
        setInterfataCarduri(interfataCarduri);
        setMeniuSucuri(new MeniuSucuri(getInterfataCarduri(), interfataBtnActiuni, "Sucuri", "Suc", "Carbogazoase",
                "SucCarbogazos", "Necarbogazoase" , "SucNatural"));
        setMeniuCafele(new MeniuCafele(getInterfataCarduri(), interfataBtnActiuni,"Cafele", "Cafea", "Cofeină",
                "Cofeina", "Fără cofeină", "NonCofeina"));
        setMeniuCeaiuri(new MeniuCeaiuri(getInterfataCarduri(), interfataBtnActiuni, "Ceaiuri", "Ceai", "Naturale",
                "CeaiNatural"));
        setMeniuApe(new MeniuApe(getInterfataCarduri(), interfataBtnActiuni));

        setText(meniuBauturi);
        getMeniuSucuri().addActionListener(this);

        setMeniuBauturiIcon(creareImagine(urlMeniuBauturi));

        setIcon(getMeniuBauturiIcon());
        add(getMeniuSucuri());
        add(getMeniuCafele());
        add(getMeniuCeaiuri());
        add(getMeniuApe());
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Meniu/Bauturi/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
