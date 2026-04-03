package org.example.Vizualizare.BaraMeniu.Cartofi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_Carduri;

import lombok.*;

@Getter @Setter
public class MeniuCartofi extends JMenu implements ActionListener{
    //  Atribute
    private I_Carduri interfataCarduri;
    private I_BtnActiuni interfataBtnActiuni;
    private JMenuItem cartofiPai, nachos, chipshuri;
    private ImageIcon meniuCartofiIcon, cartofiPaiIcon, nachosIcon, chipshuriIcon, cartofiGratinatiIcon, cartofiCoajaIcon;

    //  Constructor
    public MeniuCartofi(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni) {
        initializari();
        this.interfataCarduri = interfataCarduri;
        this.interfataBtnActiuni = interfataBtnActiuni;
    }

    //  Metode
    public void initializari() {
        setText("Cartofi");
        setIcon(creareImagine("Cartofi2"));

        setNachos(new JMenuItem("Nachos"));
        setCartofiPai(new JMenuItem("Cartofi pai"));
        setChipshuri(new JMenuItem("Chipsuri"));

        getCartofiPai().addActionListener(this);
        getNachos().addActionListener(this);
        getChipshuri().addActionListener(this);

        setCartofiPaiIcon(creareImagine("CartofiPai"));
        setNachosIcon(creareImagine("Nachos"));
        setChipshuriIcon(creareImagine("Chipsuri"));

        getCartofiPai().setIcon(getCartofiPaiIcon());
        getNachos().setIcon(getNachosIcon());
        getChipshuri().setIcon(getChipshuriIcon());

        add(getCartofiPai());
        add(getNachos());
        add(getChipshuri());
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Meniu/Cartofi/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JMenuItem btnItem) {
            if(btnItem == getCartofiPai()) {
                interfataBtnActiuni.activeazaCardCartofiPrajiti(true);
            } else if(btnItem == getChipshuri()) {
                interfataBtnActiuni.activeazaCardChipsuri(true);
            } else if(btnItem == getNachos()) {
                interfataBtnActiuni.activeazaCardNachos(true);
            }
            interfataBtnActiuni.trecereContMeniu(true, 2);
        }
    }
}
