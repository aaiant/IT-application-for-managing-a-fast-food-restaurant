package org.example.Controlor;

import javax.swing.*;
import java.awt.*;
import lombok.*;
import org.example.Model.PostgreSQL;

@Getter @Setter
public class Controlor_Principal extends JFrame{
    private Controlor_Carduri controlorCarduri;


    public Controlor_Principal() {
        super("Aplicație informatică pentru gestionarea unui local tip fast-food");
        configurari();
        initializari();
        setareCarduri();
        setJMenuBar(getControlorCarduri().creareJMenuBar());
        pack();
        setVisible(true);
    }

    //  Metode
    private void initializari() {
        setControlorCarduri(new Controlor_Carduri(Color.BLACK, "SansSerif", 0, 20, Color.BLACK,true, false,
                "Inapoi", "Inainte", "InapoiButon", "InainteButon"));
    }

    private void configurari() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1300, 710));
        setMaximumSize(new Dimension(1920, 1080));
        setLayout(new BorderLayout());
        setResizable(true);
    }

    private void setareCarduri() {
        JPanel containerVest = getControlorCarduri().getContainerVest(), containerEst = getControlorCarduri().getContainerEst(),
        containerCentru = getControlorCarduri().getContainerCentru();
        Controlor_Principal.this.add(containerVest, BorderLayout.WEST);
        Controlor_Principal.this.add(containerEst, BorderLayout.EAST);
        Controlor_Principal.this.add(containerCentru, BorderLayout.CENTER);
        containerEst.setVisible(false);
    }

}
