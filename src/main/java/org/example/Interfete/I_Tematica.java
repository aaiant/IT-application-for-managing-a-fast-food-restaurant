package org.example.Interfete;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public interface I_Tematica {
    void schimbaTematica(String codHex);
    void schimbaCuloareText(String codHex);
    void configurari(String urlTitluIcon, String textIcon);
    void asezareElemente();
    default void aplicareStiluri(Container cadru, Color culoareComponentaArg, String fontArg, int tipTextArg, int dimensiuneTextArg, Color culoareButonArg,
                                 boolean focusareButonArg, boolean focusareDesenButonArg, Border borduraExterioaraArg) {
        Component[] componente = cadru.getComponents();
        for(Component componenta : componente) {
            componenta.setForeground(culoareComponentaArg);
            componenta.setFont(new Font(fontArg, tipTextArg, dimensiuneTextArg));
            if(componenta instanceof JButton buton) {
                buton.setForeground(culoareButonArg);
                buton.setFocusable(focusareButonArg);
                buton.setFocusPainted(focusareDesenButonArg);
            } else if(componenta instanceof JTextField campText) {
                campText.setForeground(Color.BLACK);
            }
            TitledBorder borduraExterioara = (TitledBorder) borduraExterioaraArg;
            if (borduraExterioara != null) {
                borduraExterioara.setTitleColor(culoareComponentaArg);
                borduraExterioara.setTitleFont(new Font(fontArg, tipTextArg, dimensiuneTextArg));
                cadru.revalidate();
                cadru.repaint();
            }
        }
    }
    default void editareCampuri(ArrayList<JTextField> container, boolean stare) {
        for(JTextField componenta : container)
            componenta.setEditable(stare);
    }
}

