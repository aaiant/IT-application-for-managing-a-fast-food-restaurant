package org.example.Vizualizare.BaraMeniu.Gustari;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_Carduri;

import lombok.*;

@Getter @Setter
public class MeniuGustari extends JMenu implements ActionListener{
    //  Atribute
    private I_Carduri interfataCarduri;
    private I_BtnActiuni interfataBtnActiuni;
    private JMenuItem inghetate, gogosi, fursecuri;
    private ImageIcon meniuGustariIcon, inghetateIcon, gogosiIcon, fursecuriIcon;

    //  Constructor
    public MeniuGustari(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni) {
        initializari();
        this.interfataCarduri = interfataCarduri;
        this.interfataBtnActiuni = interfataBtnActiuni;
    }

    //  Metode
    public void initializari() {
        setText("Gustări");
        setIcon(creareImagine("Gustare"));

        setInghetate(new JMenuItem("Înghețate"));
        setGogosi(new JMenuItem("Gogoși"));
        setFursecuri(new JMenuItem("Fursecuri"));

        getInghetate().addActionListener(this);
        getGogosi().addActionListener(this);
        getFursecuri().addActionListener(this);

        setInghetateIcon(creareImagine("Inghetata"));
        setGogosiIcon(creareImagine("Gogoasa"));
        setFursecuriIcon(creareImagine("Fursec"));

        getInghetate().setIcon(getInghetateIcon());
        getGogosi().setIcon(getGogosiIcon());
        getFursecuri().setIcon(getFursecuriIcon());

        add(getFursecuri());
        add(getInghetate());
        add(getGogosi());
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Meniu/Gustari/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JMenuItem itemBtn) {
            if(itemBtn == getGogosi()) {
                interfataBtnActiuni.activeazaCardGogosi(true);
            } else if(itemBtn == getFursecuri()) {
                interfataBtnActiuni.activeazaCardFursecuri(true);
            } else if(itemBtn == getInghetate()) {
                interfataBtnActiuni.activeazaCardInghetata(true);
            }
            interfataBtnActiuni.trecereContMeniu(true, 2);
        }
    }
}
