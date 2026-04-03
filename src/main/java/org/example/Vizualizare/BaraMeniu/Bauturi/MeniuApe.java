package org.example.Vizualizare.BaraMeniu.Bauturi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_Carduri;

import lombok.*;

@Getter @Setter
public class MeniuApe extends JMenu implements ActionListener {
    //  Atribute
    private I_Carduri interfataCarduri;
    private I_BtnActiuni interfataBtnActiuni;
    private JMenuItem necarbogazoasa, carbogazoasa;
    private ImageIcon meniuApeIcon, necarbogazoasaIcon, carbogazoasaIcon;

    //  Constructor
    public MeniuApe(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni) {
        initializari();
        this.interfataCarduri = interfataCarduri;
        this.interfataBtnActiuni = interfataBtnActiuni;
    }

    //  Metode
    private void initializari() {
        setMeniuApeIcon(creareImagine("Apa"));
        setIcon(getMeniuApeIcon());
        setText("Ape");

        setNecarbogazoasa(new JMenuItem("Plate"));
        setNecarbogazoasaIcon(creareImagine("ApaPlata"));
        getNecarbogazoasa().setIcon(getNecarbogazoasaIcon());
        getNecarbogazoasa().addActionListener(this);

        setCarbogazoasa(new JMenuItem("Minerale"));
        setCarbogazoasaIcon(creareImagine("ApaMinerala"));

        getCarbogazoasa().addActionListener(this);
        getCarbogazoasa().setIcon(getCarbogazoasaIcon());

        add(getNecarbogazoasa());
        add(getCarbogazoasa());
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Meniu/Bauturi/Ape/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JMenuItem itemBtn) {
            if(itemBtn == getCarbogazoasa()) {
                interfataBtnActiuni.activeazaCardApaCarbogazoasa(true);
            } else if(itemBtn == getNecarbogazoasa()) {
                interfataBtnActiuni.activeazaCardApaNecarbogazoasa(true);
            }
            interfataBtnActiuni.trecereContMeniu(true, 2);
        }
    }
}
