package org.example.Vizualizare.BaraMeniu.Sosuri;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_Carduri;

import lombok.*;

@Getter @Setter
public class MeniuSosuri extends JMenuItem implements ActionListener{
    //  Atribute
    private I_Carduri interfataCarduri;
    private I_BtnActiuni interfataBtnActiuni;
    private ImageIcon meniuSosuriIcon;


    //  Constructor
    public MeniuSosuri(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni) {
        this.interfataCarduri = interfataCarduri;
        this.interfataBtnActiuni = interfataBtnActiuni;
        initializari();
    }

    //  Metode
    public void initializari() {

        setText("Sosuri");
        setMeniuSosuriIcon(creareImagine("Sosuri"));
        setIcon(getMeniuSosuriIcon());
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Sosuri/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
