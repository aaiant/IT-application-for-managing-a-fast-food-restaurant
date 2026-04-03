package org.example.Vizualizare.Carduri.Centru.Mesaje;

import javax.swing.*;
import lombok.*;

@Getter @Setter
public class datePlata {
    //  Atribute
    private String tipPlata;
    private JLabel etichetaIcon;

    //  Constructor
    public datePlata(String tipPlataArg, JLabel etichetaIconArg) {
        this.tipPlata = tipPlataArg;
        this.etichetaIcon = etichetaIconArg;
    }

}
