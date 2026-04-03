package org.example.Vizualizare.Carduri.Centru.Cont;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import lombok.*;
import org.example.Interfete.I_Tematica;
import org.example.Interfete.I_Carduri;

@Getter @Setter
public class Card_Portofel extends JPanel implements ActionListener, I_Tematica{
    //  Atribute
    private I_Carduri interfataCarduri;
    private JLabel tipContEticheta, bancnotaPrincipalaEticheta, bancnotaEticheta, soldActualEticheta;
    private JButton carduriBtn, inchidereBtn;
    private Border borduraExterioara;


    //  Construtor
    public Card_Portofel(I_Carduri interfataCarduriArg, String urlTitluIcon, String textIcon, String bancnotaPrincipalaEtichetaArg, String textTipContArg, String textbancnotaArg,
                         String textCarduriBtnArg, String textInchidereBtnArg) {
        configurari(urlTitluIcon, textIcon);
        initializari(interfataCarduriArg, textTipContArg, textbancnotaArg, bancnotaPrincipalaEtichetaArg, textCarduriBtnArg, textInchidereBtnArg);
    }

    //  Metode
    private void initializari(I_Carduri interfataCarduriArg, String bancnotaPrincipalaEtichetaArg, String textTipContArg, String textbancnotaArg,
                              String textCarduriBtnArg, String textInchidereBtnArg) {
        setInterfataCarduri(interfataCarduriArg);                                       /**/  setTipContEticheta(new JLabel(textTipContArg));       /**/ setBancnotaEticheta(new JLabel(textbancnotaArg));
        setBancnotaPrincipalaEticheta(new JLabel(bancnotaPrincipalaEtichetaArg));      /**/
        setCarduriBtn(new JButton(textCarduriBtnArg));                                /**/  getCarduriBtn().addActionListener(this);
        setInchidereBtn(new JButton(textInchidereBtnArg));                           /**/ getInchidereBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obiect = e.getSource();
    }

    @Override
    public void schimbaTematica(String codHex) {

    }

    @Override
    public void schimbaCuloareText(String codHex) {

    }

    @Override
    public void configurari(String urlTitluIcon, String textIcon) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<img src=\"").append(getClass().getResource("/Imagini/" + urlTitluIcon + ".png")).append("\">");
        sb.append(String.format("&nbsp;%s", textIcon));
        sb.append("</html>");
        setLayout(new GridBagLayout());
        Dimension dim = getPreferredSize();
        dim.width = 150;
        setPreferredSize(dim);
        setBorduraExterioara(BorderFactory.createTitledBorder(sb.toString()));
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        setBorder(BorderFactory.createCompoundBorder(borduraExterioara, BorderFactory.createRaisedBevelBorder()));
    }

    @Override
    public void asezareElemente() {

    }
}
