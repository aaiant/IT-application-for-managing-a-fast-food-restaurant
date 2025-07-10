package org.example;

import javax.swing.*;
import org.example.Controlor.*;

public class Aplicatie {
    public static void main(String[] args) {
        try {
            System.setProperty("awt.useSystemAAFontSettings", "lcd");
            System.setProperty("swing.aatext", "true");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(Controlor_Principal::new);
    }
}
