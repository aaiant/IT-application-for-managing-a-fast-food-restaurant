package org.example.Vizualizare.Carduri.Centru.Mesaje;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import lombok.*;
import org.example.Interfete.I_Carduri;
import org.example.Interfete.I_Tematica;

@Getter @Setter
public class Card_MesajMeniuri extends JPanel implements ActionListener, I_Tematica {
    //  Atribute
    private Border borduraExterioara;
    private I_Tematica interfataTematica;
    private I_Carduri interfataCarduri;
    private JLabel mesajSuport_IT_Eticheta, mesajBucatarEticheta, mesajManagerEticheta;
    private JButton inainteButon;
    private ImageIcon suportIT_Icon, bucatarIcon, managerIcon, inainteButonIcon;

    //  Constructor
    public Card_MesajMeniuri(I_Carduri interfataCarduri) {
        setInterfataCarduri(interfataCarduri);
        setInterfataTematica(interfataTematica);
        configurari("Idee", "Introducere");
        initializari();
        asezareElemente();
        aplicareStiluri(Color.BLACK);
    }

    //  Metode
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

    private void initializari() {
        setSuportIT_Icon(creareImagine("Suport_IT2"));
        setBucatarIcon(creareImagine("Chief"));
        setManagerIcon(creareImagine("Manager2"));
        setInainteButonIcon(creareImagine("InainteButon"));

        setMesajSuport_IT_Eticheta(new JLabel("<html>&emsp;În primul rând, vreau să îți urez bun-venit la magazinul nostru! " +
                "Aici, vei avea posibilitatea de a-ți crea un cont și de a plasa comenzi pentru produse de tip Fast-Food!</html>"));
        getMesajSuport_IT_Eticheta().setIcon(getSuportIT_Icon());

        setMesajBucatarEticheta(new JLabel("<html>&emsp;Te invit să guști din cele mai delicioase gogoși pe care le-am făcut, " +
                "dar nu înainte de a mânca un burger de-a dreptul special!</html>"));
        getMesajBucatarEticheta().setIcon(getBucatarIcon());
        getMesajBucatarEticheta().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        setMesajManagerEticheta(new JLabel("<html>&emsp;De altfel, aveți o gamă largă de metode prin care puteți realiza plățile" +
                " pentru comenzile plasate pe platforma noastră. Nu te oprește decât câteva click-uri!</html>"));
        getMesajManagerEticheta().setIcon(getManagerIcon());

        setInainteButon(new JButton("înainte"));
        getInainteButon().setIcon(getInainteButonIcon());
        getInainteButon().setFocusPainted(false);
        getInainteButon().addActionListener(this);
    }

    public void asezareElemente() {
        GridBagConstraints gc = new GridBagConstraints();

        Insets ins0 = new Insets(0, 0, 0, 0);
        Insets ins4 = new Insets(0, 4, 0, 4);

        int centru = GridBagConstraints.CENTER;
        int gol = GridBagConstraints.NONE;
        int orizontal = GridBagConstraints.HORIZONTAL;
        int ambele = GridBagConstraints.BOTH;
        int inceput = GridBagConstraints.LINE_START;
        int sfarsitM = GridBagConstraints.FIRST_LINE_END;

        gc.fill = ambele;
        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.05;
        gc.anchor = inceput;
        gc.insets = ins4;
        add(getMesajSuport_IT_Eticheta(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.05;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = ins4;
        add(getMesajBucatarEticheta(), gc);

        gc.gridy++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.weightx = 1;
        gc.weighty = 0.05;
        gc.anchor = inceput;
        gc.insets = ins4;
        add(getMesajManagerEticheta(), gc);

        gc.fill = gol;
        gc.gridy++;
        gc.gridx = 1;
        gc.gridwidth = 1;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.anchor = sfarsitM;
        gc.insets = ins0;
        add(getInainteButon(), gc);
    }

    private void aplicareStiluri(Color culoare) {
        Component[] componente = this.getComponents();
        for(Component componenta : componente) {
            componenta.setForeground(culoare);
            componenta.setFont(new Font("Times New Roman", Font.PLAIN, 12));

            if(componenta instanceof JButton buton) {
                buton.setForeground(Color.BLACK);
            }
        }
        TitledBorder borduraExterioara = (TitledBorder) getBorduraExterioara();
        if(borduraExterioara != null) {
            borduraExterioara.setTitleColor(culoare);
            this.revalidate();
            this.repaint();
        }
    }

    private ImageIcon creareImagine(String numeFisier) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/Imagini/%s.png", numeFisier))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obiect = e.getSource();
        if(obiect instanceof JButton buton) {
            if(buton == getInainteButon()) {
                System.out.println("test");
            }
        }
    }

    @Override
    public void schimbaTematica(String codHex) {
        Color culoare = Color.decode(codHex);
        this.setBackground(culoare);
        Component[] componente = this.getComponents();
        for(Component componenta : componente) {
            if(componenta instanceof JLabel eticheta) {
                eticheta.setOpaque(true);
                eticheta.setBackground(culoare);
            }
        }
    }

    @Override
    public void schimbaCuloareText(String codHex) {
        Color culoare = Color.decode(codHex);
        aplicareStiluri(culoare);
    }
}

