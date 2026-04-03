package org.example.Vizualizare.BaraMeniu.SpecialitatiPaineLipieBlat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import org.example.Interfete.I_BtnActiuni;
import org.example.Interfete.I_Carduri;

import lombok.*;

@Getter @Setter
public class SpecialitatiPaineLipieBlat extends JMenu implements ActionListener{
    //  Atribute
    private I_Carduri interfataCarduri;
    private JMenuItem burgeri, sandiviciuri, shaorme, pizze;
    private ImageIcon meniuDelicatesePaineLipieIcon, burgeriIcon, sandiviciuriIcon, shaormeIcon, pizzeIcon;
    private I_BtnActiuni interfataBtnActiuni;

    //  Constructor
    public SpecialitatiPaineLipieBlat(I_Carduri interfataCarduri, I_BtnActiuni interfataBtnActiuni) {
        initializari();
        this.interfataCarduri = interfataCarduri;
        this.interfataBtnActiuni = interfataBtnActiuni;
    }

    //  Metode
    public void initializari() {

        setText("Delicatese pe Pâine/Lipie & Blat");
        setIcon(creareImagine("Burger"));

        setBurgeri(new JMenuItem("Burgeri"));
        setSandiviciuri(new JMenuItem("Șandvișuri"));
        setShaorme(new JMenuItem("Shaorme"));
        setPizze(new JMenuItem("Pizze"));

        getBurgeri().addActionListener(this);
        getSandiviciuri().addActionListener(this);
        getShaorme().addActionListener(this);
        getPizze().addActionListener(this);

        setBurgeriIcon(creareImagine("Burger2"));
        setSandiviciuriIcon(creareImagine("Sandvici"));
        setShaormeIcon(creareImagine("Shaorma"));
        setPizzeIcon(creareImagine("Pizza"));

        getBurgeri().setIcon(getBurgeriIcon());
        getSandiviciuri().setIcon(getSandiviciuriIcon());
        getShaorme().setIcon(getShaormeIcon());
        getPizze().setIcon(getPizzeIcon());

        add(getBurgeri());
        add(getSandiviciuri());
        add(getShaorme());
        add(getPizze());
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/Meniu/SpecialitatiPaineLipieBlat/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sursa = e.getSource();
        if(sursa instanceof JMenuItem itemBtn) {
            if(itemBtn == getPizze()) {
                interfataBtnActiuni.activeazaCardPizze(true);
            } else if(itemBtn == getBurgeri()) {
                interfataBtnActiuni.activeazaCardBurgeri(true);
            } else if(itemBtn == getShaorme()) {
                interfataBtnActiuni.activeazaCardShaorme(true);
            } else if (itemBtn == getSandiviciuri()) {
                interfataBtnActiuni.activeazaCardSandvisuri(true);
            }
            interfataBtnActiuni.trecereContMeniu(true, 2);
        }
    }
}
