package org.example.Vizualizare.Carduri.Centru.Mesaje;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

class RedareImagine extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable tabel, Object sursa,
                                                   boolean selectat, boolean focusare, int linie, int coloana) {
        if (sursa instanceof JLabel) {
            return (JLabel) sursa;
        }
        return super.getTableCellRendererComponent(tabel, sursa, selectat, focusare, linie, coloana);
    }
}