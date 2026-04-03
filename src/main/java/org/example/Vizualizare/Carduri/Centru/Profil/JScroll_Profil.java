package org.example.Vizualizare.Carduri.Centru.Profil;

import javax.swing.*;
import java.awt.*;
import lombok.*;
import org.example.Interfete.I_FrontEnd;

@Getter @Setter
public class JScroll_Profil extends JScrollPane{
    //  Atribute
    private Campuri_JScroll campuriJScroll;

    public JScroll_Profil(Campuri_JScroll campuriJScrollArg) {
        initializari(campuriJScrollArg);
        setViewportView(getCampuriJScroll());
        getViewport().setPreferredSize(new Dimension(50, Short.MAX_VALUE));
        getViewport().setBorder(null);
    }

    //  Metode
    private void initializari(Campuri_JScroll campuriJScrollArg) {
        setCampuriJScroll(campuriJScrollArg);
        //setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
}
